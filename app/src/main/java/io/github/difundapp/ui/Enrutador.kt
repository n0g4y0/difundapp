package io.github.difundapp.ui

import android.app.Activity
import io.github.difundapp.ui.addPublicacion.AddPublicacionActivity
import io.github.difundapp.ui.home.HomeActivity
import io.github.difundapp.ui.login.LoginActivity

class Enrutador {

    fun IniciarPantallaHome(activity: Activity) {
        val intent = HomeActivity.crearIntent(activity)
        activity.startActivity(intent)
    }

    fun IniciarPantallaLogin(activity: Activity) {
        val intent = LoginActivity.createIntent(activity)
        activity.startActivity(intent)
    }

    fun startAddPostScreen(activity: Activity) {
        val intent = AddPublicacionActivity.crearIntent(activity)
        activity.startActivity(intent)
    }


}