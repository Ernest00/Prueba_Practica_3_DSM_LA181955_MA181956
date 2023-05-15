package sv.edu.udb.prueba_practica_3_dsm_la181955_ma181956

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import sv.edu.udb.prueba_practica_3_dsm_la181955_ma181956.model.DatosActivos

class MisFavoritosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis_favoritos)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_client, menu)
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
            R.id.action_listaAutos -> {
                val intent = Intent(this, ClientActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.action_favoritos -> {
                val intent = Intent(this, MisFavoritosActivity::class.java)
                startActivity(intent)
                finish()
            }



        }
        return super.onOptionsItemSelected(item)
    }
}