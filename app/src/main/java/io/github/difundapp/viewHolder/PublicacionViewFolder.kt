package io.github.difundapp.viewHolder

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import io.github.difundapp.model.Publicacion
import io.github.difundapp.util.UtilsFecha
import kotlinx.android.synthetic.main.publicacion_item.view.*

class PublicacionViewHolder(
    private val view: View,
    private val onItemClickLiveData: MutableLiveData<Publicacion>,
    private val utilsFecha: UtilsFecha
) : RecyclerView.ViewHolder(view) {

    private lateinit var publicacion: Publicacion

    init {
        view.setOnClickListener { onItemClickLiveData.postValue(publicacion) }
    }

    fun setItem(publicacion: Publicacion) {
        this.publicacion = publicacion
        with(view) {
            autor.text = publicacion.author
            contenido.text = publicacion.content
            fecha.text = utilsFecha.conversionATextoFechaNormalizado(publicacion.timestamp)
        }
    }
}