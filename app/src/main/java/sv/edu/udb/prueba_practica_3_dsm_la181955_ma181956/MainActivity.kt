package sv.edu.udb.prueba_practica_3_dsm_la181955_ma181956

import MyDatabaseHelper
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import sv.edu.udb.prueba_practica_3_dsm_la181955_ma181956.model.Usuarios

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var txtUsuario: EditText? = null
    private var txtPass: EditText? = null
    private var btnIniciar: Button? = null
    private var btnRegistrar: Button? = null
    private var cursor: Cursor? = null
    private var db: SQLiteDatabase? = null
    private lateinit var databaseHelper: MyDatabaseHelper
    private var managerUsuarios: Usuarios? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtUsuario = findViewById(R.id.txtUsuario)
        txtPass = findViewById(R.id.txtPass)
        btnIniciar = findViewById(R.id.btnIniciar)
        btnRegistrar = findViewById(R.id.btnRegistrar)

        btnIniciar!!.setOnClickListener(this)
        btnRegistrar!!.setOnClickListener(this)

        // Inicializar el helper de la base de datos
        databaseHelper = MyDatabaseHelper(this)

        // Acceder a la base de datos
        val database = databaseHelper.readableDatabase
        db = databaseHelper!!.writableDatabase

    }

    override fun onClick(view: View) {
        managerUsuarios = Usuarios(this)

        val usuario: String = txtUsuario!!.text.toString().trim()
        val pass: String = txtPass!!.text.toString().trim()
        if(db!=null){
            if (view === btnRegistrar) {

                val intent = Intent(this, RegistroActivity::class.java)
                startActivity(intent)
                finish()

            } else if (view === btnIniciar) {
                if (vericarFormulario()) {

                    cursor= managerUsuarios!!.searchUsuario(usuario)

                    if(cursor !=null && cursor!!.count>0){
                        cursor!!.moveToFirst()
                        Toast.makeText(this, "Iniciando Sesion", Toast.LENGTH_LONG).show()

                        val tip = cursor!!.getColumnIndex("tipo")
                        if(cursor!!.getString(tip)== "ADMIN" ){
                            val intent = Intent(this, AdminActivity::class.java)
                            intent.putExtra("useractivo", cursor!!.getString(4))
                            intent.putExtra("idactivo", cursor!!.getInt(0))

                            startActivity(intent)
                            finish()
                        }else{
                            val intent = Intent(this, ClientActivity::class.java)
                            intent.putExtra("useractivo", cursor!!.getString(4))
                            intent.putExtra("idactivo", cursor!!.getInt(0))

                            startActivity(intent)
                            finish()
                        }
                    }else{
                        Toast.makeText(this, "No se encontro el usuario o la contraseña", Toast.LENGTH_LONG).show()
                    }

                }
            }
        }


    }

    private fun vericarFormulario(): Boolean {
        var usuario: String = txtUsuario!!.text.toString().trim()
        var password: String = txtPass!!.text.toString().trim()

        var response = true
        var usuario_v = true
        var pass_v = true

        var notificacion: String = "Se han generado algunos errores, favor verifiquelos"


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

        response = !(usuario_v == false || pass_v == false )
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