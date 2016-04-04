package de.fabmax.calc

import android.content.Context

/**
 * Size specification for layouts. Size specifications can be relative or absolute.
 */
abstract class SizeSpec {

    private var minusSz: SizeSpec? = null
    private var plusSz: SizeSpec? = null

    abstract fun convert(parentWidth: Float, parentHeight: Float, ctx: Context): Float

    fun toPx(parentWidth: Float, parentHeight: Float, ctx: Context): Float {
        return convert(parentWidth, parentHeight, ctx) -
                (minusSz?.toPx(parentWidth, parentHeight, ctx) ?: 0f) +
                (plusSz?.toPx(parentWidth, parentHeight, ctx) ?: 0f)
    }

    operator fun minus(other: SizeSpec): SizeSpec {
        minusSz = other
        return this
    }

    operator fun plus(other: SizeSpec): SizeSpec {
        plusSz = other
        return this
    }
}

fun px(px: Float): SizeSpec {
    return SizeSpecPx(px)
}

fun dp(dp: Float): SizeSpec {
    return SizeSpecDp(dp)
}

fun dp(dp: Float, ctx: Context): Float {
    return dp * ctx.resources.displayMetrics.density
}

fun rw(rw: Float): SizeSpec {
    return SizeSpecRelWidth(rw)
}

fun parentW(): SizeSpec {
    return rw(1f)
}

fun rw(rw: Float, parentW: Float): Float {
    return parentW * rw
}

fun rh(rh: Float): SizeSpec {
    return SizeSpecRelHeight(rh)
}

fun parentH(): SizeSpec {
    return rh(1f)
}

fun rh(rh: Float, parentH: Float): Float {
    return parentH * rh
}

class SizeSpecPx(px: Float) : SizeSpec() {
    val px = px
    override fun convert(parentWidth: Float, parentHeight: Float, ctx: Context): Float {
        return px
    }
}

class SizeSpecDp(dp: Float) : SizeSpec() {
    val dp = dp
    override fun convert(parentWidth: Float, parentHeight: Float, ctx: Context): Float {
        return dp * ctx.resources.displayMetrics.density
    }
}

class SizeSpecRelWidth(relWidth: Float) : SizeSpec() {
    val relWidth = relWidth
    override fun convert(parentWidth: Float, parentHeight: Float, ctx: Context): Float {
        return relWidth * parentWidth
    }
}

class SizeSpecRelHeight(relHeight: Float) : SizeSpec() {
    val relHeight = relHeight
    override fun convert(parentWidth: Float, parentHeight: Float, ctx: Context): Float {
        return relHeight * parentHeight
    }
}
