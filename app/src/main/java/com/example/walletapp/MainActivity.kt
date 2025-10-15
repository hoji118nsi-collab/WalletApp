package com.example.walletapp

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.Button
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    private lateinit var walletBalanceText: TextView
    private lateinit var investBalanceText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        walletBalanceText = findViewById(R.id.walletBalance)
        investBalanceText = findViewById(R.id.investBalance)
        val btnOperations = findViewById<Button>(R.id.btnOperations)

        val prefs = getSharedPreferences("wallet_data", Context.MODE_PRIVATE)
        var walletBalance = prefs.getInt("wallet_balance", 0)
        var investBalance = prefs.getInt("invest_balance", 0)

        updateBalanceTexts(walletBalance, investBalance)

        btnOperations.setOnClickListener {
            showOperationsDialog(walletBalance, investBalance) { newWallet, newInvest ->
                // ذخیره تغییرات بعد از انتخاب عملیات
                walletBalance = newWallet
                investBalance = newInvest
                prefs.edit()
                    .putInt("wallet_balance", walletBalance)
                    .putInt("invest_balance", investBalance)
                    .apply()
                updateBalanceTexts(walletBalance, investBalance)
            }
        }
    }

    private fun updateBalanceTexts(wallet: Int, invest: Int) {
        walletBalanceText.text = "${wallet} تومان"
        investBalanceText.text = "${invest} تومان"
    }

    private fun showOperationsDialog(wallet: Int, invest: Int, onUpdate: (Int, Int) -> Unit) {
        val options = arrayOf("افزایش 1000 به کیف پول و 2000 به صندوق", "افزایش 500 به کیف پول و 1000 به صندوق")
        AlertDialog.Builder(this)
            .setTitle("انتخاب عملیات")
            .setItems(options) { dialog, which ->
                when (which) {
                    0 -> onUpdate(wallet + 1000, invest + 2000)
                    1 -> onUpdate(wallet + 500, invest + 1000)
                }
            }
            .setNegativeButton("لغو") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}
