package sv.edu.udb.prueba_practica_3_dsm_la181955_ma181956

import MyDatabaseHelper
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import sv.edu.udb.prueba_practica_3_dsm_la181955_ma181956.model.Automovil
import sv.edu.udb.prueba_practica_3_dsm_la181955_ma181956.model.Colores
import sv.edu.udb.prueba_practica_3_dsm_la181955_ma181956.model.Marcas
import sv.edu.udb.prueba_practica_3_dsm_la181955_ma181956.model.TipoAuto


class AutomovilActivity : AppCompatActivity(),  View.OnClickListener {
    private var managerAuto: Automovil?= null
    private var managerMarcas: Marcas? = null
    private var managerColores: Colores? = null
    private var managerTipo: TipoAuto? = null

    private var dbHelper: MyDatabaseHelper? = null
    private var db: SQLiteDatabase? = null
    private var cursor: Cursor? = null

    private var txtIdDB: TextView? = null
    private var txtId: EditText? = null
    private var txtModelo: EditText? = null
    private var txtVin: EditText? = null
    private var txtChasis: EditText? = null
    private var txtMotor: EditText? = null
    private var txtAnio: EditText? = null
    private var txtAsientos: EditText? = null
    private var txtCapacidad: EditText? = null
    private var txtPrecio: EditText? = null
    private var txtDescripcion: EditText? = null
    private var txtImagen: EditText? = null

    private var cmbMarcas: Spinner? = null
    private var cmbColores: Spinner? = null
    private var cmbTipo: Spinner? = null

