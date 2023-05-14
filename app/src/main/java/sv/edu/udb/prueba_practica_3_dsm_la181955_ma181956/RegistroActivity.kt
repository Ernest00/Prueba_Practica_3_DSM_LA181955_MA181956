package sv.edu.udb.prueba_practica_3_dsm_la181955_ma181956

import MyDatabaseHelper
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import sv.edu.udb.prueba_practica_3_dsm_la181955_ma181956.model.Usuarios

class RegistroActivity : AppCompatActivity() , View.OnClickListener{
    private var txtNombres: EditText? = null
    private var txtApellidos: EditText? = null
    private var txtEmail: EditText? = null
    private var txtUsuario: EditText? = null
    private var txtPass: EditText? = null

    private var btnIniciar: Button? = null
    private var btnRegistrar: Button? = null
    private var cursor: Cursor? = null
    private lateinit var databaseHelper: MyDatabaseHelper
    private var db: SQLiteDatabase? = null

    private var managerUsuarios: Usuarios? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        txtNombres = findViewById(R.id.txtNombres)
        txtApellidos = findViewById(R.id.txtApellidos)
        txtEmail = findViewById(R.id.txtEmail)
        txtUsuario = findViewById(R.id.txtUsuario)
        txtPass = findViewById(R.id.txtPass)

        btnIniciar = findViewById(R.id.btnIniciar)
        btnRegistrar = findViewById(R.id.btnRegistrar)

        btnIniciar!!.setOnClickListener(this)
        btnRegistrar!!.setOnClickListener(this)

        databaseHelper = MyDatabaseHelper(this)
        db = databaseHelper!!.writableDatabase
    }


    override fun onClick(view: View) {
        managerUsuarios = Usuarios(this)

        val nombres : String = txtNombres!!.text.toString().trim()
        val apellidos : String = txtApellidos!!.text.toString().trim()
        val email : String = txtEmail!!.text.toString().trim()
        val usuario: String = txtUsuario!!.text.toString().trim()
        val pass: String = txtPass!!.text.toString().trim()


        if(db!=null){

            if (view === btnRegistrar) {
                if (vericarFormulario()) {
                    managerUsuarios!!.addNewUsuario(
                        nombres,apellidos,email,usuario,pass, "CLIENT"
                    )
                    Toast.makeText(
                        this, "Se ha registrado, puede iniciar sesión",
                        Toast.LENGTH_LONG
                    ).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            }

        if (view === btnIniciar) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }


    }

    private fun vericarFormulario(): Boolean {
        var nombres : String = txtNombres!!.text.toString().trim()
        var apellidos : String = txtApellidos!!.text.toString().trim()
        var email : String = txtEmail!!.text.toString().trim()
        var usuario: String = txtUsuario!!.text.toString().trim()
        var password: String = txtPass!!.text.toString().trim()

        var response = true
        var nombres_v = true
        var apellidos_v = true
        var email_v = true
        var usuario_v = true
        var pass_v = true

        var notificacion: String = "Se han generado algunos errores, favor verifiquelos"

        if (nombres.isEmpty()) {
            txtNombres!!.error = "Ingrese nombres"
            txtNombres!!.requestFocus()
            nombres_v = false
        }
        if (apellidos.isEmpty()) {
            txtApellidos!!.error = "Ingrese apellidos"
            txtApellidos!!.requestFocus()
            apellidos_v = false
        }
        if (email.isEmpty()) {
            txtEmail!!.error = "Ingrese el email"
            txtEmail!!.requestFocus()
            email_v = false
        }else{
            val emailPattern = Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
            txtEmail!!.error = "No ha ingresado un email en formato correcto"
            email_v = emailPattern.matches(email)
        }

        if (usuario.isEmpty()) {
            txtUsuario!!.error = "Ingrese el nombre del usuario"
            txtUsuario!!.requestFocus()
            usuario_v = false
        }
        if (password.isEmpty()) {
            txtPass!!.error = "Ingrese la password"
            txtPass!!.requestFocus()
            pass_v = false
        }

        response = !(nombres_v == false ||apellidos_v == false ||email_v == false ||usuario_v == false || pass_v == false )
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
}