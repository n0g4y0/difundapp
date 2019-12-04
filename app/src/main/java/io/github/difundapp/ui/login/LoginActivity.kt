package io.github.difundapp.ui.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.github.difundapp.R
import io.github.difundapp.firebase.auth.AuthManager
import io.github.difundapp.firebase.auth.SOLICITUD_CODIGO_LOGIN
import io.github.difundapp.ui.Enrutador
import io.github.difundapp.util.mostrarMensaje
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val router by lazy { Enrutador() }
    private val authenticationManager by lazy { AuthManager() }

    companion object {
        fun createIntent(context: Context) = Intent(context, LoginActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initialize()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SOLICITUD_CODIGO_LOGIN) {

            if (resultCode == Activity.RESULT_OK) {
                router.IniciarPantallaHome(this)
            } else {
                mostrarMensaje(getString(R.string.sign_in_failed))
            }
        }
    }

    private fun initialize() {
        setSupportActionBar(loginToolbar)
        continueToHomeScreenIfUserSignedIn()
        setupClickListeners()
    }

    private fun continueToHomeScreenIfUserSignedIn() = if (isUserSignedIn()) router.IniciarPantallaHome(this) else Unit

    private fun setupClickListeners() {
        googleSignInButton.setOnClickListener { authenticationManager.IniciarFlujoLogin(this) }
    }

    private fun isUserSignedIn() = authenticationManager.estaElUsuarioLogeado()
}
