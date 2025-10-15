package com.example.walletapp

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/** صفحه اصلی اپلیکیشن با نمایش موجودی کیف پول و صندوق */
class MainActivity : AppCompatActivity() {

    private lateinit var walletBalanceText: TextView
    private lateinit var investBalanceText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // گرفتن ارجاع ویوها
        walletBalanceText = findViewById(R.id.walletBalance)
        investBalanceText = findViewById(R.id.investBalance)
        val btnOperations = findViewById<Button>(R.id.btnOperations)

        // خواندن موجودی‌ها از SharedPreferences
        val prefs = getSharedPreferences("wallet_data", Context.MODE_PRIVATE)
        var walletBalance = prefs.getInt("wallet_balance", 0)
        var investBalance = prefs.getInt("invest_balance", 0)

        updateBalanceTexts(walletBalance, investBalance)

        // باز کردن دیالوگ عملیات
        btnOperations.setOnClickListener {
            val dialog = OperationsDialog(walletBalance, investBalance) { newWallet, newInvest ->
                walletBalance = newWallet
                investBalance = newInvest
                prefs.edit()
                    .putInt("wallet_balance", walletBalance)
                    .putInt("invest_balance", investBalance)
                    .apply()
                updateBalanceTexts(walletBalance, investBalance)
            }
            dialog.show(supportFragmentManager, "OperationsDialog")
        }
    }

    /** بروزرسانی متن موجودی‌ها روی صفحه */
    private fun updateBalanceTexts(wallet: Int, invest: Int) {
        walletBalanceText.text = "${wallet} تومان"
        investBalanceText.text = "${invest} تومان"
    }
}
