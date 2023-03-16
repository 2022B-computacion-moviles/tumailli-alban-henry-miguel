package com.example.movcomphmta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.IdpResponse
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth

class IFirebaseUIAuth : AppCompatActivity() {

    lateinit var  btnLogin: Button
    lateinit var  btnLogout: Button
    lateinit var  tvNombre: TextView

    private val respuestaLoginIntent = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ){
        res:FirebaseAuthUIAuthenticationResult ->
        if(res.resultCode === RESULT_OK){
            if (res.idpResponse != null){
                seLogeo(res.idpResponse!!)
            }
        }
    }

    fun cambiarNombre(nombre:String) {
        tvNombre.text = nombre
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ifirebase_uiauth)
        btnLogin = findViewById(R.id.btn_login_firebase)
        btnLogout = findViewById(R.id.btn_logout_firebase)
        tvNombre = findViewById(R.id.tv_nombre_firebase)
        cambiarNombre("Ingrese por favor")

        btnLogin.setOnClickListener{enviarIntentLogin()}
        btnLogout.setOnClickListener { logout() }
    }

    fun enviarIntentLogin(){
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build()
        )

        val signIntent = AuthUI
            .getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
        respuestaLoginIntent.launch(signIntent)
    }

    fun logout(){
        btnLogout.visibility = View.INVISIBLE
        btnLogin.visibility = View.VISIBLE
        cambiarNombre("Ingrese por favor")
        FirebaseAuth.getInstance().signOut()
    }

    fun seLogeo(
        res:IdpResponse
    )
    {
        btnLogout.visibility = View.VISIBLE
        btnLogin.visibility = View.INVISIBLE
        if(res.isNewUser == true){
            resgistrarUsuarioPrimeraVez(res)
        }
        cambiarNombre(res.email!!)
    }

    fun resgistrarUsuarioPrimeraVez(
        res: IdpResponse
    ){

    }
}