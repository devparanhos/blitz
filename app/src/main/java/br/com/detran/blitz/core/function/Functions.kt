package br.com.detran.blitz.core.function

import android.content.Context
import android.graphics.Bitmap
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import java.lang.Math.atan2
import java.lang.Math.cos
import java.lang.Math.sin
import java.lang.Math.sqrt
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun bitmapDescriptorFromVector(
    context: Context,
    vectorResId: Int
): BitmapDescriptor? {
    val drawable = ContextCompat.getDrawable(context, vectorResId) ?: return null
    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
    val bm = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )

    val canvas = android.graphics.Canvas(bm)
    drawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bm)
}

fun calculateDistanceBetweenPoints(latBlitz: Double, longBlitz: Double, latLocation: Double, longLocation: Double) : Double {
    val earthRadius = 6371.0
    val distanceLat = Math.toRadians(latLocation - latBlitz)
    val distanceLong = Math.toRadians(longLocation - longBlitz)

    val a = sin(distanceLat / 2) * sin(distanceLat / 2) +
            cos(Math.toRadians(latBlitz)) * cos(Math.toRadians(latLocation)) *
            sin(distanceLong / 2) * sin(distanceLong / 2)

    val c = 2 * atan2(sqrt(a), sqrt(1 - a))

    return earthRadius * c
}