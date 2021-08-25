package com.sc703.etiquetas_kotlin.ui.list
import android.widget.AdapterView

interface ItemClickListener {
    fun OnItemClick(posicion: Int, estudiantes: Etiquetas)
}