package com.sc703.etiquetas_kotlin.ui.list

import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.sc703.etiquetas_kotlin.R
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class ListFragment : Fragment() {

    private val llave_Encriptacion = "12354-*-/werfmksdfSDF23$%^&*=="
    private lateinit var recyclerView: RecyclerView
    private lateinit var adaptador: Adaptador
    private val lista = ArrayList<Etiquetas>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_list, container, false)
        recyclerView = root.findViewById(R.id.RVrecyclerview)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adaptador = Adaptador(lista, context)
        recyclerView.adapter = adaptador

        CargaDatos()
        return root
    }

    private fun CargaDatos(){
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()

        db.collection("etiquetas").get().addOnSuccessListener { documento ->
            for(document in documento) {
                val e = Etiquetas()
                e.id = Desencriptar("${document.id}", llave_Encriptacion)
                e.cierre = Desencriptar("${document.data.get("cierre")}", llave_Encriptacion)
                e.color = Desencriptar("${document.data.get("color")}", llave_Encriptacion)
                e.descripcion = Desencriptar("${document.data.get("descripcion")}", llave_Encriptacion)
                e.emision = Desencriptar("${document.data.get("emision")}", llave_Encriptacion)
                e.linea = Desencriptar("${document.data.get("linea")}", llave_Encriptacion)
                e.nombre = Desencriptar("${document.data.get("nombre")}", llave_Encriptacion)
                lista.add(e)
                adaptador.notifyDataSetChanged()
            }
        }
        .addOnFailureListener { exception ->
            Toast.makeText(context, R.string.tag_fail, Toast.LENGTH_SHORT).show()
        }
    }

    private fun Desencriptar(txt_Desencriptar: String, Clave: String): String {
        val clavesecreta = GenerarClaveCriptografica(Clave)
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.DECRYPT_MODE, clavesecreta)
        val datosdecodificados = Base64.decode(txt_Desencriptar, Base64.DEFAULT)
        val datosBytes = cipher.doFinal(datosdecodificados)
        return String(datosBytes)
    }

    private fun GenerarClaveCriptografica(Llave: String): SecretKeySpec {
        val SHA_256 = MessageDigest.getInstance("SHA-256")
        var clave: ByteArray? = Llave.toByteArray(charset("UTF-8"))
        clave = SHA_256.digest(clave)
        return SecretKeySpec(clave, "AES")
    }
}