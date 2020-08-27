package br.com.calculodejuros

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DecimalFormat
import kotlin.math.pow

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnEnviar.setOnClickListener(this)
        btnLimpar.setOnClickListener(this)

    }

    override fun onClick(view: View?) {

        val id = view?.id

        if (id == R.id.btnEnviar) {

            if (validade()) {

                try {

                    var value = etValor.text.toString().toDouble()
                    var qdn = etQnt.text.toString().toInt()
                    var juros = juros.text.toString().toDouble()
                    calculate(value, qdn, juros)


                } catch (err: NumberFormatException) {

                    Toast.makeText(this, err.toString(), Toast.LENGTH_LONG).show()
                }
            } else {

                Toast.makeText(applicationContext, R.string.message_erro, Toast.LENGTH_LONG)
                        .show()
            }

        } else if (id == R.id.btnLimpar) {

            clearFields()
        }
    }

    private fun clearFields() {

        etValor.setText("")
        etQnt.setText("")
        juros.setText("")
        showResult.setText("")

    }

    private fun calculate(value: Double, qdn: Int, juros: Double) {

        if (rdbOptSimples.isChecked) {

            showCal((value + (value * (qdn * juros) / 100)).format(), "Simples")

        } else {

            val expo = (1 + (juros / 100)).pow(qdn)
            showCal((value * expo).format(), "Composto")
        }

    }

    private fun Double.format(): String {
        val df = DecimalFormat("#.00")
        return df.format(this)
    }

    private fun showCal(result: String, juros: String) {

        showResult.setText(juros + " R$ " + result)

    }

    private fun validade(): Boolean {

        return !etValor.text.isEmpty() && !etQnt.text.isEmpty() && !juros.text.isEmpty()

    }

}