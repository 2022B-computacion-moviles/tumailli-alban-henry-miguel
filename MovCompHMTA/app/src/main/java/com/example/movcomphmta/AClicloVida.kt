package com.example.movcomphmta

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.movcomphmta.databinding.ActivityAclicloVidaBinding

class AClicloVida : AppCompatActivity() {
    var textoGlobal = ""
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityAclicloVidaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAclicloVidaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_acliclo_vida)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        mostrarSnackBar("OnCreate")
    }

    override fun onRestart(){
        super.onRestart()
        mostrarSnackBar("OnRestart")
    }

    override fun onStart(){
        super.onStart()
        mostrarSnackBar("OnStart")
    }

    override fun onResume(){
        super.onResume()
        mostrarSnackBar("OnResume")
    }

    override fun onPause(){
        super.onPause()
        mostrarSnackBar("OnPause")
    }

    override fun onStop(){
        super.onStop()
        mostrarSnackBar("OnStop")
    }

    override fun onDestroy(){
        super.onDestroy()
        mostrarSnackBar("OnDestroy")
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_acliclo_vida)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run{
            putString("textoGuardado",textoGlobal)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val textoRecuperado:String? = savedInstanceState.getString("textoguarado")
        if(textoRecuperado!=null){
            mostrarSnackBar(textoRecuperado)
            textoGlobal =textoRecuperado
        }
    }

    fun mostrarSnackBar(texto:String){
        textoGlobal += texto
        Snackbar.make(findViewById(R.id.cl_ciclo_vida),
            textoGlobal, Snackbar.LENGTH_LONG)
            .setAction("Action",null).show()
    }
}