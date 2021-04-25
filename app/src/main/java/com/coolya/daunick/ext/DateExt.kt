package com.coolya.daunick.ext

import java.text.SimpleDateFormat
import java.util.*


fun Long.toStringDate(): String =
    SimpleDateFormat("dd.MM HH:mm", Locale("ru")).run { format(Date(this@toStringDate)) }