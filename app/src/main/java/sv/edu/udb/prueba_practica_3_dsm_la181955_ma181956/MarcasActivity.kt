package sv.edu.udb.prueba_practica_3_dsm_la181955_ma181956

import MyDatabaseHelper
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import sv.edu.udb.prueba_practica_3_dsm_la181955_ma181956.model.Marcas
import com.google.android.material.snackbar.Snackbar


class MarcasActivity : AppCompatActivity(),  View.OnClickListener {
    private var managerMarcas: Marcas? = null

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
        setContentView(R.layout.activity_marcas)

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
        managerMarcas = Marcas(this)
        val nombre: String = txtNombre!!.text.toString().trim()
        val idSel = txtId!!.text.toString().trim()

        if (db != null) {
            if (view === btnAgregar) {
                if (vericarFormulario("insertar")) {
                    managerMarcas!!.addNewItem(
                        nombre
                    )
                    mostrarMensajesArriba(view, "Marca agregada")

                }
            } else if (view === btnActualizar) {
                if (vericarFormulario("actualizar")) {
                    managerMarcas!!.updateItem(
                        idSel.toInt(),
                        nombre
                    )
/*                    Toast.makeText(
                        this, "Marca actualizada",
                        Toast.LENGTH_LONG
                    ).show()
  */                  mostrarMensajesArriba(view, "Marca actualizada")

                }
            } else if (view === btnEliminar) {
                if (vericarFormulario("eliminar")) {
// manager.eliminar(1);
                    managerMarcas!!.deleteItem(idSel.toInt())

                    mostrarMensajesArriba(view, "Marca eliminada")

                }
            } else if (view === btnBuscar) {

                if(vericarFormulario("buscar")){
                    cursor= managerMarcas!!.searchItem(idSel.toInt())

                    if(cursor !=null && cursor!!.count>0){
                        cursor!!.moveToFirst()

                        txtIdDB!!.text = (cursor!!.getInt(0).toString())
                        txtNombre!!.setText(cursor!!.getString(2))

                        Log.d("INFORMACION", cursor!!.getInt(1).toString())
                        mostrarMensajesArriba(view, "Cargando Información")

                    }else{

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
                txtNombre!!.error = "Ingrese el nombre de la marca"
                txtNombre!!.requestFocus()
                nombre_v = false
            }
            if (opc == "actualizar") {
                if (idproducto.isEmpty()) {
                    idseleccionado_v = false
                    notificacion = "No se ha seleccionado una marca"
                }
                response = !(nombre_v == false || idseleccionado_v == false)
            } else {
                response = !(nombre_v == false)
            }
        } else if (opc === "eliminar" || opc == "buscar") {
            if (idproducto.isEmpty()) {
                response = false
                notificacion = "No se ha seleccionado una marca"
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

}