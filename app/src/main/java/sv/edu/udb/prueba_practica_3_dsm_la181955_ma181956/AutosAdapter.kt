package sv.edu.udb.prueba_practica_3_dsm_la181955_ma181956

import android.util.Log
import android.util.Log.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

import sv.edu.udb.prueba_practica_3_dsm_la181955_ma181956.model.Autos

class AutosAdapter(private val profesors: ArrayList<String>) : RecyclerView.Adapter<AutosAdapter.ViewHolder>() {
var i :Int = 0
    private var onItemClick: OnItemClickListener? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombreTextView: TextView = view.findViewById(R.id.tvModel)
        val datosTextView: TextView = view.findViewById(R.id.tvDatos)
        val ivImagen : ImageView = view.findViewById(R.id.ivImagenCarro)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.alumno_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //val profesor = profesors[position]

        val listaCompleta = profesors

        val sublistas = listaCompleta.chunked(14)

       /* for (sublista in sublistas) {
            println(sublista)
        }*/

        println(sublistas[0])

        Log.d("Size",profesors.size.toString())
        Log.d("Size2",(profesors.size/14).toString())
        Log.d("Size3",(profesors.size/13).toString())

            holder.nombreTextView.text = profesors[0 + 14*i] + " - " + profesors[1 + 14*i] + ", Año " + profesors[6 + 14*i] +"\n" +
                    "Precio: $" + profesors[8 + 14*i]
        holder.datosTextView.text= "N° vin: " + profesors[2 + 14*i]  + "\nN° chasis:" +  profesors[3 + 14*i] + "\n" +
                "N° motor:" +profesors[4 + 14*i] + "\nN° Asientos:" + profesors[5 + 14*i]+ ",  Capacidad:" + profesors[7 + 14*i] + "ton."

        Log.d("URI PNO",profesors[9])
        try {
            Picasso.get().load(profesors[9 + 14*i].toUri()).into(holder.ivImagen)
        } catch (e: Exception) {
            e.printStackTrace()
        }


        /*  for (i in 0..profesors.size-2 step 14) {
              Log.d("Data P${0+i}",profesors[0+i])

          }*/
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
        Log.d("typo P${12+i}",profesors[12+i])
        Log.d("color P${13+i}",profesors[13+i]*/


       // Log.d("P2a",profesor.toString())

//id profesors[0+i]
        //holder.nombreTextView.text = profesors[0+i] + profesors[1+i] + profesors[2+i]
       //holder.apellidoTextView.text = profesor
        //holder.edadTextView.text = profesor.get(0).toString()

        // Agrega el escuchador de clics a la vista del elemento de la lista
     /*   holder.itemView.setOnClickListener {
            onItemClick?.onItemClick(profesor)
        }*/
        i++
    }

    override fun getItemCount(): Int {
        return profesors.size / 14
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClick = listener
    }

    interface OnItemClickListener {
        fun onItemClick(profesor: Autos)
    }
}
