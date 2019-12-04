package io.github.difundapp.util

import android.app.Activity
import android.widget.Toast

fun Activity.mostrarMensaje(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()