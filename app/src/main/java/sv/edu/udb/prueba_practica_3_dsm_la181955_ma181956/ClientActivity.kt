package sv.edu.udb.prueba_practica_3_dsm_la181955_ma181956

import MyDatabaseHelper
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import sv.edu.udb.prueba_practica_3_dsm_la181955_ma181956.model.Automovil
import sv.edu.udb.prueba_practica_3_dsm_la181955_ma181956.model.Autos
import sv.edu.udb.prueba_practica_3_dsm_la181955_ma181956.model.DatosActivos


class ClientActivity : AppCompatActivity() {

    private var managerAuto: Automovil?= null
    private var dbHelper: MyDatabaseHelper? = null
    private var db: SQLiteDatabase? = null
    private var cursor: Cursor? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AutosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client)

        val extras = intent.extras

        if(extras!=null){
            DatosActivos.usuarioActivo = extras?.getString("useractivo").toString()
            DatosActivos.idActivo    = extras?.getInt("idactivo").toString()

            Log.d("PP", DatosActivos.usuarioActivo  + ","
                    + DatosActivos.idActivo  )
        }

        dbHelper = MyDatabaseHelper(this)
        db = dbHelper!!.readableDatabase

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

listarAutomoviles()

        }

    private fun listarAutomoviles() {
        managerAuto = Automovil(this)
        managerAuto!!.searchItemAll()
        cursor = managerAuto!!.showItemAll()
        var marca = ArrayList<String>()

        if (cursor != null && cursor!!.count > 0) {
            cursor!!.moveToFirst()
            do {
                marca.add(cursor!!.getString(0))//id
                marca.add(cursor!!.getString(1))//model
                marca.add(cursor!!.getString(2))//vin
                marca.add(cursor!!.getString(3))//chasis
                marca.add(cursor!!.getString(4))//motor
                marca.add(cursor!!.getString(5))//asientos
                marca.add(cursor!!.getString(6))//año
                marca.add(cursor!!.getString(7))//capacidad
                marca.add(cursor!!.getString(8))//precio
                marca.add(cursor!!.getString(9))//img
                marca.add(cursor!!.getString(10))//descr
                marca.add(cursor!!.getString(11))//marca
                marca.add(cursor!!.getString(12))//tipo
                marca.add(cursor!!.getString(13))//color


            } while (cursor!!.moveToNext())
        }

        adapter = AutosAdapter(marca)
        recyclerView!!.adapter = adapter

        println( marca.toList() + "\n")
        Log.d("SD", marca.toList().toString() + "\n")
        Log.d("SS", marca.toString() + "\n")
        Log.d("SA", adapter.toString())
    }



    }



