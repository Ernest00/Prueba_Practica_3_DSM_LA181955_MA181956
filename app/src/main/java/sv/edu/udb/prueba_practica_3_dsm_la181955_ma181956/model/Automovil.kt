package sv.edu.udb.prueba_practica_3_dsm_la181955_ma181956.model

import MyDatabaseHelper
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import java.time.Year

class Automovil(context: Context?) {
    private var helper: MyDatabaseHelper? = null
    private var db: SQLiteDatabase? = null

    init {
        helper = MyDatabaseHelper(context)
        db = helper!!.getWritableDatabase()
    }

    companion object {
        //TABLA
        val TABLE_NAME = "automovil"

        //nombre de los campos
        val COL_ID = "idautomovil"
        val COL_MODELO = "modelo"
        val COL_VIN = "numero_vin"
        val COL_CHASIS = "numero_chasis"
        val COL_MOTOR = "numero_motor"
        val COL_ASIENTOS = "numero_asientos"
        val COL_ANIO = "anio"
        val COL_CAPACIDAD = "capacidad_asientos"
        val COL_PRECIO = "precio"
        val COL_IMAGEN = "URI_IMG"
        val COL_DESCRIPCION = "descripcion"
        val COL_IDMARCA = "idmarcas"
        val COL_IDTIPO = "idtipoautomovil"
        val COL_IDCOLOR = "idcolores"
    }

    // ContentValues
    fun generarContentValues(
        modelo: String?,
        vin: String?,
        chasis: String?,
        motor: String?,
        asientos: Int?,
        anio: Int?,
        capacidad: Int?,
        precio: Float?,
        imagen: String?,
        descripcion: String?,
        idmarca: Int?,
        idtipo: Int?,
        idcolor: Int?
    ): ContentValues? {
        val valores = ContentValues()
        valores.put(COL_MODELO, modelo)
        valores.put(COL_VIN, vin)
        valores.put(COL_CHASIS, chasis)
        valores.put(COL_MOTOR, motor)
        valores.put(COL_ASIENTOS, asientos)
        valores.put(COL_ANIO, anio)
        valores.put(COL_CAPACIDAD, capacidad)
        valores.put(COL_PRECIO, precio)
        valores.put(COL_IMAGEN, imagen)
        valores.put(COL_DESCRIPCION, descripcion)
        valores.put(COL_IDMARCA, idmarca)
        valores.put(COL_IDTIPO, idtipo)
        valores.put(COL_IDCOLOR, idcolor)
        return valores
    }

    //Agregar un nuevo registro
    fun addNewItem( modelo: String?,
        vin: String?, chasis: String?, motor: String?, asientos: Int?, anio: Int?, capacidad: Int?, precio: Float?,
        imagen: String?, descripcion: String?, idmarca: Int?, idtipo: Int?, idcolor: Int?
    ) {
        db!!.insert(
            TABLE_NAME,
            null,
            generarContentValues(
                modelo, vin, chasis, motor, asientos, anio, capacidad,
                precio, imagen, descripcion, idmarca, idtipo, idcolor
            )
        )
    }

    // Eliminar un registro
    fun deleteItem(id: Int) {
        db!!.delete(TABLE_NAME, "$COL_ID=?", arrayOf(id.toString()))
    }

    //Modificar un registro
    fun updateItem(
        id: Int,
        modelo: String?,
        vin: String?,
        chasis: String?,
        motor: String?,
        asientos: Int?,
        anio: Int?,
        capacidad: Int?,
        precio: Float?,
        imagen: String?,
        descripcion: String?,
        idmarca: Int?,
        idtipo: Int?,
        idcolor: Int?
    ) {
        db!!.update(
            TABLE_NAME,
            generarContentValues(
                modelo,
                vin,
                chasis,
                motor,
                asientos,
                anio,
                capacidad,
                precio,
                imagen,
                descripcion,
                idmarca,
                idtipo,
                idcolor
            ),
            "$COL_ID=?",
            arrayOf(id.toString())

        )
    }

    // Mostrar un registro particular
    fun searchItem(id: Int): Cursor? {
        val columns = arrayOf(COL_ID, COL_ID, COL_MODELO,
            COL_VIN,
            COL_CHASIS,
            COL_MOTOR,
            COL_ASIENTOS,
            COL_ANIO,
            COL_CAPACIDAD,
            COL_PRECIO,
            COL_IMAGEN,
            COL_DESCRIPCION,
            COL_IDMARCA,
            COL_IDTIPO,
            COL_IDCOLOR)
        return db!!.query(
            TABLE_NAME, columns,
            "$COL_ID=?", arrayOf(id.toString()), null, null, null
        )
    }

    // Mostrar todos los registros
    fun searchItemAll(): Cursor? {
        val columns = arrayOf(COL_ID,
            COL_MODELO,
            COL_VIN,
            COL_CHASIS,
            COL_MOTOR,
            COL_ASIENTOS,
            COL_ANIO,
            COL_CAPACIDAD,
            COL_PRECIO,
            COL_IMAGEN,
            COL_DESCRIPCION,
            COL_IDMARCA,
            COL_IDTIPO,
            COL_IDCOLOR)
        return db!!.query(
            TABLE_NAME, columns,
            null, null, null, null, "${COL_ID} ASC"
        )
    }

    fun showItemAll(): Cursor? {
        val columns = arrayOf(COL_ID, COL_MODELO,
            COL_VIN,
            COL_CHASIS,
            COL_MOTOR,
            COL_ASIENTOS,
            COL_ANIO,
            COL_CAPACIDAD,
            COL_PRECIO,
            COL_IMAGEN,
            COL_DESCRIPCION,
            COL_IDMARCA,
            COL_IDTIPO,
            COL_IDCOLOR)
        return db!!.query(
            TABLE_NAME, columns,
            null, null, null, null, "${COL_MODELO} ASC"
        )
    }

}