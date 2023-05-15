package sv.edu.udb.prueba_practica_3_dsm_la181955_ma181956

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import sv.edu.udb.prueba_practica_3_dsm_la181955_ma181956.model.DatosActivos

class AdminActivity : AppCompatActivity() {

    private lateinit var btnLogout : CardView
    private lateinit var btnMarcas : CardView
    private lateinit var btnColores : CardView
    private lateinit var btnTipos : CardView
    private lateinit var btnAutos : CardView
    private lateinit var btnUsuarios : CardView
    private lateinit var tvActivo : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        val extras = intent.extras

        if(extras!=null){
            DatosActivos.usuarioActivo = extras?.getString("useractivo").toString()
            DatosActivos.idActivo    = extras?.getInt("idactivo").toString()

            Log.d("PP", DatosActivos.usuarioActivo  + ","
                    + DatosActivos.idActivo  )
        }

        tvActivo = findViewById<TextView>(R.id.tvActivo)
        tvActivo.text = "Bienvenido ${DatosActivos.usuarioActivo}"

        btnLogout = findViewById<CardView>(R.id.cdvLogout)
        btnLogout.setOnClickListener {
            Toast.makeText(
                this, "Sesión cerrada",
                Toast.LENGTH_LONG
            ).show()
            DatosActivos.usuarioActivo = ""
            DatosActivos.idActivo    = ""

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


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_sign_out -> {
                Toast.makeText(
                    this, "Sesión cerrada",
                    Toast.LENGTH_LONG
                ).show()
                DatosActivos.usuarioActivo = ""
                DatosActivos.idActivo    = ""

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.action_marcas -> {
                val intent = Intent(this, MarcasActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.action_colores -> {
                val intent = Intent(this, ColoresActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.action_tipos -> {
                val intent = Intent(this, TipoAutoActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.action_automoviles -> {
                val intent = Intent(this, AutomovilActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.action_usuarios -> {
                val intent = Intent(this, UsuariosActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.action_listaAutos -> {
                val intent = Intent(this, ClientActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.action_favoritos -> {
                val intent = Intent(this, MarcasActivity::class.java)
                startActivity(intent)
                finish()
            }

        }
        return super.onOptionsItemSelected(item)
    }
}