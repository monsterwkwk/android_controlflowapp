package id.co.iconpln.controlflowapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_operation.*

class Operation : AppCompatActivity(), View.OnClickListener {

    private var inputX: Int = 0
    private var inputY: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operation)

        setButtonClickListener()
        getInputNumbers()
    }

    private fun getInputNumbers() {
        if(etBilanganX.text?.isNotEmpty() == true || etBilanganY.text?.isNotEmpty() == true) {
            inputX = etBilanganX.text.toString().toInt()
            inputY = etBilanganY.text.toString().toInt()
        }
    }

    private fun setButtonClickListener() {
        btnAdd.setOnClickListener(this)
        btnDiv.setOnClickListener(this)
        btnMult.setOnClickListener(this)
        btnSub.setOnClickListener(this)
        btnOperation.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.btnAdd -> {
                getInputNumbers()
                val add = OperationClass.Add(inputY)
                val addResult = execute(inputX, add)
                tbOpResult.text = addResult.toString()

            }
            R.id.btnDiv -> {
                getInputNumbers()
                val div = OperationClass.Divide(inputY)
                val divResult = execute(inputX, div)
                tbOpResult.text = divResult.toString()
            }
            R.id.btnMult -> {
                getInputNumbers()
                val mult = OperationClass.Multiply(inputY)
                val multResult = execute(inputX, mult)
                tbOpResult.text = multResult.toString()
            }
            R.id.btnSub -> {
                getInputNumbers()
                val sub = OperationClass.Substract(inputY)
                val subResult = execute(inputX, sub)
                tbOpResult.text = subResult.toString()
            }
            R.id.btnOperation -> {

            }
        }
    }

    private fun execute(x: Int, operationClass: OperationClass): Int {
        return when (operationClass) {
            is OperationClass.Add -> operationClass.value + x
            is OperationClass.Divide -> operationClass.value / x
            is OperationClass.Multiply -> operationClass.value * x
            is OperationClass.Substract -> operationClass.value - x
        }
    }
}
