package com.spotify.demo.extensions

import android.animation.Animator
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.TypedValue
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}


fun View.showHide(show: Boolean, isAnimate: Boolean = false) {
    if (isAnimate) {
        animate().alpha(if (show) 1f else 0f)
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {

                }

                override fun onAnimationEnd(animation: Animator?) {
                    if (!show) hide()
                }

                override fun onAnimationCancel(animation: Animator?) {

                }

                override fun onAnimationStart(animation: Animator?) {
                    if (show) show()
                }

            })
            .start()
    } else if (show) show() else hide()

}


fun View.showGone(show: Boolean) {
    if (show) show() else gone()
}


fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun View.isVisible(): Boolean {
    return visibility == View.VISIBLE
}

fun View.isGone(): Boolean {
    return visibility == View.GONE
}

fun View.isInvisible(): Boolean {
    return visibility == View.INVISIBLE
}

fun View.toPxF(dp: Int): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        resources.displayMetrics
    ).toFloat()
}

fun View.getColorX(@ColorRes colorId: Int): Int {
    return ContextCompat.getColor(context, colorId)
}

fun View.getColorStateList(@ColorRes colorId: Int): ColorStateList? {
    return ContextCompat.getColorStateList(context, colorId)
}


fun View.hideKeyboard() {
    try {
        val imm = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (!imm.isActive) {
            return
        }
        imm.hideSoftInputFromWindow(this.windowToken, 0)
    } catch (ignored: Exception) {

    }
}

fun View.showKeyboard() {
    try {

        if (this.isFocusable) {
            this.requestFocus()
        }

        val inputManager = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        inputManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)

    } catch (ignored: Exception) {

    }
}


/**
 * Set an onclick listener
 */
fun <T : View> T.click(block: (T) -> Unit) = setOnClickListener {
    it?.hideKeyboard()
    block(it as T)
}

/**
 * Set an onclick listener
 */
fun <T : View> T.click(block: (T) -> Unit, hideKeyboard: Boolean = true) = setOnClickListener {
    if (hideKeyboard)
        it?.hideKeyboard()
    block(it as T)
}

/**
 * Extension method to remove the required boilerplate for running code after a view has been
 * inflated and measured.
 *
 * @author Antonio Leiva
 * @see <a href="https://antonioleiva.com/kotlin-ongloballayoutlistener/>Kotlin recipes: OnGlobalLayoutListener</a>
 */
inline fun <T : View> T.afterMeasured(crossinline f: T.() -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            if (!viewTreeObserver.isAlive) {
                return
            }
            if (measuredWidth > 0 && measuredHeight > 0) {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                f()
            }
        }
    })
}

/**
 * Extension method to get a view as bitmap.
 */
fun View.getBitmap(): Bitmap {
    val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bmp)
    draw(canvas)
    canvas.save()
    return bmp
}

fun View.getBitmap(x: Int, y: Int, mWidth: Int, mHeight: Int): Bitmap {
    val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bmp)
    draw(canvas)
    canvas.save()

    return Bitmap.createBitmap(bmp, x, y, mWidth, mHeight)
}

/**
 * Extension method to provide simpler access to {@link View#getResources()#getString(int)}.
 */
fun View.getString(stringResId: Int): String = resources.getString(stringResId)

