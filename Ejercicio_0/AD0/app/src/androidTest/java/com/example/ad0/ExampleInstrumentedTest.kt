package com.example.ad0

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.runBlocking

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*


@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.ad0", appContext.packageName)
    }
    @Test
    fun getNombrePorDNI() = runBlocking {
        val miClase = db()
        val salida = miClase.getNombrePorDNI("73691453D")
        assertEquals("Funcion 1 ",salida)

    }
    @Test
    fun getUsuariosPorNombre() = runBlocking {
        FirebaseFirestore.setLoggingEnabled(true)

        val miClase = db()
        val salida = miClase.getUsuariosPorNombre("Naia")
        assertEquals(listOf("Naia Blanca"),salida)
    }
}

