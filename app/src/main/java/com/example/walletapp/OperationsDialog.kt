package com.example.walletapp

import android.graphics.Typeface
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment

class OperationsDialog(
    private val wallet: Int,
    private val invest: Int,
    private val onUpdate: (Int, Int) -> Unit
) : DialogFragment() {

    private var shabnamFont: Typeface? = null
    private var dialogCard: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_operations, container, false)

        // بارگذاری فونت شبنم
        try {
            shabnamFont = Typeface.createFromAsset(requireContext().assets, "fonts/shabnam.ttf")
        } catch (e: Exception) {
            Toast.makeText(context, "فونت شبنم پیدا نشد.", Toast.LENGTH_SHORT).show()
        }

        // بستن دیالوگ با کلیک روی فضای بیرونی
        view.findViewById<View>(R.id.overlay).setOnClickListener {
            playExitAnimationAndDismiss()
        }

        // تنظیم اندازه CardView
        val metrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(metrics)
        val size = (metrics.widthPixels.coerceAtMost(metrics.heightPixels) * 0.85).toInt()
        dialogCard = view.findViewById(R.id.dialogCard)
        dialogCard?.layoutParams?.width = size
        dialogCard?.layoutParams?.height = size
        dialogCard?.requestLayout()

        // انیمیشن ورود
        val zoomIn = AnimationUtils.loadAnimation(requireContext(), R.anim.zoom_in_bounce)
        dialogCard?.startAnimation(zoomIn)

        // دکمه‌ها
        val buttons = listOf(
            view.findViewById<Button>(R.id.btnDeposit),
            view.findViewById<Button>(R.id.btnNewPurchase),
            view.findViewById<Button>(R.id.btnViewPurchases),
            view.findViewById<Button>(R.id.btnStats),
            view.findViewById<Button>(R.id.btnFuturePurchases),
            view.findViewById<Button>(R.id.btnTransferInvest)
        )

        // اعمال فونت و افکت ripple/برجسته
        shabnamFont?.let { font ->
            buttons.forEach {
                it.typeface = font
                it.background = resources.getDrawable(R.drawable.btn_circle_ripple, null)
                it.textSize = 13f // متن کمی بزرگ‌تر
            }
        }

        // کلیک‌ها با متن جدید
        view.findViewById<Button>(R.id.btnDeposit).apply {
            text = "ورود پول"
            setOnClickListener {
                onUpdate(wallet + 1000, invest + 2000)
                playExitAnimationAndDismiss()
            }
        }

        view.findViewById<Button>(R.id.btnNewPurchase).apply {
            text = "خرید جدید"
            setOnClickListener {
                onUpdate(wallet + 500, invest + 1000)
                playExitAnimationAndDismiss()
            }
        }

        view.findViewById<Button>(R.id.btnViewPurchases).apply {
            text = "مشاهده خریدها"
            setOnClickListener {
                Toast.makeText(context, "مشاهده خریدها", Toast.LENGTH_SHORT).show()
            }
        }

        view.findViewById<Button>(R.id.btnStats).apply {
            text = "آمار"
            setOnClickListener {
                Toast.makeText(context, "آمار", Toast.LENGTH_SHORT).show()
            }
        }

        view.findViewById<Button>(R.id.btnFuturePurchases).apply {
            text = "خریدهای آتی"
            setOnClickListener {
                Toast.makeText(context, "خریدهای آتی", Toast.LENGTH_SHORT).show()
            }
        }

        view.findViewById<Button>(R.id.btnTransferInvest).apply {
            text = "سرمایه گذاری"
            setOnClickListener {
                onUpdate(wallet, invest + wallet)
                playExitAnimationAndDismiss()
            }
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    private fun playExitAnimationAndDismiss() {
        dialogCard?.let { card ->
            val zoomOut = AnimationUtils.loadAnimation(requireContext(), R.anim.zoom_out_fade)
            zoomOut.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}
                override fun onAnimationEnd(animation: Animation?) {
                    dismissAllowingStateLoss()
                }
                override fun onAnimationRepeat(animation: Animation?) {}
            })
            card.startAnimation(zoomOut)
        } ?: dismissAllowingStateLoss()
    }
}
