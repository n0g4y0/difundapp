package io.github.difundapp.firebase.realtimeDatabase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.github.difundapp.firebase.auth.AuthManager
import io.github.difundapp.model.Publicacion


private const val REFERENCIA_PUBLICACIONES = "publicaciones"


class RealtimeDatabaseManager {


    private val auth = AuthManager()
    private val baseDeDatos = FirebaseDatabase.getInstance()

    private val valoresPublicaciones = MutableLiveData<List<Publicacion>>()

    private lateinit var PublicacionesEscuchaDeEventos: ValueEventListener


    fun agregarPublicacion(contenido: String) {
        //1
        val referenciaPublicacion = baseDeDatos.getReference(REFERENCIA_PUBLICACIONES)
        //2
        val key = referenciaPublicacion.push().key ?: ""
        val post = crearPublicacion(key, contenido)

        //3
        referenciaPublicacion.child(key).setValue(post)
    }

    fun onPostsValuesChange(): LiveData<List<Publicacion>> {
        escucharCambiosValorEnPublicaciones()
        return valoresPublicaciones
    }

    fun removePostsValuesChangesListener() {
        baseDeDatos.getReference(REFERENCIA_PUBLICACIONES).removeEventListener(PublicacionesEscuchaDeEventos)
    }

    private fun crearPublicacion(llave: String, contenido: String): Publicacion {
        val usuario = auth.getUsuarioActual()
        val tiempoActual = getTiempoActual()
        return Publicacion(llave, contenido, usuario, tiempoActual)
    }


    /*
    * metodo encargado de actualizar el recyclerView de la pantalla HOME
    * */
    private fun escucharCambiosValorEnPublicaciones() {
        //1
        PublicacionesEscuchaDeEventos = object : ValueEventListener {
            //2
            override fun onCancelled(databaseError: DatabaseError) {
                /* No op */
            }

            //3
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //4
                if (dataSnapshot.exists()) {
                    val publicaciones = dataSnapshot.children.mapNotNull { it.getValue(Publicacion::class.java) }.toList()
                    valoresPublicaciones.postValue(publicaciones)
                } else {
                    //5
                    valoresPublicaciones.postValue(emptyList())
                }
            }
        }

        //6
        baseDeDatos.getReference(REFERENCIA_PUBLICACIONES)
            .addValueEventListener(PublicacionesEscuchaDeEventos)
    }



    private fun getTiempoActual() = System.currentTimeMillis()



}