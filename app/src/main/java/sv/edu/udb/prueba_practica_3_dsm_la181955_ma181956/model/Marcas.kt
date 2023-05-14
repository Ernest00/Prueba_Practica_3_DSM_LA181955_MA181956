package sv.edu.udb.prueba_practica_3_dsm_la181955_ma181956.model

import MyDatabaseHelper
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class Marcas (context: Context?) {
    private var helper: MyDatabaseHelper? = null
    private var db: SQLiteDatabase? = null
    init {
        helper = MyDatabaseHelper(context)
        db = helper!!.getWritableDatabase()
    }

    companion object {
        //TABLA
        val TABLE_NAME = "marcas"
        //nombre de los campos
        val COL_ID = "idmarcas"
        val COL_NOMBRE = "nombre"
    }
    // ContentValues
    fun generarContentValues(
        nombre: String?
    ): ContentValues? {
        val valores = ContentValues()
        valores.put(COL_NOMBRE, nombre)
        return valores
    }
    //Agregar un nuevo registro
    fun addNewItem(nombre: String?) {
        db!!.insert(
            TABLE_NAME,
            null,
            generarContentValues(nombre)
        )
    }
    // Eliminar un registro
    fun deleteItem(id: Int) {
        db!!.delete(TABLE_NAME, "$COL_ID=?", arrayOf(id.toString()))
    }
    //Modificar un registro
    fun updateItem(
        id: Int,
        nombre: String?
    ) {
        db!!.update(
            TABLE_NAME, generarContentValues(nombre),
            "$COL_ID=?", arrayOf(id.toString())

        )
    }
    // Mostrar un registro particular
    fun searchItem(id: Int): Cursor? {
        val columns = arrayOf(COL_ID, COL_ID, COL_NOMBRE)
        return db!!.query(
            TABLE_NAME, columns,
            "$COL_ID=?", arrayOf(id.toString()), null, null, null
        )
    }
    // Mostrar todos los registros
    fun searchItemAll(): Cursor? {
        val columns = arrayOf(COL_ID, COL_NOMBRE)
        return db!!.query(
            TABLE_NAME, columns,
            null, null, null, null, "${COL_ID} ASC"
        )
    }

    fun showItemAll(): Cursor? {
        val columns = arrayOf(COL_ID, COL_NOMBRE)
        return db!!.query(
            TABLE_NAME, columns,
            null, null, null, null, "$COL_NOMBRE ASC"
        )
    }

    // Spinner marcas
    fun searchID(nombre: String): Int? {
        val columns = arrayOf(COL_ID, COL_NOMBRE)
        var cursor: Cursor? = db!!.query(
            TABLE_NAME, columns,
            "$COL_NOMBRE=?", arrayOf(nombre.toString()), null, null, null
        )
        cursor!!.moveToFirst()
        return cursor!!.getInt(0)
    }
    fun searchNombre(id: Int): String? {
        val columns = arrayOf(COL_ID, COL_NOMBRE)
        var cursor: Cursor? = db!!.query(
            TABLE_NAME, columns,
            "$COL_ID=?", arrayOf(id.toString()), null, null, null
        )
        cursor!!.moveToFirst()
        return cursor!!.getString(1)
    }
}