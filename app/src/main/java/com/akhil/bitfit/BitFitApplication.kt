package com.akhil.bitfit

import android.app.Application

class BitFitApplication : Application()  {
    val db by lazy {AppDatabase.getInstance(this)}
}