package com.example.walletapp

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.Button
import androidx.fragment.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.util.DisplayMetrics
import android.widget.Toast

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

    private fun updateBalanceTexts(wallet: Int, invest: Int) {
        walletBalanceText.text = "${wallet} تومان"
        investBalanceText.text = "${invest} تومان"
    }
}

/** Dialog صفحه عملیات با دکمه‌ها و اندازه داینامیک */
class OperationsDialog(
    private val wallet: Int,
    private val invest: Int,
    private val onUpdate: (Int, Int) -> Unit
) : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_operations, container, false)

        // وقتی روی بک‌گراند کلیک شد، دیالوگ بسته شود
        view.findViewById<View>(R.id.overlay).setOnClickListener {
            dismiss()
        }

        // محاسبه اندازه مربعی متناسب با صفحه
        val metrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(metrics)
        val size = (metrics.widthPixels.coerceAtMost(metrics.heightPixels) * 0.8).toInt()
        view.findViewById<View>(R.id.dialogCard).layoutParams.width = size
        view.findViewById<View>(R.id.dialogCard).layoutParams.height = size

        // دکمه‌ها را هندل می‌کنیم
        view.findViewById<View>(R.id.btnDeposit).setOnClickListener {
            onUpdate(wallet + 1000, invest + 2000)
            dismiss()
        }
        view.findViewById<View>(R.id.btnNewPurchase).setOnClickListener {
            onUpdate(wallet + 500, invest + 1000)
            dismiss()
        }

        // دکمه‌های دیگر برای ساعت 3، 5، 6 و 7
        view.findViewById<View>(R.id.btnViewPurchases)?.setOnClickListener {
            Toast.makeText(context, "مشاهده خریدها", Toast.LENGTH_SHORT).show()
        }
        view.findViewById<View>(R.id.btnMonthlyStats)?.setOnClickListener {
            Toast.makeText(context, "آمار ماهانه و سالانه", Toast.LENGTH_SHORT).show()
        }
        view.findViewById<View>(R.id.btnFuturePurchases)?.setOnClickListener {
            Toast.makeText(context, "لیست خریدهای آتی", Toast.LENGTH_SHORT).show()
        }
        view.findViewById<View>(R.id.btnTransferInvest)?.setOnClickListener {
            onUpdate(wallet, invest + wallet) // انتقال کل موجودی به صندوق
            dismiss()
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }
}
