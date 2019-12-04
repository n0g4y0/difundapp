package io.github.difundapp.firebase.auth

import android.app.Activity
import android.content.Context
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

const val SOLICITUD_CODIGO_LOGIN = 1000

class AuthManager {

    private val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    private val providers = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())

    fun IniciarFlujoLogin(activity: Activity) {
        activity.startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setIsSmartLockEnabled(false)
                .build(),
            SOLICITUD_CODIGO_LOGIN
        )
    }

    fun estaElUsuarioLogeado() = firebaseAuth.currentUser != null

    fun getUsuarioActual() = firebaseAuth.currentUser?.displayName ?: ""

    fun cerrarSesion(context: Context) {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
        val googleSignInClient = GoogleSignIn.getClient(context, googleSignInOptions)
        firebaseAuth.signOut()
        googleSignInClient.signOut()
    }


}