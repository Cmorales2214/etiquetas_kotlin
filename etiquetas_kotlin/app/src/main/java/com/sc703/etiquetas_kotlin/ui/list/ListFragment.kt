package com.sc703.etiquetas_kotlin.ui.list

import android.app.AlertDialog
import android.database.sqlite.SQLiteDatabase
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
import com.sc703.etiquetas_kotlin.ui.etiqueta.EtiquetaFragment
import com.sc703.etiquetas_kotlin.ui.sqlite.ModeloSQL
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class ListFragment : Fragment() {

    private val llave_Encriptacion = "12354-*-/werfmksdfSDF23$%^&*=="

    private lateinit var recyclerView: RecyclerView

    private lateinit var adaptador: Adaptador
    private lateinit var modeloSQL: ModeloSQL
    private lateinit var bd_SQLite: SQLiteDatabase
    //private lateinit var dialogo: AlertDialog
    //private lateinit var bulder: AlertDialog.Builder

    private val lista = ArrayList<Etiquetas>()

    var id: String? = null
    var cierre: String? = null
    var color: String? = null
    var descripcion: String? = null
    var emision: String? = null
    var linea: String? = null
    var nombre: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_list, container, false)
        modeloSQL = ModeloSQL(context, "etiquetas", null, 1)
        recyclerView = root.findViewById(R.id.RVrecyclerview)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adaptador = Adaptador(lista)
        recyclerView.adapter = adaptador

        CargaDatos2()
        return root
    }

    private fun CargaDatos2(){
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()

        db.collection("etiquetas").get().addOnSuccessListener { documento ->
            for(document in documento) {
                val e = Etiquetas()
                e.id = "${document.id}"
                e.cierre = "${document.data.get("cierre")}"
                e.color = "${document.data.get("color")}"
                e.descripcion = "${document.data.get("descripcion")}"
                e.emision = "${document.data.get("emision")}"
                e.linea = "${document.data.get("linea")}"
                e.nombre = "${document.data.get("nombre")}"
                lista.add(e)
                adaptador.notifyDataSetChanged()
                //Toast.makeText(context, "${document.id}", Toast.LENGTH_SHORT).show()
            }
        }
        .addOnFailureListener { exception ->
            Toast.makeText(context, R.string.tag_fail, Toast.LENGTH_SHORT).show()
        }

            /*if(documento.exists()){
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
                }*/
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

    private fun CargaDatos() {
        bd_SQLite = modeloSQL.readableDatabase
        val fila = bd_SQLite.rawQuery("SELECT * FROM etiquetas", null)
        for (i in 0 until fila.count) {
            val e = Etiquetas()

            if (fila.moveToPosition(i)) {
                id = fila.getString(0).toString()
                cierre = fila.getString(1).toString()
                color = fila.getString(2).toString()
                descripcion = fila.getString(3).toString()
                emision = fila.getString(4).toString()
                linea = fila.getString(5).toString()
                nombre = fila.getString(6).toString()

                e.id = id
                e.cierre = cierre
                e.color = color
                e.descripcion = descripcion
                e.emision = emision
                e.linea = linea
                e.nombre = nombre
                lista.add(e)
                adaptador.notifyDataSetChanged()
            } else {
                Toast.makeText(context, R.string.tag_fail, Toast.LENGTH_SHORT).show()
            }
        }
        bd_SQLite.close()
    }
}