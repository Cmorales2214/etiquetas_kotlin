package com.sc703.etiquetas_kotlin.ui.etiqueta

import android.content.Intent
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.firestore.FirebaseFirestore
import com.sc703.etiquetas_kotlin.R
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec


class EtiquetaFragment : Fragment() {

    private lateinit var edt_TAGid: EditText
    private lateinit var spn_TAGcolor: Spinner
    private lateinit var selected_color : String
    private lateinit var edt_TAGoperator: EditText
    private lateinit var edt_TAGline: EditText
    private lateinit var edt_TAGdescription: EditText
    private lateinit var edt_TAGemission: EditText
    private lateinit var edt_TAGclose: EditText
    private lateinit var btn_TAGAgregar: Button
    private lateinit var btn_TAGBuscar: Button
    private lateinit var btn_TAGEliminar: Button
    private lateinit var btn_TAGEditar: Button
    private lateinit var btn_TAGLimpiar: Button

    private val llave_Encriptacion =
        "12354-*-/werfmksdfSDF23$%^&*=="

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_etiqueta, container, false)

        edt_TAGid = root.findViewById(R.id.edt_TAGid)
        spn_TAGcolor = root.findViewById(R.id.spn_TAGcolor)
        edt_TAGoperator = root.findViewById(R.id.edt_TAGoperator)
        edt_TAGline = root.findViewById(R.id.edt_TAGline)
        edt_TAGdescription = root.findViewById(R.id.edt_TAGdescription)
        edt_TAGemission = root.findViewById(R.id.edt_TAGemission)
        edt_TAGclose = root.findViewById(R.id.edt_TAGclose)
        btn_TAGAgregar = root.findViewById(R.id.btn_TAGAgregar)
        btn_TAGBuscar = root.findViewById(R.id.btn_TAGBuscar)
        btn_TAGEliminar = root.findViewById(R.id.btn_TAGEliminar)
        btn_TAGEditar = root.findViewById(R.id.btn_TAGEditar)
        btn_TAGLimpiar = root.findViewById(R.id.btn_TAGLimpiar)

        btn_TAGAgregar.setOnClickListener(View.OnClickListener { v -> AgregarTag(v) })
        btn_TAGEditar.setOnClickListener(View.OnClickListener { v -> EditarTag(v) })
        btn_TAGEliminar.setOnClickListener(View.OnClickListener { v -> EliminarTag(v) })
        btn_TAGBuscar.setOnClickListener(View.OnClickListener { v -> BuscarTag(v) })
        btn_TAGLimpiar.setOnClickListener(View.OnClickListener { v -> LimpiarTag(v) })

        ArrayAdapter
            .createFromResource(requireContext(), R.array.TAG_spinner_options, android.R.layout.simple_spinner_item)
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spn_TAGcolor.adapter = adapter
            }
        spn_TAGcolor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selected_color = resources.getStringArray(R.array.tag_colors)[position].toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) { }
        }

        selected_color = resources.getStringArray(R.array.tag_colors)[0].toString()

        return root
    }

    private fun AgregarTag(view: View){
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        val dato = hashMapOf(
            "color" to Encriptar(selected_color.toString(), llave_Encriptacion),
            "nombre" to Encriptar(edt_TAGoperator.text.toString(), llave_Encriptacion),
            "linea" to Encriptar(edt_TAGline.text.toString(), llave_Encriptacion),
            "descripcion" to Encriptar(edt_TAGdescription.text.toString(), llave_Encriptacion),
            "emision" to Encriptar(edt_TAGemission.text.toString(), llave_Encriptacion),
            "cierre" to Encriptar(edt_TAGclose.text.toString(), llave_Encriptacion)
        )

        db.collection("etiquetas").document(Encriptar(edt_TAGid.text.toString(), llave_Encriptacion)).set(dato)
            .addOnSuccessListener { _ ->
                Toast.makeText(context, R.string.tag_add, Toast.LENGTH_SHORT).show()
                LimpiarTag(view)
            }
            .addOnFailureListener { _ ->
                Toast.makeText(context, R.string.tag_fail, Toast.LENGTH_SHORT).show()
                LimpiarTag(view)
                LimpiarTag(view)
            }
    }

    private fun EditarTag(view: View){
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()

        val dato = hashMapOf(
            "color" to Encriptar(selected_color.toString(), llave_Encriptacion),
            "nombre" to Encriptar(edt_TAGoperator.text.toString(), llave_Encriptacion),
            "linea" to Encriptar(edt_TAGline.text.toString(), llave_Encriptacion),
            "descripcion" to Encriptar(edt_TAGdescription.text.toString(), llave_Encriptacion),
            "emision" to Encriptar(edt_TAGemission.text.toString(), llave_Encriptacion),
            "cierre" to Encriptar(edt_TAGclose.text.toString(), llave_Encriptacion)
        )

        db.collection("etiquetas").document(Encriptar(edt_TAGid.text.toString(), llave_Encriptacion)).get()
            .addOnSuccessListener { documento ->
                if (documento.exists()) {
                    db.collection("etiquetas").document(Encriptar(edt_TAGid.text.toString(), llave_Encriptacion)).set(dato)
                        .addOnSuccessListener { _ ->
                            Toast.makeText(context, R.string.tag_add, Toast.LENGTH_SHORT).show()
                            LimpiarTag(view)
                        }
                        .addOnFailureListener { _ ->
                            Toast.makeText(context, R.string.tag_fail, Toast.LENGTH_SHORT).show()
                            LimpiarTag(view)
                        }
                }else{
                    Toast.makeText(context, R.string.tag_not_found, Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun EliminarTag(view: View){
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()

        if (edt_TAGid.text.isNotBlank()) {
            db.collection("etiquetas")
                .document(Encriptar(edt_TAGid.text.toString(), llave_Encriptacion))
                .delete()
                .addOnSuccessListener { _ ->Toast.makeText(context, R.string.tag_remove, Toast.LENGTH_SHORT).show()
                    LimpiarTag(view)
                }
                .addOnFailureListener { _ -> Toast.makeText(context, R.string.tag_fail, Toast.LENGTH_SHORT).show()
                    LimpiarTag(view)
                }
        }
    }

    private fun BuscarTag(view: View){
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()

        db.collection("etiquetas").document(Encriptar(edt_TAGid.text.toString(), llave_Encriptacion)).get()
            .addOnSuccessListener { documento ->
                if(documento.exists()){
                    val color:String? = Desencriptar(documento.getString("color").toString(), llave_Encriptacion)
                    val operador:String? = Desencriptar(documento.getString("nombre").toString(), llave_Encriptacion)
                    val lineaproducc:String? = Desencriptar(documento.getString("linea").toString(), llave_Encriptacion)
                    val descripcion:String? = Desencriptar(documento.getString("descripcion").toString(), llave_Encriptacion)
                    val emission:String? = Desencriptar(documento.getString("emision").toString(), llave_Encriptacion)
                    val dateclose:String? = Desencriptar(documento.getString("cierre").toString(), llave_Encriptacion)
                    var i = 0
                    spn_TAGcolor.setSelection(0)
                    selected_color = resources.getStringArray(R.array.tag_colors)[0].toString()
                    resources.getStringArray(R.array.tag_colors).forEach {
                        if (resources.getStringArray(R.array.tag_colors)[i].toString() == color.toString()) {
                            spn_TAGcolor.setSelection(i)
                            selected_color = resources.getStringArray(R.array.tag_colors)[i].toString()
                        }
                        i++
                    }
                    edt_TAGoperator.setText(operador)
                    edt_TAGline.setText(lineaproducc)
                    edt_TAGdescription.setText(descripcion)
                    edt_TAGemission.setText(emission)
                    edt_TAGclose.setText(dateclose)
                }else{
                    Toast.makeText(context, R.string.tag_not_found, Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { _ -> Toast.makeText(context, R.string.tag_fail, Toast.LENGTH_SHORT).show()
                LimpiarTag(view)
            }
    }

    private fun LimpiarTag(view: View){
        spn_TAGcolor.setSelection(0)
        selected_color = resources.getStringArray(R.array.tag_colors)[0].toString()
        edt_TAGoperator.setText("")
        edt_TAGline.setText("")
        edt_TAGdescription.setText("")
        edt_TAGemission.setText("")
        edt_TAGclose.setText("")
        edt_TAGid.setText("")
    }

    private fun GenerarClaveCriptografica(Llave: String): SecretKeySpec {
        val SHA_256 = MessageDigest.getInstance("SHA-256")
        var clave: ByteArray? = Llave.toByteArray(charset("UTF-8"))
        clave = SHA_256.digest(clave)
        return SecretKeySpec(clave, "AES")
    }

    private fun Encriptar(txt_Encriptar: String, Clave: String): String {
        val clavesecreta = GenerarClaveCriptografica(Clave)
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, clavesecreta)
        val datosBytes = cipher.doFinal(txt_Encriptar.toByteArray())
        return Base64.encodeToString(datosBytes, Base64.DEFAULT)
    }

    private fun Desencriptar(txt_Desencriptar: String, Clave: String): String {
        val clavesecreta = GenerarClaveCriptografica(Clave)
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.DECRYPT_MODE, clavesecreta)
        val datosdecodificados = Base64.decode(txt_Desencriptar, Base64.DEFAULT)
        val datosBytes = cipher.doFinal(datosdecodificados)
        return String(datosBytes)
    }

}