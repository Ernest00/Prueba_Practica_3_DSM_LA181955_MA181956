package sv.edu.udb.prueba_practica_3_dsm_la181955_ma181956.model

data class Autos(
    val idauto: Int,
    var modelo: String,
    var numero_vin: String,
    var numero_chasis: String,
    var numero_motor: String,
    var numero_asientos: Int,
    var anio: Int,
    var capacidad: Int,
    var precio: String,
    var uri_img: String,
    var descripcion: String,
    var idmarca: Int,
    var idtipoautomovil: Int,
    var idcolor: Int
)
