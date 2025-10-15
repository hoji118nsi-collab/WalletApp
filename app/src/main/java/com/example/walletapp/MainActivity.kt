package com.example.walletapp

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/** صفحه اصلی اپلیکیشن با نمایش موجودی کیف پول و صندوق */
class MainActivity : AppCompatActivity() {

    private lateinit var walletBalanceText: TextView
    private lateinit var investBalanceText: TextView
    private lateinit var btnOperations: Button
    private var shabnamFont: Typeface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ✅ بارگذاری امن فونت شبنم از res/font
        // در صورتی که فونت در res/font قرار دارد، نیازی به assets نیست.
        try {
            shabnamFont = resources.getFont(R.font.shabnam)
        } catch (e: Exception) {
            Toast.makeText(this, "⚠️ فونت شبنم پیدا نشد، از فونت پیش‌فرض استفاده می‌شود.", Toast.LENGTH_SHORT).show()
        }

        // گرفتن ارجاع ویوها
        walletBalanceText = findViewById(R.id.walletBalance)
        investBalanceText = findViewById(R.id.investBalance)
        btnOperations = findViewById(R.id.btnOperations)

        // اعمال فونت شبنم اگر موجود بود
        shabnamFont?.let {
            walletBalanceText.typeface = it
            investBalanceText.typeface = it
            btnOperations.typeface = it
        }

        // خواندن موجودی‌ها از SharedPreferences
        val prefs = getSharedPreferences("wallet_data", Context.MODE_PRIVATE)
        var walletBalance = prefs.getInt("wallet_balance", 0)
        var investBalance = prefs.getInt("invest_balance", 0)

        updateBalanceTexts(walletBalance, investBalance)

        // باز کردن دیالوگ عملیات با هندل کردن کرش احتمالی
        btnOperations.setOnClickListener {
            try {
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
            } catch (e: Exception) {
                Toast.makeText(this, "خطا در باز کردن دیالوگ: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    /** بروزرسانی متن موجودی‌ها روی صفحه */
    private fun updateBalanceTexts(wallet: Int, invest: Int) {
        walletBalanceText.text = "${wallet} تومان"
        investBalanceText.text = "${invest} تومان"
    }
}
