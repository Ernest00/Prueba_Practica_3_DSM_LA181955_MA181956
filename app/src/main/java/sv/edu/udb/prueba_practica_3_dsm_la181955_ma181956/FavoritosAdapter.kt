package sv.edu.udb.prueba_practica_3_dsm_la181955_ma181956

import MyDatabaseHelper
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import sv.edu.udb.prueba_practica_3_dsm_la181955_ma181956.model.Autos
import sv.edu.udb.prueba_practica_3_dsm_la181955_ma181956.model.DatosActivos
import sv.edu.udb.prueba_practica_3_dsm_la181955_ma181956.model.Favoritos

class FavoritosAdapter(private val context: Context, private val profesors: ArrayList<String>, private val fechas: ArrayList<String>) :
    RecyclerView.Adapter<FavoritosAdapter.ViewHolder>() {
    var i: Int = 0
    var dbHelper: MyDatabaseHelper? = null
    var db: SQLiteDatabase? = null
    var cursor: Cursor? = null

    lateinit var listaCompleta: ArrayList<String>
    lateinit var listaID: ArrayList<String>
    lateinit var listaFechas: ArrayList<String>

    private var onItemClick: OnItemClickListener? = null
    private var managerFavoritos: Favoritos? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombreFechaView: TextView = view.findViewById(R.id.tvFecha)
        val nombreTextView: TextView = view.findViewById(R.id.tvModel)
        val datosTextView: TextView = view.findViewById(R.id.tvDatos)
        val ivImagen: ImageView = view.findViewById(R.id.ivImagenCarro)
        val btnEliminar: Button = view.findViewById(R.id.EliminarButton)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        listaCompleta = profesors
        listaID = ArrayList<String>()
        for (i in 0 until listaCompleta.size step 17) {
            listaID.add(listaCompleta[i])
        }

        dbHelper = MyDatabaseHelper(context)
        db = dbHelper!!.writableDatabase
        managerFavoritos = Favoritos(context)

        listaFechas = fechas

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.favoritos_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        listaID.add(profesors[0 + 17 * i])

        holder.nombreFechaView.text = "Agregado a favoritos : " + listaFechas[position]

        holder.nombreTextView.text =
            profesors[14 + 17 * i] + " " + profesors[1 + 17 * i] + ", Año " + profesors[6 + 17 * i] + "\n" +
                    "Precio: $" + profesors[8 + 17 * i]
        holder.datosTextView.text =
            "Tipo: ${profesors[15 + 17 * i]}\nColor: ${profesors[16 + 17 * i]}\nN° vin:  + ${profesors[2 + 17 * i]} \nN° chasis: ${profesors[3 + 17 * i]} \n" +
                    "N° motor:${profesors[4 + 17 * i]} \nN° Asientos: ${profesors[5 + 17 * i]},  Capacidad: ${profesors[7 + 17 * i]}ton.\n" +
                    "Descripción: ${profesors[10 + 17 * i]}"

        try {
            Picasso.get().load(profesors[9 + 17 * i].toUri()).into(holder.ivImagen)
        } catch (e: Exception) {
            holder.ivImagen.setImageResource(R.drawable.auto)
            e.printStackTrace()
        }


        holder.btnEliminar.setOnClickListener {
            Log.d("Jp", "IDS ${listaID.toString()}")
            Log.d("Jp2", "ID Seleccionado? ${listaID[position].toString()}")

            //Toast.makeText(context, "ID Seleccionado${listaID[position].toString()}", Toast.LENGTH_SHORT).show()
            managerFavoritos = Favoritos(context)
            val usuario = DatosActivos.idActivo?.toInt()
            val auto = listaID[position].toInt()

            val IdSel = managerFavoritos!!.GetIdToEliminate(usuario!!, auto)

            managerFavoritos!!.deleteItem(IdSel)
            Toast.makeText(context, "Eliminado de favoritos, recargue la vista para ver los cambios", Toast.LENGTH_SHORT).show()


        }

        i++
    }

    override fun getItemCount(): Int {
        return profesors.size / 17
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClick = listener
    }

    interface OnItemClickListener {
        fun onItemClick(profesor: Autos)
    }

}
