package com.sc703.proyecto

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {

    private val  Codigo_Solicitud = 1234

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SolicitarPermisos()
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