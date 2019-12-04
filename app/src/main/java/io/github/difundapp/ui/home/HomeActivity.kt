package io.github.difundapp.ui.home

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.difundapp.R
import io.github.difundapp.adaptador.ListaComentariosAdapter
import io.github.difundapp.firebase.auth.AuthManager
import io.github.difundapp.firebase.realtimeDatabase.RealtimeDatabaseManager
import io.github.difundapp.model.Publicacion
import io.github.difundapp.ui.Enrutador
import io.github.difundapp.util.UtilsFecha
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private val authManager by lazy { AuthManager() }
    private val realtimeDatabaseManager by lazy { RealtimeDatabaseManager() }

    private val enrutador by lazy { Enrutador() }
    private val ListaComentariosAdapter by lazy { ListaComentariosAdapter(UtilsFecha()) }

    companion object {
        fun crearIntent(context: Context) = Intent(context, HomeActivity::class.java)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        inicializar()
    }

    override fun onStart() {
        super.onStart()
        escuchaPorCambiosPublicaciones()
    }

    override fun onStop() {
        super.onStop()
        realtimeDatabaseManager.removePostsValuesChangesListener()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean =
        when (item?.itemId) {
            R.id.action_logout -> {
                authManager.cerrarSesion(this)
                enrutador.IniciarPantallaLogin(this)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    private fun inicializar() {
        setSupportActionBar(homeToolbar)
        inicializarRecyclerView()

        agregar_publicacion_Fab.setOnClickListener { enrutador.startAddPostScreen(this) }

    }

    private fun inicializarRecyclerView() {
        lista_publicaciones.layoutManager = LinearLayoutManager(this)
        lista_publicaciones.setHasFixedSize(true)
        lista_publicaciones.adapter = ListaComentariosAdapter

        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        lista_publicaciones.addItemDecoration(divider)
    }

    private fun escuchaPorCambiosPublicaciones() {
        realtimeDatabaseManager.onPostsValuesChange()
            .observe(this, Observer(::sobreActualizacionesPublicaciones))
    }

    private fun sobreActualizacionesPublicaciones(publicaciones: List<Publicacion>) {
        ListaComentariosAdapter.enActualizacionPublicaciones(publicaciones)
    }

}
