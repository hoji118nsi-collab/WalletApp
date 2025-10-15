package com.example.walletapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

class OperationsDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_operations, container, false)

        // کلیک روی بک‌گراند بسته شدن دیالوگ
        view.findViewById<View>(R.id.overlay).setOnClickListener {
            dismiss()
        }

        // دکمه‌ها رو اینجا هندل می‌کنیم
        view.findViewById<View>(R.id.btnDeposit).setOnClickListener {
            // عملیات ورود پول
        }

        return view
    }
}
