package com.sc703.etiquetas_kotlin.ui.etiqueta

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.sc703.etiquetas_kotlin.R


class EtiquetaFragment : Fragment() {

    private lateinit var edt_TAGid: EditText
    private lateinit var edt_TAGcolor: EditText
    private lateinit var edt_TAGoperator: EditText
    private lateinit var edt_TAGline: EditText
    private lateinit var edt_TAGdescription: EditText
    private lateinit var edt_TAGemission: EditText
    private lateinit var edt_TAGclose: EditText
    private lateinit var btn_TAGAgregar: Button
    private lateinit var btn_TAGBuscar: Button
    private lateinit var btn_TAGEliminar: Button
    private lateinit var btn_TAGEditar: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.fragment_etiqueta, container, false)

        edt_TAGid = root.findViewById(R.id.edt_TAGid)
        edt_TAGcolor = root.findViewById(R.id.edt_TAGcolor)
        edt_TAGoperator = root.findViewById(R.id.edt_TAGoperator)
        edt_TAGline = root.findViewById(R.id.edt_TAGline)
        edt_TAGdescription = root.findViewById(R.id.edt_TAGdescription)
        edt_TAGemission = root.findViewById(R.id.edt_TAGemission)
        edt_TAGclose = root.findViewById(R.id.edt_TAGclose)
        btn_TAGAgregar = root.findViewById(R.id.btn_TAGAgregar)
        btn_TAGBuscar = root.findViewById(R.id.btn_TAGBuscar)
        btn_TAGEliminar = root.findViewById(R.id.btn_TAGEliminar)
        btn_TAGEditar = root.findViewById(R.id.btn_TAGEditar)

        btn_TAGAgregar.setOnClickListener(View.OnClickListener { v -> AgregarTag(v) })



        return root
    }

    private fun AgregarTag(view: View){

        val db: FirebaseFirestore = FirebaseFirestore.getInstance()


            val dato = hashMapOf(
                "color" to edt_TAGcolor.text.toString(),
                "name" to edt_TAGoperator.text.toString(),
                "linea" to edt_TAGline.text.toString(),
                "descripcion" to edt_TAGdescription.text.toString(),
                "emision" to edt_TAGemission.text.toString(),
                "cierre" to edt_TAGclose.text.toString()
            )

            db.collection("etiquetas").document(edt_TAGid.toString()).set(dato)
                .addOnSuccessListener { _ ->Toast.makeText(context, R.string.TAG_add, Toast.LENGTH_SHORT)
                        .show()
                }
                .addOnFailureListener { _ -> Toast.makeText(context, R.string.TAG_fail, Toast.LENGTH_SHORT)
                        .show()
                }


    }

}