package com.sc703.etiquetas_kotlin.ui.storage

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.sc703.etiquetas_kotlin.R
import java.io.File
import java.io.IOException

class StorageFragment : Fragment() {

    private lateinit var imv_imagen: ImageView
    private lateinit var edt_NombreImag: EditText
    private lateinit var btn_Buscar: Button
    private lateinit var btn_Cargar: Button
    private lateinit var btn_Descargar: Button
    //Creamos la referencia al servicio de Storage
    private lateinit var Almacenamiento: StorageReference
    //Ruta par la imagen
    private lateinit var RutaIMG: Uri
    //Asugnamos un ID al proceso de busqueda
    private val ID_Proceso = 1234
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_storage, container, false)
        imv_imagen = root.findViewById(R.id.imv_Imagen)
        edt_NombreImag = root.findViewById(R.id.edt_NombreImg)
        btn_Buscar = root.findViewById(R.id.btn_Buscar)
        btn_Cargar = root.findViewById(R.id.btn_Cargar)
        btn_Descargar = root.findViewById(R.id.btn_Descargar)

        btn_Buscar.setOnClickListener(View.OnClickListener { v -> BuscarArchivo(v) })
        btn_Cargar.setOnClickListener(View.OnClickListener { v -> CargarArchivo(v) })
        btn_Descargar.setOnClickListener(View.OnClickListener { DescargarArchivo() })

        return root
    }
    //Funcion para abrir un explorador de archivos *** Imagenes
    private fun BuscarArchivo(view: View){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, R.string.Storage_Image_selection.toString()),ID_Proceso)
    }
    //Funcion para obtener los datos de la imagen
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ID_Proceso && resultCode == Activity.RESULT_OK && data != null)
            RutaIMG = data.data!!
        try {
            val bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver,RutaIMG)
            imv_imagen.setImageBitmap(bitmap)
        }catch (e: IOException){
            e.printStackTrace()
            Toast.makeText(context, R.string.Storage_Image_load_fail,
                Toast.LENGTH_SHORT).show()
        }
    }

    //Funcion para Cargar las imagenes ala Storage de Firebase
    private fun CargarArchivo(view: View){
        Almacenamiento = FirebaseStorage.getInstance().reference.child("Imagenes")
        if (RutaIMG != null){
            val dialogo = ProgressDialog(context)
            dialogo.setTitle(R.string.Storage_upload_progress)
            dialogo.show()

            val ref = Almacenamiento.child(edt_NombreImag.text.toString())
            ref.putFile(RutaIMG)
                .addOnSuccessListener {
                    dialogo.dismiss()
                    Toast.makeText(context, R.string.Storage_upload, Toast.LENGTH_SHORT)
                        .show()
                }
                .addOnFailureListener {
                    dialogo.dismiss()
                    Toast.makeText(context, R.string.Storage_upload_fail, Toast.LENGTH_SHORT)
                        .show()
                }
        }else{
            Toast.makeText(context, R.string.Storage_upload_fail, Toast.LENGTH_SHORT)
                .show()
        }
    }

    //Funcion para descargar las imagenes del Storage de Firebase
    private fun DescargarArchivo(){
        //Asignamos la ruta de la imagen a descargar
        Almacenamiento = FirebaseStorage.getInstance().reference.child("Imagenes/" +
                edt_NombreImag.text.toString())
        lateinit var temporal: File

        try {
            temporal = File.createTempFile("images", ".jpg")
        }catch (e: IOException){
            e.printStackTrace()
        }
        val archivoFinal = temporal
        Almacenamiento.getFile(temporal)
            .addOnSuccessListener {
                val imagen = archivoFinal.absolutePath
                imv_imagen.setImageBitmap(BitmapFactory.decodeFile(imagen))
            }
            .addOnFailureListener {
                Toast.makeText(context, R.string.Storage_download_fail, Toast.LENGTH_SHORT)
                    .show()
            }
    }
}