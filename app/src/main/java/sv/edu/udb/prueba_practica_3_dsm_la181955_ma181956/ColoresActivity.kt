package sv.edu.udb.prueba_practica_3_dsm_la181955_ma181956

import MyDatabaseHelper
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import sv.edu.udb.prueba_practica_3_dsm_la181955_ma181956.model.Colores
import sv.edu.udb.prueba_practica_3_dsm_la181955_ma181956.model.DatosActivos


class ColoresActivity : AppCompatActivity(),  View.OnClickListener {
    private var managerColores: Colores? = null

    private var dbHelper: MyDatabaseHelper? = null
    private var db: SQLiteDatabase? = null
    private var cursor: Cursor? = null

    private var txtIdDB: TextView? = null
    private var txtId: EditText? = null
    private var txtNombre: EditText? = null

    private var btnAgregar: Button? = null
    private var btnActualizar: Button? = null
    private var btnEliminar: Button? = null
    private var btnBuscar: Button? = null
    private var btnVolver : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_colores)//activity

        txtIdDB = findViewById(R.id.txtIdDB)
        txtId = findViewById(R.id.txtId)
        txtNombre = findViewById(R.id.txtNombre)

        btnAgregar = findViewById(R.id.btnAgregar)
        btnActualizar = findViewById(R.id.btnActualizar)
        btnEliminar = findViewById(R.id.btnEliminar)
        btnBuscar = findViewById(R.id.btnBuscar)
        btnVolver = findViewById(R.id.btnVolver)

        dbHelper = MyDatabaseHelper(this)
        db = dbHelper!!.writableDatabase

        btnAgregar!!.setOnClickListener(this)
        btnActualizar!!.setOnClickListener(this)
        btnEliminar!!.setOnClickListener(this)
        btnBuscar!!.setOnClickListener(this)
        btnVolver!!.setOnClickListener(this)
    }


    override fun onClick(view: View) {
        managerColores = Colores(this)
        val nombre: String = txtNombre!!.text.toString().trim()
        val idSel = txtId!!.text.toString().trim()

        if (db != null) {
            if (view === btnAgregar) {
                if (vericarFormulario("insertar")) {
                    managerColores!!.addNewItem(
                        nombre
                    )
                    mostrarMensajesArriba(view, "Color agregado")
                    txtNombre!!.setText("")
                }
            } else if (view === btnActualizar) {
                if (vericarFormulario("actualizar")) {
                    managerColores!!.updateItem(
                        idSel.toInt(),
                        nombre
                    )
                      mostrarMensajesArriba(view, "Color actualizado")
                    txtNombre!!.setText("")
                    txtId!!.setText("")

                }
            } else if (view === btnEliminar) {
                if (vericarFormulario("eliminar")) {

                    managerColores!!.deleteItem(idSel.toInt())

                    mostrarMensajesArriba(view, "Color eliminado")
                    txtNombre!!.setText("")
                    txtId!!.setText("")

                }
            } else if (view === btnBuscar) {

                if(vericarFormulario("buscar")){
                    cursor= managerColores!!.searchItem(idSel.toInt())

                    if(cursor !=null && cursor!!.count>0){
                        cursor!!.moveToFirst()

                        txtIdDB!!.text = (cursor!!.getInt(0).toString())
                        txtNombre!!.setText(cursor!!.getString(2))

                        Log.d("INFORMACION", cursor!!.getInt(1).toString())
                        mostrarMensajesArriba(view, "Cargando Información")

                    }else{
                        txtNombre!!.setText("")
                        mostrarMensajesArriba(view, "No se encontraron registros")
                    }

                }


            }else if (view === btnVolver) {
                val intent = Intent(this, AdminActivity::class.java)
                startActivity(intent)
                finish()

            } else {

                mostrarMensajesArriba(view, "No se puede conectar a la Base de Datos")
            }

            if (view === btnVolver) {
                val intent = Intent(this, AdminActivity::class.java)
                startActivity(intent)
                finish()

            }
        }


    }

    private fun vericarFormulario(opc: String): Boolean {
        var notificacion: String = "Se han generado algunos errores, favor verifiquelos"
        var response = true

        var nombre_v = true
        var idseleccionado_v = true

        val nombre: String = txtNombre!!.text.toString().trim()

        val idproducto: String = txtId!!.text.toString().trim()
        if (opc === "insertar" || opc == "actualizar") {
            if (nombre.isEmpty()) {
                txtNombre!!.error = "Ingrese la descripción del color"
                txtNombre!!.requestFocus()
                nombre_v = false
            }
            if (opc == "actualizar") {
                if (idproducto.isEmpty()) {
                    idseleccionado_v = false
                    notificacion = "No se ha seleccionado un color"
                }
                response = !(nombre_v == false || idseleccionado_v == false)
            } else {
                response = !(nombre_v == false)
            }
        } else if (opc === "eliminar" || opc == "buscar") {
            if (idproducto.isEmpty()) {
                response = false
                notificacion = "No se ha seleccionado un color"
            }
        }
//Mostrar errores
        if (response == false) {
            Toast.makeText(
                this,
                notificacion,
                Toast.LENGTH_LONG
            ).show()
        }
        return response
    }

    fun mostrarMensajesArriba(view:View, mensaje: String){
        val snackbar = Snackbar.make(view, mensaje, Snackbar.LENGTH_LONG)
        val layoutParams = snackbar.view.layoutParams as FrameLayout.LayoutParams
        layoutParams.gravity = Gravity.TOP
        snackbar.view.layoutParams = layoutParams
        snackbar.show()
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