    private var btnAgregar: Button? = null
    private var btnActualizar: Button? = null
    private var btnEliminar: Button? = null
    private var btnBuscar: Button? = null
    private var btnVolver : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_automovil)//activity

        txtIdDB = findViewById(R.id.txtIdDB)
        txtId = findViewById(R.id.txtId)
        txtModelo = findViewById(R.id.txtModelo)
        txtVin = findViewById(R.id.txtVIN)
        txtChasis = findViewById(R.id.txtChasis)
        txtMotor = findViewById(R.id.txtMotor)
        txtAnio = findViewById(R.id.txtAnio)
        txtAsientos = findViewById(R.id.txtAsientos)
        txtCapacidad = findViewById(R.id.txtCapacidad)
        txtPrecio = findViewById(R.id.txtPrecio)
        txtDescripcion = findViewById(R.id.txtDescripcion)
        txtImagen = findViewById(R.id.txtImagen)

        cmbMarcas = findViewById<Spinner>(R.id.cmbMarcas)
        cmbColores = findViewById<Spinner>(R.id.cmbColores)
        cmbTipo = findViewById<Spinner>(R.id.cmbTipo)

        btnAgregar = findViewById(R.id.btnAgregar)
        btnActualizar = findViewById(R.id.btnActualizar)
        btnEliminar = findViewById(R.id.btnEliminar)
        btnBuscar = findViewById(R.id.btnBuscar)
        btnVolver = findViewById(R.id.btnVolver)

        dbHelper = MyDatabaseHelper(this)
        db = dbHelper!!.writableDatabase

        setSpinnerMarcas()
        setSpinnerColores()
        setSpinnerTipo()

        btnAgregar!!.setOnClickListener(this)
        btnActualizar!!.setOnClickListener(this)
        btnEliminar!!.setOnClickListener(this)
        btnBuscar!!.setOnClickListener(this)
        btnVolver!!.setOnClickListener(this)
    }

    private fun setSpinnerMarcas() {
        managerMarcas = Marcas(this)
        managerMarcas!!.searchItemAll()
        cursor = managerMarcas!!.showItemAll()
        var marca = ArrayList<String>()
        if (cursor != null && cursor!!.count > 0) {
            cursor!!.moveToFirst()
            do {
                marca.add(cursor!!.getString(1))
            } while (cursor!!.moveToNext())
        }
        var adaptador = ArrayAdapter(this, android.R.layout.simple_spinner_item, marca)
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        cmbMarcas!!.adapter = adaptador
    }

    private fun setSpinnerColores() {
        managerColores = Colores(this)
        managerColores!!.searchItemAll()
        cursor = managerColores!!.showItemAll()
        var colr = ArrayList<String>()
        if (cursor != null && cursor!!.count > 0) {
            cursor!!.moveToFirst()
            do {
                colr.add(cursor!!.getString(1))
            } while (cursor!!.moveToNext())
        }
        var adaptador2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, colr)
        adaptador2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        cmbColores!!.adapter = adaptador2
    }

    private fun setSpinnerTipo() {
        managerTipo = TipoAuto(this)
        managerTipo!!.searchItemAll()
        cursor = managerTipo!!.showItemAll()
        var tipoA = ArrayList<String>()
        if (cursor != null && cursor!!.count > 0) {
            cursor!!.moveToFirst()
            do {
                tipoA.add(cursor!!.getString(1))
            } while (cursor!!.moveToNext())
        }
        var adaptador3 = ArrayAdapter(this, android.R.layout.simple_spinner_item, tipoA)
        adaptador3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        cmbTipo!!.adapter = adaptador3
    }


    override fun onClick(view: View) {
        managerAuto = Automovil(this)

        val modelo: String = txtModelo!!.text.toString().trim()
        val vin: String = txtVin!!.text.toString().trim()
        val chasis: String = txtChasis!!.text.toString().trim()
        val motor: String = txtMotor!!.text.toString().trim()
        val anio: String = txtAnio!!.text.toString().trim()
        val asientos: String = txtAsientos!!.text.toString().trim()
        val capacidad: String = txtCapacidad!!.text.toString().trim()
        val precio: String = txtPrecio!!.text.toString().trim()
        val descripcion: String = txtDescripcion!!.text.toString().trim()
        val imagen: String = txtImagen!!.text.toString().trim()

        val marca: String = cmbMarcas!!.selectedItem.toString().trim()
        val idmarca = managerMarcas!!.searchID(marca)
        val tipo: String = cmbTipo!!.selectedItem.toString().trim()
        val idtipo = managerTipo!!.searchID(tipo)
        val color: String = cmbColores!!.selectedItem.toString().trim()
        val idcolor = managerColores!!.searchID(color)


        val idSel = txtId!!.text.toString().trim()

        if (db != null) {
            if (view === btnAgregar) {
                if (vericarFormulario("insertar")) {
                    managerAuto!!.addNewItem(
                        modelo,
                        vin,
                        chasis,
                        motor,
                        asientos.toInt(),
                        anio.toInt(),
                        capacidad.toInt(),
                        precio.toFloat(),
                        imagen,
                        descripcion,
                        idmarca,
                        idtipo,
                        idcolor

                    )

                    mostrarMensajesArriba(view, "Automovil agregado")

                }
            } else if (view === btnActualizar) {
                if (vericarFormulario("actualizar")) {
                    managerAuto!!.updateItem(
                        idSel.toInt(),
                        modelo,
                        vin,
                        chasis,
                        motor,
                        asientos.toInt(),
                        anio.toInt(),
                        capacidad.toInt(),
                        precio.toFloat(),
                        imagen,
                        descripcion,
                        idmarca,
                        idtipo,
                        idcolor
                    )
                    txtId!!.setText("")
                    mostrarMensajesArriba(view, "Automovil actualizado")

                }
            } else if (view === btnEliminar) {
                if (vericarFormulario("eliminar")) {
// manager.eliminar(1);
                    managerAuto!!.deleteItem(idSel.toInt())
                    txtId!!.setText("")
                    mostrarMensajesArriba(view, "Automovil eliminado")

                }
            } else if (view === btnBuscar) {

                if(vericarFormulario("buscar")){
                    cursor= managerAuto!!.searchItem(idSel.toInt())

                    if(cursor !=null && cursor!!.count>0){
                        cursor!!.moveToFirst()
                        var label_marca: String ? = managerMarcas!!.searchNombre(cursor!!.getInt(12))
                        var label_tipo: String ? = managerTipo!!.searchNombre(cursor!!.getInt(13))
                        var label_color: String ? = managerColores!!.searchNombre(cursor!!.getInt(14))

                        txtIdDB!!.text = (cursor!!.getInt(0).toString())
                        txtModelo!!.setText(cursor!!.getString(2))
                        txtVin!!.setText(cursor!!.getString(3))
                        txtChasis!!.setText(cursor!!.getString(4))
                        txtMotor!!.setText(cursor!!.getString(5))
                        txtAsientos!!.setText(cursor!!.getString(6))
                        txtAnio!!.setText(cursor!!.getString(7))
                        txtCapacidad!!.setText(cursor!!.getString(8))
                        txtPrecio!!.setText(cursor!!.getString(9))
                        txtImagen!!.setText(cursor!!.getString(10))
                        txtDescripcion!!.setText(cursor!!.getString(11))
                        cmbMarcas!!.setSelectionByValue(label_marca.toString())
                        cmbTipo!!.setSelectionByValue(label_tipo.toString())
                        cmbColores!!.setSelectionByValue(label_color.toString())

                        Log.d("INFORMACION", cursor!!.getInt(1).toString())
                        Log.d("INFORMACION", cursor!!.getInt(12).toString() + " - " + cursor!!.getInt(13).toString()  + " - " + cursor!!.getInt(14).toString())
                        Log.d("INFORMACION", label_marca + " - " + label_tipo  + " - " + label_color)

                        mostrarMensajesArriba(view, "Cargando Información")

                    }else{

                        mostrarMensajesArriba(view, "No se encontraron registros")
                    }

                }


            }else if (view === btnVolver) {
                val intent = Intent(this, AdminActivity::class.java)
                startActivity(intent)
                finish()

            } else {

                mostrarMensajesArriba(view, "No se puede conectar a la Base de Datos")
            }

            if (view === btnVolver) {
                val intent = Intent(this, AdminActivity::class.java)
                startActivity(intent)
                finish()

            }
        }


    }

    fun Spinner.setSelectionByValue(value: Any) {
        val adapter = this.adapter
        for (i in 0 until adapter.count) {
            val item = adapter.getItem(i)
            if (item == value || item.toString() == value.toString()) {
                this.setSelection(i)
                break
            }
        }
    }


    private fun vericarFormulario(opc: String): Boolean {
        var notificacion: String = "Se han generado algunos errores, favor verifiquelos"
        var response = true

        var modelo_v = true
        var vin_v = true
        var chasis_v = true
        var motor_v = true
        var anio_v = true
        var asientos_v = true
        var capacidad_v = true
        var precio_v = true
        var descripcion_v = true
        var imagen_v = true
        var idmarca_v = true
        var idtipo_v = true
        var idcolor_v = true


        var idseleccionado_v = true

        val modelo: String = txtModelo!!.text.toString().trim()
        val vin: String = txtVin!!.text.toString().trim()
        val chasis: String = txtChasis!!.text.toString().trim()
        val motor: String = txtMotor!!.text.toString().trim()
        val anio: String = txtAnio!!.text.toString().trim()
        val asientos: String = txtAsientos!!.text.toString().trim()
        val capacidad: String = txtCapacidad!!.text.toString().trim()
        val precio: String = txtPrecio!!.text.toString().trim()
        val descripcion: String = txtDescripcion!!.text.toString().trim()
        val imagen: String = txtImagen!!.text.toString().trim()

        val marca: String = cmbMarcas!!.selectedItem.toString().trim()
        val tipo: String = cmbTipo!!.selectedItem.toString().trim()
        val color: String = cmbColores!!.selectedItem.toString().trim()

        val idproducto: String = txtId!!.text.toString().trim()
        if (opc === "insertar" || opc == "actualizar") {
            if (modelo.isEmpty()) {
                txtModelo!!.error = "Ingrese la descripción del tipo de auto"
                txtModelo!!.requestFocus()
                modelo_v = false
            }
            if (vin.isEmpty()) {
                txtVin!!.error = "Ingrese el N° de VIN"
                txtVin!!.requestFocus()
                vin_v = false
            }

            if (chasis.isEmpty()) {
                txtChasis!!.error = "Ingrese el N° de chasis"
                txtChasis!!.requestFocus()
                chasis_v = false
            }

            if (motor.isEmpty()) {
                txtMotor!!.error = "Ingrese el N° de motor"
                txtMotor!!.requestFocus()
                motor_v = false
            }

            if (anio.isEmpty()) {
                txtAnio!!.error = "Ingrese el Año "
                txtAnio!!.requestFocus()
                anio_v = false
            }

            if (asientos.isEmpty()) {
                txtAsientos!!.error = "Ingrese el N° de asientos"
                txtAsientos!!.requestFocus()
                asientos_v = false
            }

            if (capacidad.isEmpty()) {
                txtCapacidad!!.error = "Ingrese la capacidad"
                txtCapacidad!!.requestFocus()
                capacidad_v = false
            }

            if (precio.isEmpty()) {
                txtPrecio!!.error = "Ingrese el precio $$"
                txtPrecio!!.requestFocus()
                precio_v = false
            }

            if (descripcion.isEmpty()) {
                txtDescripcion!!.error = "Ingrese la descripción del tipo de auto"
                txtDescripcion!!.requestFocus()
                descripcion_v = false
            }

            if (imagen.isEmpty()) {
                txtImagen!!.error = "Ingrese la url de imagen"
                txtImagen!!.requestFocus()
                imagen_v = false
            }

            if (opc == "actualizar") {
                if (idproducto.isEmpty()) {
                    idseleccionado_v = false
                    notificacion = "No se ha seleccionado un automovil"
                }
                response = !(modelo_v == false || vin_v == false || chasis_v == false || motor_v == false || anio_v == false || asientos_v == false || capacidad_v == false || precio_v == false || descripcion_v == false || imagen_v== false || idseleccionado_v == false)
            } else {
                response = !(modelo_v == false || vin_v == false || chasis_v == false || motor_v == false || anio_v == false || asientos_v == false || capacidad_v == false || precio_v == false || descripcion_v == false || imagen_v== false)
            }
        } else if (opc === "eliminar" || opc == "buscar") {
            if (idproducto.isEmpty()) {
                response = false
                notificacion = "No se ha seleccionado un automovil"
            }
        }
//Mostrar errores
        if (response == false) {
            Toast.makeText(
                this,
                notificacion,
                Toast.LENGTH_LONG
            ).show()
        }
        return response
    }

    fun mostrarMensajesArriba(view:View, mensaje: String){
        val snackbar = Snackbar.make(view, mensaje, Snackbar.LENGTH_LONG)
        val layoutParams = snackbar.view.layoutParams as FrameLayout.LayoutParams
        layoutParams.gravity = Gravity.TOP
        snackbar.view.layoutParams = layoutParams
        snackbar.show()
    }


}