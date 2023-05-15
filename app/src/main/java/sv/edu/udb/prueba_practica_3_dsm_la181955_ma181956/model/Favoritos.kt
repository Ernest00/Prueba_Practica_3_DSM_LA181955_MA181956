package sv.edu.udb.prueba_practica_3_dsm_la181955_ma181956.model

import MyDatabaseHelper
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import java.time.Year

class Favoritos(context: Context?) {
    private var helper: MyDatabaseHelper? = null
    private var db: SQLiteDatabase? = null

    init {
        helper = MyDatabaseHelper(context)
        db = helper!!.getWritableDatabase()
    }

    companion object {
        //TABLA
        val TABLE_NAME = "favoritos_automovil"
        //nombre de los campos
        val COL_ID = "idfavoritosautomovil"
        val COL_IDUSUARIO = "idusuario"
        val COL_IDAUTOMOVIL = "idfavoritoautomovil"
        val COL_FECHA = "fecha_agregado"
    }

    // ContentValues
    fun generarContentValues(
        idusuario: Int?,
        idauto: Int?,
        fecha: String?
    ): ContentValues? {
        val valores = ContentValues()
        valores.put(COL_IDUSUARIO, idusuario)
        valores.put(COL_IDAUTOMOVIL, idauto)
        valores.put(COL_FECHA, fecha)
        return valores
    }

    //Agregar un nuevo registro
    fun addNewItem( idusuario: Int?,
                    idauto: Int?,
                    fecha: String?
    ) {
        db!!.insert(
            TABLE_NAME,
            null,
            generarContentValues(
                idusuario, idauto, fecha
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
        idusuario: Int?,
        idauto: Int?,
        fecha: String?
    ) {
        db!!.update(
            TABLE_NAME,
            generarContentValues(
                idusuario, idauto, fecha
            ),
            "$COL_ID=?",
            arrayOf(id.toString())

        )
    }

    // Mostrar un registro particular
    fun searchItem(id: Int): Cursor? {
        val columns = arrayOf(COL_ID, COL_ID,
            COL_IDUSUARIO,
           COL_IDAUTOMOVIL,
        COL_FECHA)
        return db!!.query(
            TABLE_NAME, columns,
            "$COL_ID=?", arrayOf(id.toString()), null, null, null
        )
    }
//En base al usuario en sesion
    fun getRecordsByUser(idUsuario: Int): Cursor? {
        val columns = arrayOf(COL_ID, COL_IDUSUARIO, COL_IDAUTOMOVIL, COL_FECHA)
        val selection = "$COL_IDUSUARIO = ?"
        val selectionArgs = arrayOf(idUsuario.toString())

        return db!!.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null)
    }


    // Buscar si existe ya el favorito
    fun validateAdd(idUsuario: Int, idAutomovil: Int): Boolean {
        val columns = arrayOf(COL_ID, COL_IDUSUARIO, COL_IDAUTOMOVIL)
        val selection = "$COL_IDUSUARIO = ? AND $COL_IDAUTOMOVIL = ?"
        val selectionArgs = arrayOf(idUsuario.toString(), idAutomovil.toString())

        val cursor = db!!.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null)
        val recordExists = cursor != null && cursor.count > 0

        cursor?.close()

        return recordExists
    }

    fun GetIdToEliminate(idUsuario: Int, idAutomovil: Int): Int {
        val columns = arrayOf(COL_ID, COL_IDUSUARIO, COL_IDAUTOMOVIL)
        val selection = "$COL_IDUSUARIO = ? AND $COL_IDAUTOMOVIL = ?"
        val selectionArgs = arrayOf(idUsuario.toString(), idAutomovil.toString())

        val cursor = db!!.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null)

        var id = -1

        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getInt(0)
        }
        cursor?.close()

        return id
    }

    //Get fecha
    fun getFecha(idUsuario: Int, idAutomovil: Int): String {
        val columns = arrayOf(COL_ID, COL_IDUSUARIO, COL_IDAUTOMOVIL, COL_FECHA)
        val selection = "$COL_IDUSUARIO = ? AND $COL_IDAUTOMOVIL = ?"
        val selectionArgs = arrayOf(idUsuario.toString(), idAutomovil.toString())

        val cursor = db!!.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null)

        var fecha = ""

        if (cursor != null && cursor.moveToFirst()) {
            fecha = cursor.getString(3)
        }

        cursor?.close()

        return fecha
    }

    // Mostrar todos los registros
    fun searchItemAll(): Cursor? {
        val columns = arrayOf(COL_ID,
            COL_IDUSUARIO,
            COL_IDAUTOMOVIL,
            COL_FECHA)
        return db!!.query(
            TABLE_NAME, columns,
            null, null, null, null, "${COL_ID} ASC"
        )
    }

    fun showItemAll(): Cursor? {
        val columns = arrayOf(COL_ID, COL_IDUSUARIO,
            COL_IDAUTOMOVIL,
            COL_FECHA)
        return db!!.query(
            TABLE_NAME, columns,
            null, null, null, null, "${COL_FECHA} ASC"
        )
    }

}