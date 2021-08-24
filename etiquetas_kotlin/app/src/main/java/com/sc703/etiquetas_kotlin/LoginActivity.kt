package com.sc703.etiquetas_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var txtUsuario: EditText
    private lateinit var txtContrasena: EditText

    private lateinit var progressBar : ProgressBar
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        txtUsuario = findViewById(R.id.txtUser)
        txtContrasena = findViewById(R.id.txtPassword)

        progressBar = findViewById(R.id.progressBar2)

        auth = FirebaseAuth.getInstance()
    }

    fun olvidarContrasena(view: View){
        startActivity(Intent(this, RecuperarPassActivity::class.java))
    }

    fun registro(view: View){
        startActivity(Intent(this, RegistrarActivity::class.java))
    }

    fun login(view: View){
        loginUsuario()
    }

    private fun loginUsuario(){
        val usuario : String=txtUsuario.text.toString()
        val contrasena : String = txtContrasena.text.toString()

        if (!TextUtils.isEmpty(usuario)
            && !TextUtils.isEmpty(contrasena)){

            progressBar.visibility = View.VISIBLE

            auth.signInWithEmailAndPassword(usuario, contrasena)
                .addOnCompleteListener(this){
                        task ->

                    if(task.isSuccessful){
                        session()
                    }else{
                        Toast.makeText(this, R.string.toast_wrong, Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun session(){
        startActivity(Intent(this, MainActivity::class.java))
    }

}