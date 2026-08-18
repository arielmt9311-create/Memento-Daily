package com.ariel.mementoestoico

import android.content.Context

enum class WidgetTheme {
    DARK, LIGHT, GLASS
}

object WidgetThemeStore {
    private const val PREFS = "memento_preferences"
    private const val KEY_THEME = "widget_theme"
    private const val KEY_TEXT_SIZE = "widget_text_size"
    private const val KEY_FAVORITES = "favorites"

    fun load(context: Context): WidgetTheme {
        val value = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getString(KEY_THEME, WidgetTheme.DARK.name)
        return runCatching { WidgetTheme.valueOf(value ?: WidgetTheme.DARK.name) }
            .getOrDefault(WidgetTheme.DARK)
    }

    fun save(context: Context, theme: WidgetTheme) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_THEME, theme.name)
            .apply()
    }

    fun loadTextSize(context: Context): Int {
        return context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getInt(KEY_TEXT_SIZE, 18)
    }

    fun saveTextSize(context: Context, size: Int) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit()
            .putInt(KEY_TEXT_SIZE, size.coerceIn(14, 24))
            .apply()
    }

    fun isFavorite(context: Context, quote: StoicQuote): Boolean {
        return favoriteKeys(context).contains(keyFor(quote))
    }

    fun toggleFavorite(context: Context, quote: StoicQuote): Boolean {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val set = favoriteKeys(context).toMutableSet()
        val key = keyFor(quote)
        val nowFavorite = if (set.contains(key)) {
            set.remove(key)
            false
        } else {
            set.add(key)
            true
        }
        prefs.edit().putStringSet(KEY_FAVORITES, set).apply()
        return nowFavorite
    }

    private fun favoriteKeys(context: Context): Set<String> {
        return context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getStringSet(KEY_FAVORITES, emptySet())
            ?.toSet()
            ?: emptySet()
    }

    private fun keyFor(quote: StoicQuote): String = "${quote.text}||${quote.source}"
}
