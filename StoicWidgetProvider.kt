package com.ariel.mementoestoico

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.TypedValue
import android.widget.RemoteViews

class StoicWidgetProvider : AppWidgetProvider() {

    companion object {
        const val ACTION_REFRESH_QUOTE = "com.ariel.mementoestoico.ACTION_REFRESH_QUOTE"

        fun updateAllWidgets(context: Context) {
            val manager = AppWidgetManager.getInstance(context)
            val component = ComponentName(context, StoicWidgetProvider::class.java)
            manager.getAppWidgetIds(component).forEach {
                updateWidget(context, manager, it)
            }
        }

        private fun updateWidget(
            context: Context,
            manager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val quote = QuoteRepository.quoteOfTheDay()
            val theme = WidgetThemeStore.load(context)
            val textSize = WidgetThemeStore.loadTextSize(context)
            val views = RemoteViews(context.packageName, R.layout.widget_stoic)

            views.setTextViewText(R.id.widget_quote, "“${quote.text}”")
            views.setTextViewText(R.id.widget_source, "— ${quote.source}")
            views.setTextViewTextSize(R.id.widget_quote, TypedValue.COMPLEX_UNIT_SP, textSize.toFloat())

            when (theme) {
                WidgetTheme.DARK -> {
                    views.setInt(R.id.widget_container, "setBackgroundResource", R.drawable.widget_dark)
                    views.setTextColor(R.id.widget_quote, 0xFFFFFFFF.toInt())
                    views.setTextColor(R.id.widget_source, 0xFFD8B36A.toInt())
                    views.setTextColor(R.id.widget_brand, 0xFFB9B9B9.toInt())
                    views.setTextColor(R.id.widget_refresh, 0xFFE3C078.toInt())
                }
                WidgetTheme.LIGHT -> {
                    views.setInt(R.id.widget_container, "setBackgroundResource", R.drawable.widget_light)
                    views.setTextColor(R.id.widget_quote, 0xFF171717.toInt())
                    views.setTextColor(R.id.widget_source, 0xFF8A6226.toInt())
                    views.setTextColor(R.id.widget_brand, 0xFF696969.toInt())
                    views.setTextColor(R.id.widget_refresh, 0xFF7C581F.toInt())
                }
                WidgetTheme.GLASS -> {
                    views.setInt(R.id.widget_container, "setBackgroundResource", R.drawable.widget_glass)
                    views.setTextColor(R.id.widget_quote, 0xFFFFFFFF.toInt())
                    views.setTextColor(R.id.widget_source, 0xFFF0C879.toInt())
                    views.setTextColor(R.id.widget_brand, 0xFFD5D5D5.toInt())
                    views.setTextColor(R.id.widget_refresh, 0xFFF0C879.toInt())
                }
            }

            val refreshIntent = Intent(context, StoicWidgetProvider::class.java).apply {
                action = ACTION_REFRESH_QUOTE
            }
            val refreshPendingIntent = PendingIntent.getBroadcast(
                context,
                appWidgetId,
                refreshIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            views.setOnClickPendingIntent(R.id.widget_refresh, refreshPendingIntent)

            val openIntent = Intent(context, MainActivity::class.java)
            val openPendingIntent = PendingIntent.getActivity(
                context,
                appWidgetId + 10000,
                openIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            views.setOnClickPendingIntent(R.id.widget_container, openPendingIntent)

            manager.updateAppWidget(appWidgetId, views)
        }
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        appWidgetIds.forEach { updateWidget(context, appWidgetManager, it) }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action == ACTION_REFRESH_QUOTE) {
            updateAllWidgets(context)
        }
    }
}
