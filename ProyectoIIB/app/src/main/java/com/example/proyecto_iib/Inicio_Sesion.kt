package com.example.proyecto_iib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Inicio_Sesion : AppCompatActivity() {

    private lateinit var correo: EditText
    private lateinit var contrasenia: EditText
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio_sesion)





        val ir_registro= findViewById<TextView>(R.id.text_ir_registrarse2)
        ir_registro.setOnClickListener {

            irActividad(Registro_Paciente::class.java)

        }
        correo=findViewById(R.id.editText_correo)
        contrasenia=findViewById(R.id.editText_Password)

        val btn_iniciarsecion=findViewById<Button>(R.id.btn_ingresar)

        btn_iniciarsecion.setOnClickListener{

            if(correo.text.isNotEmpty()&&contrasenia.text.isNotEmpty()){
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(correo.text.toString(),contrasenia.text.toString())
                    .addOnCompleteListener{
                        if(it.isSuccessful){
                            Log.d("Ingreso", "Ok")

                            db.collection("odontologos").get()
                                .addOnSuccessListener { result ->
                                    for (document in result) {
                                        if (correo.text.toString() == document.data?.get("correo")
                                                .toString()
                                        ){
                                            nombreUsuarioLogueado(Pacientes::class.java, document.data?.get("nombre").toString()
                                                ,document.data?.get("correo").toString())
                                        }

                                    }
                                }

                            db.collection("pacientes").get()
                                .addOnSuccessListener { result ->
                                    for (document in result) {
                                        if (correo.text.toString() == document.data?.get("correo")
                                                .toString()
                                        ){
                                            nombreUsuarioLogueado(Calendario_Citas::class.java, document.data?.get("nombre").toString()
                                            ,document.data?.get("correo").toString())
                                        }

                                    }
                                }

                        }else{
                            Log.d("Ingreso", "Error")
                            abrirDialogo("Datos incorrectos. Ingrese sus datos correctamente o Registrese")
                            correo.setText("")
                            contrasenia.setText("")

                        }
                    }
            }

        }


        val btn_restablecer_contraseña=findViewById<TextView>(R.id.text_olvidaste_contrasenia)
        btn_restablecer_contraseña.setOnClickListener {
            if(correo.text.isNotEmpty()){
            restrablecerContraseña(correo.text.toString())
                correo.setText("")
            }else{
                abrirDialogo("Para restablecer su contrasena. Ingrese su correo")
            }

        }

    }




    fun nombreUsuarioLogueado(
        clase: Class<*>,
        nombre: String,
        correo:String
    ) {
        val usuario = Intent(this, clase)
        usuario.putExtra("nombre_us", nombre)
        usuario.putExtra("correo",correo)
        startActivity(usuario)
    }


    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        startActivity(intent)

    }

    fun abrirDialogo(Titulo: String) {
        val builder = AlertDialog.Builder(this)

        builder.setTitle(Titulo)
        builder.setPositiveButton(
            "Aceptar",
            null
        )

        val dialog = builder.create()
        dialog.show()
    }



    fun restrablecerContraseña(
        correo: String
    ){

        FirebaseAuth.getInstance().sendPasswordResetEmail(correo)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    abrirDialogo("Se ha enviado un correo de recuperación a su cuenta.")
                } else {
                    abrirDialogo("Error usuario no registrado")
                }
            }


    }






}