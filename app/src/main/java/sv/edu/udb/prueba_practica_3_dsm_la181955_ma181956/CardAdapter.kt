package sv.edu.udb.prueba_practica_3_dsm_la181955_ma181956

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import sv.edu.udb.prueba_practica_3_dsm_la181955_ma181956.model.Automovil

class CardAdapter(private val dataList: List<Automovil>) :
    RecyclerView.Adapter<CardAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_automovils, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val marcaData = dataList[position]

        holder.imageView.setImageResource(R.drawable.image) // Reemplaza con la imagen deseada

        val ivImagen = holder.findViewById<ImageView>(R.id.imageView)
        holder.titleTextView.text = marcaData.searchItemAll().toString()
        holder.descriptionTextView.text = marcaData.description

        Picasso.get().load(productos[position].imagen).into(ivImagen)

        holder.favoriteButton.setOnClickListener {
            // Lógica para manejar el evento del botón de favorito aquí
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: CardView = itemView.findViewById(R.id.cardView)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val favoriteButton: Button = itemView.findViewById(R.id.favoriteButton)
    }
}
