package com.sc703.etiquetas_kotlin.ui.contacto

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.sc703.etiquetas_kotlin.R

class CotactoFragment : Fragment() {

    private lateinit var btn_Llamar : Button
    private lateinit var btn_Email : Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_cotacto, container, false)

        btn_Llamar = root.findViewById(R.id.btn_Llamar)
        btn_Email = root.findViewById(R.id.btn_Correo)

        btn_Llamar.setOnClickListener(View.OnClickListener {
            Llamar("tel:" + getString(R.string.Contact_number))
        })

        btn_Email.setOnClickListener(View.OnClickListener {
            Correo(
                getString(R.string.Contact_email),
                getString(R.string.Contact_email_subject),
                getString(R.string.Contact_email_message)
            )
        })

        return root
    }

    private fun Llamar(Telefono:String){
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse(Telefono))
        startActivity(intent)
    }

    private fun Correo(Para: String, Asunto: String, Mensaje:String){
        val email = Intent(Intent.ACTION_SEND)
        email.data = Uri.parse("mailto:")
        email.type = "text/plain"
        email.putExtra(Intent.EXTRA_SUBJECT, Asunto)
        email.putExtra(Intent.EXTRA_EMAIL, arrayOf(Para))
        email.putExtra(Intent.EXTRA_TEXT, Mensaje)

        try {
            startActivity(Intent.createChooser(email,R.string.Contact_send_mail.toString()))
        }catch (e: ActivityNotFoundException){
            Toast.makeText(context,R.string.Contact_no_app, Toast.LENGTH_SHORT).show()
        }
    }

}