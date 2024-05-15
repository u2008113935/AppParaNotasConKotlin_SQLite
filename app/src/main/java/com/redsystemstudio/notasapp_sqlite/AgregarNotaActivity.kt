package com.redsystemstudio.notasapp_sqlite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.redsystemstudio.notasapp_sqlite.databinding.ActivityAgregarNotaBinding

class AgregarNotaActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAgregarNotaBinding
    private lateinit var db : NotasDatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgregarNotaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotasDatabaseHelper(this)

        binding.ivGuardarNota.setOnClickListener {
            val titulo = binding.etTitulo.text.toString()
            val descripcion = binding.etDescripcion.text.toString()

            if (!titulo.isEmpty() && !descripcion.isEmpty()){
                guardarNota(titulo, descripcion)
            }else{
                Toast.makeText(applicationContext, "Llene los campos",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun guardarNota(titulo : String, descripcion : String){
        val nota = Nota(0, titulo , descripcion)
        db.insertNota(nota)
        startActivity(Intent(applicationContext, MainActivity::class.java))
        finishAffinity()
        Toast.makeText(applicationContext, "Se ha agregado la nota",Toast.LENGTH_SHORT).show()
    }
}