package com.sc703.etiquetas_kotlin.ui.list

import android.R.color
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sc703.etiquetas_kotlin.R


class Adaptador(private var lista: MutableList<Etiquetas>, var parent: Context?)
    : RecyclerView.Adapter<Adaptador.Holder>() {

    private lateinit var itemClickListener: ItemClickListener

    class Holder(item: View) : RecyclerView.ViewHolder(item) {
        var tv_item_id: TextView
        var tv_item_operator_name: TextView
        var tv_item_production_line: TextView
        var tv_item_description: TextView
        var tv_item_emission_date: TextView
        var tv_item_close_date: TextView
        var tag: LinearLayout

        init {
            tv_item_id = item.findViewById(R.id.tv_item_id)
            tv_item_operator_name = item.findViewById(R.id.tv_item_operator_name)
            tv_item_production_line = item.findViewById(R.id.tv_item_production_line)
            tv_item_description = item.findViewById(R.id.tv_item_description)
            tv_item_emission_date = item.findViewById(R.id.tv_item_emission_date)
            tv_item_close_date = item.findViewById(R.id.tv_item_close_date)
            tag = item.findViewById(R.id.tag)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val item_row = LayoutInflater.from(parent.context).inflate(R.layout.tag, parent, false)
        return Holder(item_row)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val e = lista[position]
        holder.tv_item_id.text = String.format(e.id.toString())
        holder.tv_item_operator_name.text = String.format(e.nombre.toString())
        holder.tv_item_production_line.text = String.format(e.linea.toString())
        holder.tv_item_description.text = String.format(e.descripcion.toString())
        holder.tv_item_emission_date.text = String.format(e.emision.toString())
        holder.tv_item_close_date.text = String.format(e.cierre.toString())
        if(String.format(e.color.toString()) == "Red") {
            holder.tag.setBackgroundColor(Color.parseColor(getColor(R.color.tag_red)));
        }
        if(String.format(e.color.toString()) == "Blue") {
            holder.tag.setBackgroundColor(Color.parseColor(getColor(R.color.tag_blue)));
        }
        if(String.format(e.color.toString()) == "Green") {
            holder.tag.setBackgroundColor(Color.parseColor(getColor(R.color.tag_green)));
        }
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    private fun getColor(color_resource : Int):String{
        var hex = String.format("%06X", 0xFFFFFF and parent?.let { ContextCompat.getColor(it, color_resource) }!!)
        return "#CC"+hex
    }
}