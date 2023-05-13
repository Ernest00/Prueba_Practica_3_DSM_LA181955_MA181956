import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

class MyDatabaseHelper(private val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "cars_motors.sqlite"
        private const val DATABASE_VERSION = 1
    }

    private val databasePath: String
        get() = context.getDatabasePath(DATABASE_NAME).path

    init {
        if (!databaseExists()) {
            try {
                copyDatabaseFromAssets()
            } catch (e: IOException) {
                e.printStackTrace()
                throw RuntimeException("Error al copiar la base de datos desde assets")
            }
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // No es necesario implementar este método, ya que la base de datos ya está creada.
        // Puedes dejarlo vacío o agregar algún código adicional si es necesario.
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Implementa aquí la lógica para actualizar la base de datos si es necesario.
        // Puedes dejarlo vacío si no necesitas realizar actualizaciones en la base de datos.
    }

    private fun databaseExists(): Boolean {
        val dbFile = File(databasePath)
        return dbFile.exists()
    }

    @Throws(IOException::class)
    private fun copyDatabaseFromAssets() {
        val inputStream: InputStream = context.assets.open(DATABASE_NAME)
        val outputStream = FileOutputStream(databasePath)

        val buffer = ByteArray(1024)
        var length: Int
        while (inputStream.read(buffer).also { length = it } > 0) {
            outputStream.write(buffer, 0, length)
        }

        outputStream.flush()
        outputStream.close()
        inputStream.close()
    }
}
