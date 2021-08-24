package com.sc703.etiquetas_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.firebase.auth.FirebaseAuth

class RecuperarPassActivity : AppCompatActivity() {

    private lateinit var txtEmail : EditText
    private lateinit var auth: FirebaseAuth

    private lateinit var progressBar : ProgressBar

    //Variable para anuncio
    private lateinit var av_Banner: AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperar_pass)

        txtEmail = findViewById(R.id.txtEmail)
        auth = FirebaseAuth.getInstance()
        progressBar = findViewById(R.id.progressBar3)


        //Inicializar anuncios
        MobileAds.initialize(this){}
        //Solicitar bloque de anuncio
        val solicitud_Anuncio = AdRequest.Builder().build()
        av_Banner = findViewById(R.id.av_banner)
        //Pasamos el bloque de anuncio a Adview
        av_Banner.loadAd(solicitud_Anuncio)
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
                        Toast.makeText(this, R.string.toast_email_sent,
                            Toast.LENGTH_SHORT).show()
                    } else{
                        Toast.makeText(this, R.string.toast_wrong,
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }

    }
}