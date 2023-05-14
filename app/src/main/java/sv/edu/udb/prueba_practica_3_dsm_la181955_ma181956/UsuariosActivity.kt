package sv.edu.udb.prueba_practica_3_dsm_la181955_ma181956

import MyDatabaseHelper
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import sv.edu.udb.prueba_practica_3_dsm_la181955_ma181956.model.Usuarios


class UsuariosActivity : AppCompatActivity(), View.OnClickListener {
    private var managerUsuarios: Usuarios? = null

    private var dbHelper: MyDatabaseHelper? = null
    private var db: SQLiteDatabase? = null
    private var cursor: Cursor? = null

    private var txtIdDB: TextView? = null
    private var txtId: EditText? = null
    private var txtNombres: EditText? = null
    private var txtApellidos: EditText? = null
    private var txtEmail: EditText? = null
    private var txtUsuario: EditText? = null
    private var txtPass: EditText? = null

    private var cmbUsuarios: Spinner? = null

    private var btnAgregar: Button? = null
    private var btnActualizar: Button? = null
    private var btnEliminar: Button? = null
    private var btnBuscar: Button? = null
    private var btnVolver: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuarios)//activity

        txtIdDB = findViewById(R.id.txtIdDB)
        txtId = findViewById(R.id.txtId)

        txtNombres = findViewById(R.id.txtNombres)
        txtApellidos = findViewById(R.id.txtApellidos)
        txtEmail = findViewById(R.id.txtEmail)
        txtUsuario = findViewById(R.id.txtUser)
        txtPass = findViewById(R.id.txtPassword)

        cmbUsuarios = findViewById<Spinner>(R.id.cmbUsuario)

        btnAgregar = findViewById(R.id.btnAgregar)
        btnActualizar = findViewById(R.id.btnActualizar)
        btnEliminar = findViewById(R.id.btnEliminar)
        btnBuscar = findViewById(R.id.btnBuscar)
        btnVolver = findViewById(R.id.btnVolver)

        dbHelper = MyDatabaseHelper(this)
        db = dbHelper!!.writableDatabase

        setSpinnerUsuarios()

        btnAgregar!!.setOnClickListener(this)
        btnActualizar!!.setOnClickListener(this)
        btnEliminar!!.setOnClickListener(this)
        btnBuscar!!.setOnClickListener(this)
        btnVolver!!.setOnClickListener(this)
    }

    private fun setSpinnerUsuarios() {
        val spinnerValues = arrayOf("Administrador", "Cliente")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerValues)
        cmbUsuarios!!.adapter = adapter
    }


    override fun onClick(view: View) {
        managerUsuarios = Usuarios(this)

        val nombres: String = txtNombres!!.text.toString().trim()
        val apellidos: String = txtApellidos!!.text.toString().trim()
        val email: String = txtEmail!!.text.toString().trim()
        val usuario: String = txtUsuario!!.text.toString().trim()
        val pass: String = txtPass!!.text.toString().trim()

        val tipo: String = cmbUsuarios!!.selectedItem.toString().trim()
        var tipoC = ""
        if (tipo == "Administrador") {
            tipoC = "ADMIN"
        } else {
            tipoC = "CLIENT"
        }

        val idSel = txtId!!.text.toString().trim()

        if (db != null) {
            if (view === btnAgregar) {
                if (vericarFormulario("insertar")) {
                    managerUsuarios!!.addNewUsuario(
                        nombres, apellidos, email, usuario, pass, tipoC

                    )

                    mostrarMensajesArriba(view, "Usuario agregado")

                }
            } else if (view === btnActualizar) {
                if (vericarFormulario("actualizar")) {
                    managerUsuarios!!.updateUsuario(
                        idSel.toInt(),
                        nombres, apellidos, email, usuario, pass, tipoC
                    )
                    txtId!!.setText("")
                    mostrarMensajesArriba(view, "Usuario actualizado")

                }
            } else if (view === btnEliminar) {
                if (vericarFormulario("eliminar")) {
// manager.eliminar(1);
                    managerUsuarios!!.deleteUsuario(idSel.toInt())
                    txtId!!.setText("")
                    mostrarMensajesArriba(view, "Usuario eliminado")

                }
            } else if (view === btnBuscar) {

                if (vericarFormulario("buscar")) {
                    cursor = managerUsuarios!!.searchUser(idSel.toInt())

                    if (cursor != null && cursor!!.count > 0) {
                        cursor!!.moveToFirst()
                        var label_tipo = cursor!!.getString(6)

                        txtIdDB!!.text = (cursor!!.getInt(0).toString())
                        txtNombres!!.setText(cursor!!.getString(1))
                        txtApellidos!!.setText(cursor!!.getString(2))
                        txtEmail!!.setText(cursor!!.getString(3))
                        txtUsuario!!.setText(cursor!!.getString(4))
                        txtPass!!.setText(cursor!!.getString(5))

                        if (label_tipo == "ADMIN") {
                            cmbUsuarios!!.setSelection(0)
                        } else {
                            cmbUsuarios!!.setSelection(1)
                        }

                        Log.d("INFORMACION", cursor!!.getInt(1).toString())

                        mostrarMensajesArriba(view, "Cargando Información")

                    } else {

                        mostrarMensajesArriba(view, "No se encontraron registros")
                    }

                }


            } else if (view === btnVolver) {
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

    fun Spinner.setSelectionByValue(value: Any) {
        val adapter = this.adapter
        for (i in 0 until adapter.count) {
            val item = adapter.getItem(i)
            if (item == value || item.toString() == value.toString()) {
                this.setSelection(i)
                break
            }
        }
    }


    private fun vericarFormulario(opc: String): Boolean {
        var notificacion: String = "Se han generado algunos errores, favor verifiquelos"
        var response = true

        var nombres_v = true
        var apellidos_v = true
        var email_v = true
        var usuario_v = true
        var password_v = true


        var idseleccionado_v = true

        var nombres: String = txtNombres!!.text.toString().trim()
        var apellidos: String = txtApellidos!!.text.toString().trim()
        var email: String = txtEmail!!.text.toString().trim()
        var usuario: String = txtUsuario!!.text.toString().trim()
        var password: String = txtPass!!.text.toString().trim()


        val idproducto: String = txtId!!.text.toString().trim()
        if (opc === "insertar" || opc == "actualizar") {
            if (nombres.isEmpty()) {
                txtNombres!!.error = "Ingrese el nombre"
                txtNombres!!.requestFocus()
                nombres_v = false
            }
            if (apellidos.isEmpty()) {
                txtApellidos!!.error = "Ingrese el apellido"
                txtApellidos!!.requestFocus()
                apellidos_v = false
            }

            if (email.isEmpty()) {
                txtEmail!!.error = "Ingrese el email"
                txtEmail!!.requestFocus()
                email_v = false
            }

            if (usuario.isEmpty()) {
                txtUsuario!!.error = "Ingrese el usuario"
                txtUsuario!!.requestFocus()
                usuario_v = false
            }

            if (password.isEmpty()) {
                txtPass!!.error = "Ingrese la contraseña "
                txtPass!!.requestFocus()
                password_v = false
            }

            if (opc == "actualizar") {
                if (idproducto.isEmpty()) {
                    idseleccionado_v = false
                    notificacion = "No se ha seleccionado un automovil"
                }
                response =
                    !(nombres_v == false || apellidos_v == false || email_v == false || usuario_v == false || password_v == false || idseleccionado_v == false)
            } else {
                response =
                    !(nombres_v == false || apellidos_v == false || email_v == false || usuario_v == false || password_v == false)
            }
        } else if (opc === "eliminar" || opc == "buscar") {
            if (idproducto.isEmpty()) {
                response = false
                notificacion = "No se ha seleccionado un usuario"
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

    fun mostrarMensajesArriba(view: View, mensaje: String) {
        val snackbar = Snackbar.make(view, mensaje, Snackbar.LENGTH_LONG)
        val layoutParams = snackbar.view.layoutParams as FrameLayout.LayoutParams
        layoutParams.gravity = Gravity.TOP
        snackbar.view.layoutParams = layoutParams
        snackbar.show()
    }


}