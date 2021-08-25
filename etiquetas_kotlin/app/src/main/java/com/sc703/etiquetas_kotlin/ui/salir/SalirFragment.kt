package com.sc703.etiquetas_kotlin.ui.salir

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.sc703.etiquetas_kotlin.LoginActivity
import com.sc703.etiquetas_kotlin.R


class SalirFragment : Fragment() {

    private lateinit var btn_salir: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_salir, container, false)

        btn_salir=root.findViewById(R.id.btn_salir)
        btn_salir.setOnClickListener(View.OnClickListener {
            //Cerramos la conexion con Firebase
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(context,R.string.close_session_log_out, Toast.LENGTH_SHORT).show()
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        })

        return root
    }
}