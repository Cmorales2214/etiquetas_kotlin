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

class RecuperarPassActivity : AppCompatActivity() {

    private lateinit var txtEmail : EditText
    private lateinit var auth: FirebaseAuth

    private lateinit var progressBar : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperar_pass)

        txtEmail = findViewById(R.id.txtEmail)
        auth = FirebaseAuth.getInstance()
        progressBar = findViewById(R.id.progressBar3)
    }

    fun enviar(view: View){
        val email = txtEmail.text.toString()

        if(!TextUtils.isEmpty(email)){
            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(this){
                        task ->

                    if(task.isSuccessful){
                        progressBar.visibility = View.VISIBLE
                        startActivity(Intent(this, LoginActivity::class.java))
                        Toast.makeText(this, "Email enviado",
                            Toast.LENGTH_SHORT).show()
                    } else{
                        Toast.makeText(this, "Error al enviar Email",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }

    }
}