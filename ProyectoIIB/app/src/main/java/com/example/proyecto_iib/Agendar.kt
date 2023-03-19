package com.example.proyecto_iib

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import android.widget.CalendarView.OnDateChangeListener
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date


class Agendar : AppCompatActivity() {

    val db: FirebaseFirestore = Firebase.firestore
    var idPaciente=""
    var idOdontologo=""
    var idProcedimiento=""
    var fechaCita=""
    var horaInicio="09:00"
    var horaFin="18:00"
    var horaCita=""

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agendar)

        val home = findViewById<ImageButton>(R.id.btn_home)
        val cancelar = findViewById<ImageButton>(R.id.btn_cancelar)
        val pacientes = findViewById<ImageButton>(R.id.btn_pacientes)
        val perfil = findViewById<ImageButton>(R.id.btn_perfil)
        val calendar = findViewById<CalendarView>(R.id.calendarView)
        val agendar_cita = findViewById<Button>(R.id.btn_agendar_c)
        val selec_hora = findViewById<Button>(R.id.btn_selec_hora)
        val et_hora = findViewById<EditText>(R.id.tf_hora)

        home
            .setOnClickListener{
                abrirActividad(Home::class.java)
            }

        cancelar
            .setOnClickListener{
                abrirActividad(Cancelar::class.java)
            }

        pacientes
            .setOnClickListener{
                abrirActividad(Pacientes::class.java)
            }

        perfil
            .setOnClickListener{
                abrirActividad(Perfil::class.java)
            }

        val especialidades = arrayListOf<String>()


        db.collection("especialidades")
            .get()
            .addOnSuccessListener { res ->
                for(procedimiento in res){
                    especialidades.add(procedimiento["nombre"].toString())
                }
            }


        val especialista : AutoCompleteTextView = findViewById(R.id.auto_complete_especialista)
        val adapter = ArrayAdapter(this,R.layout.list_item,especialidades)

        especialista.setAdapter(adapter)
        especialista.onItemClickListener = AdapterView.OnItemClickListener{
            adapterView, view, i, l ->
            val itemSelected = adapterView.getItemAtPosition(i)
            var idEspecialidad = -1
            val procedimientos = arrayListOf<String>()
            val hora_inicio = ""
            val hora_fin = ""

            db.collection("especialidades")
                .whereEqualTo("nombre",itemSelected.toString())
                .get()
                .addOnSuccessListener { res ->
                    for ( especialidad in res){
                        idEspecialidad = especialidad.id.toInt()
                    }

                    db.collection("procedimientos")
                        .whereEqualTo("idEspecialidad",idEspecialidad.toString())
                        .get()
                        .addOnSuccessListener { res ->
                            for(procedimiento in res){
                                procedimientos.add(procedimiento["nombre"].toString())
                            }
                        }

                    db.collection("odontologos")
                        .whereEqualTo("id_especialidad",idEspecialidad.toString())
                        .get()
                        .addOnSuccessListener { res ->
                            for(odontologo in res){
                                idOdontologo = odontologo.id
                                horaInicio = odontologo["hora_atencion_inicio"].toString()
                                horaFin = odontologo["hora_atencion_final"].toString()
                            }
                        }

                    Log.i("Procedimientos",procedimientos.toString())

                    val procedimiento : AutoCompleteTextView = findViewById(R.id.auto_complete_procedimiento)
                    val adapterProcedimiento = ArrayAdapter(this,R.layout.list_item,procedimientos)

                    procedimiento.setAdapter(adapterProcedimiento)
                    procedimiento.onItemClickListener = AdapterView.OnItemClickListener {
                            adapterView, view, i, l ->
                        val itemSelectedProced = adapterView.getItemAtPosition(i)
                        db.collection("procedimientos")
                            .whereEqualTo("nombre",itemSelectedProced.toString())
                            .get()
                            .addOnSuccessListener { res ->
                                for (proc in res) {
                                    idProcedimiento = proc.id
                                }
                                Log.i("Id Prod",idProcedimiento+" ODon "+idOdontologo+" fecha"+fechaCita+"Hora"+horaCita)
                            }
                    }

                }
        }

        calendar.setOnDateChangeListener(OnDateChangeListener { view, year, month, dayOfMonth -> //Define formato inicial.
            val mes = month+1
            val fecha = "$year/$mes/$dayOfMonth"
            val formatoInicial: DateFormat = SimpleDateFormat("yyyy/MM/dd")
            var actualdate = ""
            try {
                val dateInicial: Date = formatoInicial.parse(fecha)
                //* Aqui define el formato deseado.
                val nuevoFormato = "dd-MM-yyyy"
                val formatoSalida = SimpleDateFormat(nuevoFormato)
                actualdate = formatoSalida.format(dateInicial)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            fechaCita = actualdate
        })

        val cal = Calendar.getInstance()
        val listenerHora = TimePickerDialog.OnTimeSetListener{
                view,selectedHourOfDay,selectedMinute ->
            if (selectedHourOfDay >= horaInicio.subSequence(0,2).toString().toInt() && selectedHourOfDay <= horaFin.subSequence(0,2).toString().toInt()){
                et_hora.setText("${selectedHourOfDay}:${selectedMinute}")
                horaCita = "${selectedHourOfDay}:${selectedMinute}"
            }
            else
                Toast.makeText(this,
                    "El horario disponible es de "+horaInicio+" a "+horaFin,
                    Toast.LENGTH_LONG
                ).show()
        }

        selec_hora.setOnClickListener{
            TimePickerDialog(this,
                listenerHora,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }


        //GUARDAR
        agendar_cita.setOnClickListener {

        }
    }

    private fun abrirActividad(
        clase: Class<*>,
    ) {
        val i = Intent(this, clase)
        startActivity(i);
    }

}