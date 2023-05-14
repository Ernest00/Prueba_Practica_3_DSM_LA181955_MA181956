package sv.edu.udb.prueba_practica_3_dsm_la181955_ma181956

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import sv.edu.udb.prueba_practica_3_dsm_la181955_ma181956.model.Automovil

class ClientActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var cardAdapter: CardAdapter
    private val dataList: MutableList<Automovil> = mutableListOf()
    private var managerAuto: Automovil?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client)

        recyclerView = findViewById(R.id.listAutos)
        recyclerView.layoutManager = LinearLayoutManager(this)
        cardAdapter = CardAdapter(dataList)
        recyclerView.adapter = cardAdapter

        // Obtén los datos de SQLite
        managerAuto = Automovil(this)
        val cursor: Cursor? = managerAuto!!.searchItemAll() // Obtén el cursor con los datos de SQLite

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // Obtén los valores de las columnas según tu estructura de base de datos
                    val id = cursor.getInt(0)
                    val modelo = cursor.getString(1)
                    val vin = cursor.getString(2)
                    val chasis = cursor.getString(3)
                    val motor = cursor.getString(4)
                    val asientos = cursor.getString(5)
                    val anio = cursor.getString(6)
                    val capacidad = cursor.getString(7)
                    val precio = cursor.getString(8)
                    val imagen = cursor.getString(9)
                    val descripcion = cursor.getString(10)
                    val idmarca = cursor.getString(11)
                    val idtipo = cursor.getString(12)
                    val idcolor = cursor.getString(13)


                    // Crea un objeto MarcaData con los valores
                    val marcaData = Automovil(id, modelo, vin,chasis,motor,asientos,anio,capacidad,precio,imagen,descripcion,idmarca,idtipo,idcolor)

                    // Agrega el objeto a la lista
                    dataList.add(marcaData)
                } while (cursor.moveToNext())
            }

        // Notifica al adaptador que los datos han cambiado
        cardAdapter.notifyDataSetChanged()

    }
}