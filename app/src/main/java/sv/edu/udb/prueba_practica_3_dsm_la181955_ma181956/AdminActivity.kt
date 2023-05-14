package sv.edu.udb.prueba_practica_3_dsm_la181955_ma181956

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import sv.edu.udb.prueba_practica_3_dsm_la181955_ma181956.model.Usuarios

class AdminActivity : AppCompatActivity() {

    private lateinit var btnLogout : CardView
    private lateinit var btnMarcas : CardView
    private lateinit var btnColores : CardView
    private lateinit var btnTipos : CardView
    private lateinit var btnAutos : CardView
    private lateinit var btnUsuarios : CardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        btnLogout = findViewById<CardView>(R.id.cdvLogout)
        btnLogout.setOnClickListener {
            Toast.makeText(
                this, "Sesión cerrada",
                Toast.LENGTH_LONG
            ).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnMarcas = findViewById<CardView>(R.id.cdvMarcas)
        btnMarcas.setOnClickListener {
            val intent = Intent(this, MarcasActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnColores = findViewById<CardView>(R.id.cdvColores)
        btnColores.setOnClickListener {
            val intent = Intent(this, ColoresActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnTipos = findViewById<CardView>(R.id.cdvTipos)
        btnTipos.setOnClickListener {
            val intent = Intent(this, TipoAutoActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnAutos = findViewById<CardView>(R.id.cdvAutos)
        btnAutos.setOnClickListener {
            val intent = Intent(this, AutomovilActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnUsuarios = findViewById<CardView>(R.id.cdvUsuarios)
        btnUsuarios.setOnClickListener {
            val intent = Intent(this, UsuariosActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}