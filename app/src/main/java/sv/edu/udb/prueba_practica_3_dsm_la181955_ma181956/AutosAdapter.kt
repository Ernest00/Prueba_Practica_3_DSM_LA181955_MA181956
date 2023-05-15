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
import java.text.SimpleDateFormat
import java.util.Calendar

class AutosAdapter(private val context: Context, private val listAutos: ArrayList<String>) :
    RecyclerView.Adapter<AutosAdapter.ViewHolder>() {
    var i: Int = 0
    var dbHelper: MyDatabaseHelper? = null
    var db: SQLiteDatabase? = null
    var cursor: Cursor? = null

    lateinit var listaCompleta: ArrayList<String>
    lateinit var listaID: ArrayList<String>
    private var onItemClick: OnItemClickListener? = null
    private var managerFavoritos: Favoritos? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombreTextView: TextView = view.findViewById(R.id.tvModel)
        val datosTextView: TextView = view.findViewById(R.id.tvDatos)
        val ivImagen: ImageView = view.findViewById(R.id.ivImagenCarro)
        val btnFav: Button = view.findViewById(R.id.favoriteButton)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        listaCompleta = listAutos
        listaID = ArrayList<String>()
        for (i in 0 until listaCompleta.size step 17) {
            listaID.add(listaCompleta[i])
        }

        dbHelper = MyDatabaseHelper(context)
        db = dbHelper!!.writableDatabase
        managerFavoritos = Favoritos(context)

        val view = LayoutInflater.from(parent.context).inflate(R.layout.autos_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        listaID.add(listAutos[0 + 17 * i])

        holder.nombreTextView.text =
            listAutos[14 + 17 * i] + " " + listAutos[1 + 17 * i] + ", Año " + listAutos[6 + 17 * i] + "\n" + "Precio: $" + listAutos[8 + 17 * i]
        holder.datosTextView.text =
            "Tipo: ${listAutos[15 + 17 * i]}\nColor: ${listAutos[16 + 17 * i]}\nN° vin:  + ${listAutos[2 + 17 * i]} \nN° chasis: ${listAutos[3 + 17 * i]} \n" + "N° motor:${listAutos[4 + 17 * i]} \nN° Asientos: ${listAutos[5 + 17 * i]},  Capacidad: ${listAutos[7 + 17 * i]}ton.\n" + "Descripción: ${listAutos[10 + 17 * i]}"

        try {
            Picasso.get().load(listAutos[9 + 17 * i].toUri()).into(holder.ivImagen)
        } catch (e: Exception) {

            e.printStackTrace()
        }



        holder.btnFav.setOnClickListener {

            managerFavoritos = Favoritos(context)
            val usuario = DatosActivos.idActivo?.toInt()
            val auto = listaID[position].toInt()

            // Obtener la fecha actual en tiempo real
            val calendar = Calendar.getInstance()
            val currentDate = calendar.time

// Formatear la fecha en el formato deseado
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

            val fechaAutomatica = dateFormat.format(currentDate)/**/
            if (db != null) {
                var b: Boolean = true
                b = managerFavoritos!!.validateAdd(usuario!!, auto)
                if (b == false) {
                    managerFavoritos!!.addNewItem(
                        usuario, auto, fechaAutomatica
                    )

                    Toast.makeText(context, "Agregado a favoritos", Toast.LENGTH_SHORT).show()
                } else {

                    Toast.makeText(context, "Ya está en favoritos", Toast.LENGTH_SHORT).show()
                }


            }

        }


        i++
    }

    override fun getItemCount(): Int {
        return listAutos.size / 17
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClick = listener
    }

    interface OnItemClickListener {
        fun onItemClick(profesor: Autos)
    }

    fun mostrarMensajesArriba(view: View, mensaje: String) {
        val snackbar = Snackbar.make(view, mensaje, Snackbar.LENGTH_LONG)
        val layoutParams = snackbar.view.layoutParams as FrameLayout.LayoutParams
        layoutParams.gravity = Gravity.TOP
        snackbar.view.layoutParams = layoutParams
        snackbar.show()
    }
}
