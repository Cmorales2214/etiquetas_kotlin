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
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegistrarActivity : AppCompatActivity() {
    private lateinit var txtNombre: EditText
    private lateinit var txtApellido: EditText
    private lateinit var txtCorreo : EditText
    private lateinit var txtContrasena : EditText
    private lateinit var progressBar : ProgressBar
    private lateinit var dbReference: DatabaseReference
    private lateinit var database : FirebaseDatabase
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar2)

        //Referenciar las variables con las instancias
        txtNombre = findViewById(R.id.txtNombre)
        txtApellido = findViewById(R.id.txtApellido)
        txtCorreo = findViewById(R.id.txtEmail)
        txtContrasena = findViewById(R.id.txtPassword)

        progressBar = findViewById(R.id.progressBar)

        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()

        dbReference = database.reference.child("User")

    }


    //Funcion para el evento Onclick
    fun registro(view: View){

        crearNuevaCuenta()
    }

    private fun crearNuevaCuenta(){
        val nombre : String = txtNombre.text.toString()
        val apellido : String = txtApellido.text.toString()
        val email : String = txtCorreo.text.toString()
        val contrasena : String = txtContrasena.text.toString()

        if (!TextUtils.isEmpty(nombre)
            && !TextUtils.isEmpty(apellido)
            && !TextUtils.isEmpty(email)
            && !TextUtils.isEmpty(contrasena) ){

            progressBar.visibility = View.VISIBLE

            auth.createUserWithEmailAndPassword(email,contrasena)
                .addOnCompleteListener(this){

                        task ->

                    if(task.isComplete){
                        val User: FirebaseUser? = auth.currentUser
                        verificarEmail(User)

                        val userBD =dbReference.child(User?.uid!!)

                        userBD.child("Nombre").setValue(nombre)
                        userBD.child("Apellido").setValue(apellido)
                        session()
                    }
                }

        }
    }

    private fun session(){

        startActivity(Intent(this, LoginActivity::class.java))

    }


    private fun verificarEmail (user: FirebaseUser?){
        user?.sendEmailVerification()
            ?.addOnCompleteListener(this){
                    task ->

                if(task.isComplete){
                    Toast.makeText(this, R.string.toast_email_sent,
                        Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, R.string.toast_wrong,
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

}