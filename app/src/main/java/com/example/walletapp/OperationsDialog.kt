package com.example.walletapp

import android.graphics.Typeface
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment

class OperationsDialog(
    private val wallet: Int,
    private val invest: Int,
    private val onUpdate: (Int, Int) -> Unit
) : DialogFragment() {

    private var shabnamFont: Typeface? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_operations, container, false)

        // بارگذاری فونت شبنم
        try {
            shabnamFont = Typeface.createFromAsset(requireContext().assets, "fonts/shabnam.ttf")
        } catch (e: Exception) {
            Toast.makeText(context, "فونت شبنم پیدا نشد.", Toast.LENGTH_SHORT).show()
        }

        view.findViewById<View>(R.id.overlay).setOnClickListener { dismiss() }

        // اندازه مربع دیالوگ برای CardView دایره‌ای
        val metrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(metrics)
        val size = (metrics.widthPixels.coerceAtMost(metrics.heightPixels) * 0.8).toInt()
        val dialogCard = view.findViewById<View>(R.id.dialogCard)
        dialogCard.layoutParams.width = size
        dialogCard.layoutParams.height = size
        dialogCard.requestLayout()

        // دکمه‌ها
        val buttons = listOf(
            view.findViewById<Button>(R.id.btnDeposit),
            view.findViewById<Button>(R.id.btnNewPurchase),
            view.findViewById<Button>(R.id.btnViewPurchases),
            view.findViewById<Button>(R.id.btnStats),
            view.findViewById<Button>(R.id.btnFuturePurchases),
            view.findViewById<Button>(R.id.btnTransferInvest)
        )

        // اعمال فونت شبنم
        shabnamFont?.let { font ->
            buttons.forEach { it.typeface = font }
        }

        // رویداد کلیک دکمه‌ها
        view.findViewById<Button>(R.id.btnDeposit).setOnClickListener {
            onUpdate(wallet + 1000, invest + 2000)
            dismiss()
        }
        view.findViewById<Button>(R.id.btnNewPurchase).setOnClickListener {
            onUpdate(wallet + 500, invest + 1000)
            dismiss()
        }
        view.findViewById<Button>(R.id.btnViewPurchases).setOnClickListener {
            Toast.makeText(context, "مشاهده خریدها", Toast.LENGTH_SHORT).show()
        }
        view.findViewById<Button>(R.id.btnStats).setOnClickListener {
            Toast.makeText(context, "آمار ماهانه و سالانه", Toast.LENGTH_SHORT).show()
        }
        view.findViewById<Button>(R.id.btnFuturePurchases).setOnClickListener {
            Toast.makeText(context, "لیست خریدهای آتی", Toast.LENGTH_SHORT).show()
        }
        view.findViewById<Button>(R.id.btnTransferInvest).setOnClickListener {
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
