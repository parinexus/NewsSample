package com.parinexus.presentation.utils


fun String.encode(): String = java.net.URLEncoder.encode(this, "UTF-8")
fun String.decode(): String = java.net.URLDecoder.decode(this, "UTF-8")
