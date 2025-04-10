package com.example.calculadora_kotlin

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private lateinit var tvOperacion: TextView
    private lateinit var tvResultado: TextView
    private var primerNumero: Double = Double.NaN
    private var segundoNumero: Double = Double.NaN
    private var operacionActual: String = ""
    private val formatoDecimal = DecimalFormat("#.##########")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvOperacion = findViewById(R.id.tvOperacion)
        tvResultado = findViewById(R.id.tvResultado)
    }

    fun seleccionarNumero(view: View) {
        val boton = view as Button
        val numero = boton.text.toString()

        // Evita múltiples puntos
        if (numero == "." && tvResultado.text.contains(".")) return

        tvResultado.append(numero)
    }

    fun cambiarOperador(view: View) {
        if (tvResultado.text.isNotEmpty()) {
            try {
                primerNumero = tvResultado.text.toString().toDouble()
                val boton = view as Button
                operacionActual = when (boton.text.toString().trim()) {
                    "÷" -> "/"
                    "X" -> "*"
                    else -> boton.text.toString().trim()
                }

                // Muestra la operación en la línea superior
                tvOperacion.text = "${formatoDecimal.format(primerNumero)} $operacionActual"
                tvResultado.text = ""

            } catch (e: NumberFormatException) {
                tvResultado.text = "Error"
            }
        }
    }

    fun igual(view: View) {
        if (tvResultado.text.isNotEmpty() && !primerNumero.isNaN()) {
            try {
                segundoNumero = tvResultado.text.toString().toDouble()

                val resultado = when (operacionActual) {
                    "+" -> primerNumero + segundoNumero
                    "-" -> primerNumero - segundoNumero
                    "*" -> primerNumero * segundoNumero
                    "/" -> if (segundoNumero != 0.0) primerNumero / segundoNumero else Double.NaN
                    "%" -> primerNumero % segundoNumero
                    else -> Double.NaN
                }

                if (!resultado.isNaN()) {
                    // Muestra la operación completa
                    tvOperacion.text = "${formatoDecimal.format(primerNumero)} $operacionActual ${formatoDecimal.format(segundoNumero)} ="
                    tvResultado.text = formatoDecimal.format(resultado)
                    primerNumero = resultado // Permite continuar operaciones
                } else {
                    tvResultado.text = "Error"
                }

            } catch (e: Exception) {
                tvResultado.text = "Error"
            }
        }
    }

    fun borrar(view: View) {
        val boton = view as Button
        when (boton.text.toString().trim()) {
            "C" -> {
                if (tvResultado.text.isNotEmpty()) {
                    tvResultado.text = tvResultado.text.dropLast(1)
                }
            }
            "CA" -> {
                tvOperacion.text = ""
                tvResultado.text = ""
                primerNumero = Double.NaN
                segundoNumero = Double.NaN
                operacionActual = ""
            }
        }
    }
}