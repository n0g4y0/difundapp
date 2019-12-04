package io.github.difundapp.ui.addPublicacion

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.difundapp.R
import io.github.difundapp.firebase.realtimeDatabase.RealtimeDatabaseManager
import io.github.difundapp.util.mostrarMensaje
import kotlinx.android.synthetic.main.activity_add_publicacion.*

class AddPublicacionActivity : AppCompatActivity() {

    companion object {
        fun crearIntent(context: Context) = Intent(context, AddPublicacionActivity::class.java)
    }

    private val realtimeDatabaseManager by lazy { RealtimeDatabaseManager() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_publicacion)
        inicializar()
    }

    private fun inicializar() {
        configurarClickListeners()

    }

    private fun configurarClickListeners() {
        add_publicacion_button.setOnClickListener {
            agregarPublicacionSiNoEstaVacia()
        }
    }

    private fun agregarPublicacionSiNoEstaVacia() {

        val mensajePublicacion = publicacion_text.text.toString().trim()
        if (mensajePublicacion.isNotEmpty()) {

            realtimeDatabaseManager.agregarPublicacion(mensajePublicacion)
            mostrarMensaje(getString(R.string.publicacion_exitosa))
            finish()
        } else {
            mostrarMensaje(getString(R.string.mensaje_publicacion_vacia))
        }
    }


}