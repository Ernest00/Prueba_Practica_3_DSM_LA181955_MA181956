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

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.autos_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val profesor = listAutos[position]

        val listaCompleta = listAutos

        val sublistas = listaCompleta.chunked(14)

        /* for (sublista in sublistas) {
             println(sublista)
         }*/

        // Log.d("Size",profesors.size.toString())
        //  Log.d("Size2",(profesors.size/17).toString())


        listaID.add(listAutos[0 + 17 * i])

        holder.nombreTextView.text =
            listAutos[14 + 17 * i] + " " + listAutos[1 + 17 * i] + ", Año " + listAutos[6 + 17 * i] + "\n" +
                    "Precio: $" + listAutos[8 + 17 * i]
        holder.datosTextView.text =
            "Tipo: ${listAutos[15 + 17 * i]}\nColor: ${listAutos[16 + 17 * i]}\nN° vin:  + ${listAutos[2 + 17 * i]} \nN° chasis: ${listAutos[3 + 17 * i]} \n" +
                    "N° motor:${listAutos[4 + 17 * i]} \nN° Asientos: ${listAutos[5 + 17 * i]},  Capacidad: ${listAutos[7 + 17 * i]}ton.\n" +
                    "Descripción: ${listAutos[10 + 17 * i]}"

        try {
            Picasso.get().load(listAutos[9 + 17 * i].toUri()).into(holder.ivImagen)
        } catch (e: Exception) {
            holder.ivImagen.setImageResource(R.drawable.auto)
            e.printStackTrace()
        }

        /*Log.d("Id P${0+i}",profesors[0+i])
        Log.d("Modelo P${1+i}",profesors[1+i])
        Log.d("Vin P${2+i}",profesors[2+i])
        Log.d("Chasis P${3+i}",profesors[3+i])
        Log.d("Motor P${4+i}",profesors[4+i])
        Log.d("Asientos P${5+i}",profesors[5+i])
        Log.d("Anio P${6+i}",profesors[6+i])
        Log.d("Capacity P${7+i}",profesors[7+i])
        Log.d("Precio P${8+i}",profesors[8+i])
        Log.d("URI P${9+i}",profesors[9+i])
        Log.d("descr P${10+i}",profesors[10+i])
        Log.d("marca P${11+i}",profesors[11+i])
        Log.d("tipo P${12+i}",profesors[12+i])
        Log.d("color P${13+i}",profesors[13+i]
        Log.d("NMarca P${13+i}",profesors[14+i]
        Log.d("NTipo P${13+i}",profesors[15+i]
        Log.d("NColor P${13+i}",profesors[16+i]*/

        // Agrega el escuchador de clics a la vista del elemento de la lista
        /* holder.itemView.setOnClickListener {
               onItemClick?.onItemClick(profesor[0+i])
           }*/

        holder.btnFav.setOnClickListener {
            Log.d("Jp", "IDS ${listaID.toString()}")
            Log.d("Jp2", "ID Seleccionado? ${listaID[position].toString()}")

            //Toast.makeText(context, "ID Seleccionado${listaID[position].toString()}", Toast.LENGTH_SHORT).show()
            managerFavoritos = Favoritos(context)
            val usuario = DatosActivos.idActivo?.toInt()
            val auto = listaID[position].toInt()

            // Obtener la fecha actual en tiempo real
            val calendar = Calendar.getInstance()
            val currentDate = calendar.time

// Formatear la fecha en el formato deseado
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

            val fechaAutomatica = dateFormat.format(currentDate)
            /**/
            if (db != null) {
                var b: Boolean = true
                b = managerFavoritos!!.validateAdd(usuario!!, auto)
                if (b == false) {
                    managerFavoritos!!.addNewItem(
                        usuario,
                        auto,
                        fechaAutomatica
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
