package com.example.walletapp

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        view.findViewById<View>(R.id.dialogCard).layoutParams.width = size
        view.findViewById<View>(R.id.dialogCard).layoutParams.height = size

        // دکمه‌ها و هندل کردن آنها
        view.findViewById<View>(R.id.btnDeposit).setOnClickListener {
            onUpdate(wallet + 1000, invest + 2000)
            dismiss()
        }
        view.findViewById<View>(R.id.btnNewPurchase).setOnClickListener {
            onUpdate(wallet + 500, invest + 1000)
            dismiss()
        }
        view.findViewById<View>(R.id.btnViewPurchases).setOnClickListener {
            Toast.makeText(context, "مشاهده خریدها", Toast.LENGTH_SHORT).show()
        }
        // دکمه آمار ماهانه: اگر میخوای فعال باشد، XML هم id اضافه شود
        // view.findViewById<View>(R.id.btnMonthlyStats)?.setOnClickListener {
        //     Toast.makeText(context, "آمار ماهانه و سالانه", Toast.LENGTH_SHORT).show()
        // }
        view.findViewById<View>(R.id.btnFuturePurchases).setOnClickListener {
            Toast.makeText(context, "لیست خریدهای آتی", Toast.LENGTH_SHORT).show()
        }
        view.findViewById<View>(R.id.btnTransferInvest).setOnClickListener {
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
