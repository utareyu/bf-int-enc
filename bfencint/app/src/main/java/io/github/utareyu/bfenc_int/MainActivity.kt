package io.github.utareyu.bfenc_int

import android.R.attr.label
import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

var InputText = ""

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun TxtToBf(view: View) {
        //val TxtInput = InputTextbox.text.toString()
        ResultTextbox.text = "++++++++++++++[>++>+++++++>++++++++>+++++<<<<-]>++++++.>>++++.-----.<<--.>.++++.<++.>>>+++.<++++.>+++++.<----.+++++.>-----.<-------.+++.----.<-.>+.<.>+.++++++.<.-."

        val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label.toString(), ResultTextbox.text)
        clipboard.setPrimaryClip(clip)

        val toast = Toast.makeText(
        this, "Copied", Toast.LENGTH_SHORT)
        toast.show()
    }
    fun btToTxt(view: View){
        val bfInput = InputTextbox.text.toString()
        val txtOutput = Interpreter.execute(bfInput)
        ResultTextbox.text = txtOutput.toString()
    }
}