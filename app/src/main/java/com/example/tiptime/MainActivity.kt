package com.example.tiptime

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculateButton.setOnClickListener { calculateTip() }
        binding.totalBillAmountEditText.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode) }
    }

    private fun calculateTip(){
//        The content in the EditView is an editable which is neither string double integer or char. you first need to make it a string using toString( val totalAmount = binding.totalBillAmount.text.toString() and then to any other data type
        val totalAmount = binding.totalBillAmountEditText.text.toString()
        val cost = totalAmount.toDoubleOrNull()
        val tipPercent = when(binding.tipOptions.checkedRadioButtonId){
            R.id.twenty_percent -> 0.20
            R.id.fifteen_percent -> 0.15
            R.id.ten_percent -> 0.10
            R.id.five_percent -> 0.05
            else -> 0.00
        }
        if (cost==null){
            binding.tipAmount.text=""
            return
        }
        var tip = cost*tipPercent
        if(binding.roundSwitch.isChecked){
            tip = ceil(tip)
        }
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipAmount.text= getString(R.string.tip_amount, formattedTip)
    }
    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}