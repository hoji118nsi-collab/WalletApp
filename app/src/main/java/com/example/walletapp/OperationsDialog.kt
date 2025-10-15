package com.example.walletapp

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment

/** Dialog صفحه عملیات با دکمه‌ها و اندازه داینامیک */
class OperationsDialog(
    private val wallet: Int,
    private val invest: Int,
    private val onUpdate: (Int, Int) -> Unit
) : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_operations, container, false)

        // وقتی روی بک‌گراند کلیک شد، دیالوگ بسته شود
        view.findViewById<View>(R.id.overlay).setOnClickListener { dismiss() }

        // محاسبه اندازه مربعی متناسب با صفحه
        val metrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(metrics)
        val size = (metrics.widthPixels.coerceAtMost(metrics.heightPixels) * 0.8).toInt()
        val dialogCard = view.findViewById<View>(R.id.dialogCard)
        dialogCard.layoutParams.width = size
        dialogCard.layoutParams.height = size

        // دکمه‌ها و هندل کردن آنها
        val btnDeposit = view.findViewById<Button>(R.id.btnDeposit)
        val btnNewPurchase = view.findViewById<Button>(R.id.btnNewPurchase)
        val btnViewPurchases = view.findViewById<Button>(R.id.btnViewPurchases)
        val btnStats = view.findViewById<Button>(R.id.btnStats)
        val btnFuturePurchases = view.findViewById<Button>(R.id.btnFuturePurchases)
        val btnTransferInvest = view.findViewById<Button>(R.id.btnTransferInvest)

        // رویدادهای کلیک دکمه‌ها
        btnDeposit.setOnClickListener {
            onUpdate(wallet + 1000, invest + 2000)
            dismiss()
        }

        btnNewPurchase.setOnClickListener {
            onUpdate(wallet + 500, invest + 1000)
            dismiss()
        }

        btnViewPurchases.setOnClickListener {
            Toast.makeText(context, "مشاهده خریدها", Toast.LENGTH_SHORT).show()
        }

        btnStats.setOnClickListener {
            Toast.makeText(context, "آمار ماهانه و سالانه", Toast.LENGTH_SHORT).show()
        }

        btnFuturePurchases.setOnClickListener {
            Toast.makeText(context, "لیست خریدهای آتی", Toast.LENGTH_SHORT).show()
        }

        btnTransferInvest.setOnClickListener {
            onUpdate(wallet, invest + wallet)
            dismiss()
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }
}
