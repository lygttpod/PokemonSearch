package com.lygttpod.android.pokemonsearch.utils

/**
 * <pre>
 *      author  : Allen
 *      date    : 2023/10/26
 *      desc    :
 * </pre>
 */
object ColorUtils {

    enum class Colors(val key: String, val value: String) {
        YELLOW("yellow", "#FFFF00"),
        BLUE("blue", "#0000FF"),
        RED("red", "#FF0000"),
        PINK("pink", "#FFC0CB"),
        GRAY("gray", "#808080"),
        BLACK("black", "#000000"),
        PURPLE("purple", "#800080"),
        WHITE("white", "#FFFFFF"),
        GREEN("green", "#008000"),
        BROWN("brown", "#A52A2A"),
    }

    fun formatColor(name: String?): Colors {
        return Colors.values().find { it.key == name } ?: Colors.WHITE
    }
}