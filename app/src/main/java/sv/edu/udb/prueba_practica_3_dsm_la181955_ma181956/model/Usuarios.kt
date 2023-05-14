package sv.edu.udb.prueba_practica_3_dsm_la181955_ma181956.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import MyDatabaseHelper

class Usuarios(context: Context?) {
    private var helper: MyDatabaseHelper? = null
    private var db: SQLiteDatabase? = null
    init {
        helper = MyDatabaseHelper(context)
        db = helper!!.getWritableDatabase()
    }

    companion object {
        //TABLA USUARIOS
        val TABLE_NAME_USUARIOS = "usuario"
        //nombre de los campos de la tabla contactos
        val COL_ID = "idusuario"
        val COL_NOMBRES = "nombres"
        val COL_APELLIDOS = "apellidos"
        val COL_EMAIL = "email"
        val COL_USER = "user"
        val COL_PASS = "password"
        val COL_TIPO = "tipo"
    }
    // ContentValues
    fun generarContentValues(
        nombres: String?,
        apellidos: String?,
        email: String?,
        user: String?,
        pass: String?,
        tipo: String?
    ): ContentValues? {
        val valores = ContentValues()
        valores.put(Usuarios.COL_NOMBRES, nombres)
        valores.put(Usuarios.COL_APELLIDOS, apellidos)
        valores.put(Usuarios.COL_EMAIL, email)
        valores.put(Usuarios.COL_USER, user)
        valores.put(Usuarios.COL_PASS, pass)
        valores.put(Usuarios.COL_TIPO, tipo)
        return valores
    }

    //Agregar un nuevo registro
    fun addNewUsuario(nombre: String?,apellidos: String?,email: String?,user: String?, pass: String?, tipo: String?) {
        db!!.insert(
            TABLE_NAME_USUARIOS,
            null,
            generarContentValues(nombre, apellidos, email, user, pass, tipo)
        )
    }
    // Eliminar un registro
    fun deleteUsuario(id: Int) {
        db!!.delete(TABLE_NAME_USUARIOS, "$COL_ID=?", arrayOf(id.toString()))
    }
    //Modificar un registro
    fun updateUsuario(
        id: Int,
        nombres: String?,
        apellidos: String?,
        email: String?,
        user: String?,
        pass: String?,
        tipo: String?
    ) {
        db!!.update(
            TABLE_NAME_USUARIOS, generarContentValues(nombres, apellidos, email, user, pass, tipo),
            "$COL_ID=?", arrayOf(id.toString())

        )
    }
    // Mostrar un registro particular
    fun searchUser(id: Int): Cursor? {
        val columns = arrayOf(COL_ID, COL_NOMBRES, COL_APELLIDOS, COL_EMAIL, COL_USER, COL_PASS, COL_TIPO)

        return db!!.query(
            TABLE_NAME_USUARIOS, columns,
            "$COL_ID=?", arrayOf(id.toString()), null, null, null
        )
    }

    // Mostrar un registro particular
    fun searchUsuario(nick: String): Cursor? {
        val columns = arrayOf(COL_ID, COL_NOMBRES, COL_APELLIDOS, COL_EMAIL, COL_USER, COL_PASS, COL_TIPO)

        return db!!.query(
            TABLE_NAME_USUARIOS, columns,
            "$COL_USER=?", arrayOf(nick), null, null, null
        )
    }
    // Mostrar todos los registros
    fun searchUsuariosAll(): Cursor? {
        val columns = arrayOf(COL_ID, COL_NOMBRES, COL_APELLIDOS, COL_EMAIL, COL_USER, COL_PASS, COL_TIPO)
        return db!!.query(
            TABLE_NAME_USUARIOS, columns,
            null, null, null, null, "${Usuarios.COL_USER} ASC"
        )
    }
}