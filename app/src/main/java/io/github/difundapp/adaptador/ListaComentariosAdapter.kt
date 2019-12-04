package io.github.difundapp.adaptador

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import io.github.difundapp.R
import io.github.difundapp.model.Publicacion
import io.github.difundapp.util.UtilsFecha
import io.github.difundapp.viewHolder.PublicacionViewHolder

class ListaComentariosAdapter(private val utilsFecha: UtilsFecha) : RecyclerView.Adapter<PublicacionViewHolder>() {

    private val publicaciones = mutableListOf<Publicacion>()
    private val onItemClickLiveData = MutableLiveData<Publicacion>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublicacionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.publicacion_item, parent, false)
        return PublicacionViewHolder(view, onItemClickLiveData, utilsFecha)
    }


    override fun onBindViewHolder(holder: PublicacionViewHolder, position: Int) = holder.setItem(publicaciones[position])



    override fun getItemCount(): Int = publicaciones.size



    fun enActualizacionPublicaciones(publicaciones: List<Publicacion>) {
        this.publicaciones.clear()
        this.publicaciones.addAll(publicaciones)
        notifyDataSetChanged()
    }

    fun enItemPublicacionClick(): LiveData<Publicacion> = onItemClickLiveData


}