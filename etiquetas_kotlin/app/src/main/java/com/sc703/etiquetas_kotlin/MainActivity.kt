package com.sc703.etiquetas_kotlin

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {

    private val  Codigo_Solicitud = 1234
    // Intanciamos el autententicador
    private lateinit var auth: FirebaseAuth

override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    auth = FirebaseAuth.getInstance()

}
    //Mantenemos la sesion iniciada
    override fun onStart() {
        super.onStart()
        val User = auth.currentUser
        Toast.makeText(applicationContext, R.string.login_successful, Toast.LENGTH_SHORT).show()
        ActualizarInterfaz(User)
    }

    //Metodo para cambiar de intent (formulario)
    private fun ActualizarInterfaz(user: FirebaseUser?) {
        if (user != null) {
            val intent = Intent(this, ActivityPrincipal::class.java)
            startActivity(intent)
        }
    }


    private fun SolicitarPermisos(){
        val Telefono = ActivityCompat.checkSelfPermission(this@MainActivity,
            android.Manifest.permission.CALL_PHONE)
        val Internet = ActivityCompat.checkSelfPermission(this@MainActivity,
            android.Manifest.permission.INTERNET)

        if (Telefono != PackageManager.PERMISSION_GRANTED ||
            Internet != PackageManager.PERMISSION_GRANTED){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                requestPermissions(arrayOf(android.Manifest.permission.CALL_PHONE,
                    android.Manifest.permission.INTERNET),Codigo_Solicitud)
            }
        }
    }
}