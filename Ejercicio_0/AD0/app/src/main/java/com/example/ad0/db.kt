package com.example.ad0

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.Period


class db {
    suspend fun prueba(): String {
        val db = FirebaseFirestore.getInstance()
        val salida = db.collection("Persona")
            .get()
            .await()
        println("Cantidad de documentos: ${salida.size()}")



        // Por ejemplo, devolver el nombre del primer documento
        return if (!salida.isEmpty) {
            salida.documents[0].getString("Nombre") ?: "No encontrado nada"
        } else {
            "No se ha obtenido resultado"
        }
    }
    suspend fun getNombrePorDNI(dni: String): String? {
        val db = FirebaseFirestore.getInstance()
        val salida = db.collection("Persona")
            .whereEqualTo("DNI-NIE",dni)
            .get()
            .await()
        return if(!salida.isEmpty){
            salida.documents[0].getString("Nombre")
        }else{
            null
        }
    }
    suspend fun getUsuariosPorNombre(nombre: String): List<String>{
        val db = FirebaseFirestore.getInstance()
        val salida = db.collection("Persona")
            .whereEqualTo("Nombre",nombre)
            .get()
            .await()
        return salida.documents.mapNotNull {
            "${it.getString("Nombre")} ${it.getString("Apellido")}"
        }
    }



    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getMenoresDe(edadMax: Int): List<String>{
        val db = FirebaseFirestore.getInstance()
        val salida = db.collection("Persona")
            .get()
            .await()
        val hoy = LocalDate.now()
        return salida.documents.mapNotNull {
            val fecha = it.getString("Fecha de naciniento")?.let { LocalDate.parse(it) }
            if(fecha !=null){
                val edad = Period.between(fecha, hoy).years
                if (edad< edadMax)"${it.getString("Nombre")} ${it.getString("Apellido")}" else null
            } else null
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getMayoresOIgualDe(edadMin: Int): List<String>{
        val db = FirebaseFirestore.getInstance()
        val salida = db.collection("Persona")
            .get()
            .await()
        val hoy = LocalDate.now()
        return salida.documents.mapNotNull {
            val fecha = it.getString("Fecha de naciniento")?.let { LocalDate.parse(it) }
            if(fecha !=null){
                val edad = Period.between(fecha, hoy).years
                if(edad >= edadMin)"${it.getString("Nombre")} ${it.getString("Apellido")}" else null
            }else null
        }
    }
    suspend fun  getUsuariosVascos(): List<String>{
        val provinciasVascas = listOf("Bizkaia","Vitoria","Gipuzkoa")
        val db = FirebaseFirestore.getInstance()
        val salida = db.collection("Persona")
            .whereIn("Provincia",provinciasVascas)
            .get()
            .await()
        return salida.documents.mapNotNull {
            "${it.getString("Nombre")} ${it.getString("Apellido")}"
        }
    }
    suspend fun getUsuariosNoVascos(): List<String>{
        val provinciasVascar = listOf("Bizkaia", "Vitoria","Gipuzkoa")
        val db = FirebaseFirestore.getInstance()
        val salida = db.collection("Persona")
            .get()
            .await()
        return salida.documents.mapNotNull {
            val provincia = it.getString("Provincia")
            if(provincia !=null && provincia !in provinciasVascar){
                "${it.getString("Nombre")} ${it.getString("Apellido")}"
            } else null
        }
    }
}



