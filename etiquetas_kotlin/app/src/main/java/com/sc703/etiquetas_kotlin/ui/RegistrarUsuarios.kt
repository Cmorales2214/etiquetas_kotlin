package com.sc703.etiquetas_kotlin.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.sc703.etiquetas_kotlin.MainActivity
import com.sc703.etiquetas_kotlin.R

class RegistrarUsuarios : AppCompatActivity() {

    //Intanciar controles
    private lateinit var edt_nombreUsuario : EditText
    private lateinit var edt_ID : EditText
    private lateinit var edt_contrasena: EditText

    private lateinit var btn_Registrar : Button
    //Instancia de autenticador
    private final lateinit var Autenticador: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_usuarios)

        //Enlace de controles
        edt_nombreUsuario = findViewById(R.id.edt_nombreUser)
        edt_ID = findViewById(R.id.edt_idEmpleado)
        edt_contrasena = findViewById(R.id.edt_contrasena)

        //Iniciar autenticador
    Autenticador = FirebaseAuth.getInstance()
}



    //Registrar al usuario
    fun Registro(view: View?){
        val nicknameUser = edt_nombreUsuario.toString()
        //  val ID =  edt_ID.text.toString().toInt()
        val contrasena = edt_contrasena.text.toString()

        //Validacion
        validarNombreVacio()
        validarIDVacio()
        validarContrasena()

        Autenticador.createUserWithEmailAndPassword( nicknameUser, contrasena)
            .addOnCompleteListener(this){ task ->
                if (task.isSuccessful){
                    val UserID = Autenticador.currentUser
                    Toast.makeText(applicationContext,"Registro correcto", Toast.LENGTH_SHORT).show()
                    //Actualizar Interfaz
                    ActualizarInterfaz(UserID)
                } else{
                    Toast.makeText(applicationContext, "Registro incorrecto", Toast.LENGTH_SHORT).show()
                }
            }

    }

    //Actualizar Interfaz

    private fun ActualizarInterfaz(user: FirebaseUser?){
        if(user!= null){
            val intent = Intent (this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validarNombreVacio():Boolean{
        //Validar espacio vacio en nombre
        val nombreUsuario = edt_nombreUsuario.text.toString().trim { it <= ' ' }
        return if (nombreUsuario.isEmpty()){
            edt_nombreUsuario.error = "Digite un nombre".toString()
            false
        }else {
            edt_nombreUsuario.error = null
            true
        }
    }

    private fun validarIDVacio():Boolean{

        val IDusuario= edt_ID.text.toString().trim { it <= ' ' }
        return if (IDusuario.isEmpty()){
            edt_ID.error = "Digite su ID ".toString()
            false
        }else {
            edt_ID.error = null
            true
        }
    }
    private fun validarContrasena():Boolean{

        val contrasena = edt_contrasena.text.toString().trim { it <= ' ' }
        return if (contrasena.isEmpty()){
            edt_contrasena.error = "Digite una contraseña".toString()
            false
        }else {
            edt_contrasena.error = null
            true
        }




}
}