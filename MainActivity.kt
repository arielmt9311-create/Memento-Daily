package com.ariel.mementoestoico

import android.app.Activity
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class MainActivity : Activity() {

    private lateinit var quoteText: TextView
    private lateinit var sourceText: TextView
    private lateinit var dateText: TextView
    private lateinit var previewCard: LinearLayout
    private lateinit var favoriteButton: Button
    private lateinit var textSizeLabel: TextView

    private var previewOffset = 0
    private var currentQuote: StoicQuote = QuoteRepository.quoteOfTheDay()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        buildUi()
        showQuote()
        applyPreviewTheme()
    }

    override fun onResume() {
        super.onResume()
        showQuote()
        applyPreviewTheme()
    }

    private fun dp(v: Int): Int = (v * resources.displayMetrics.density).toInt()

    private fun buildUi() {
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(20), dp(24), dp(20), dp(28))
            setBackgroundColor(0xFF0B0C0F.toInt())
        }

        root.addView(TextView(this).apply {
            text = "MEMENTO"
            textSize = 12f
            letterSpacing = 0.20f
            setTextColor(0xFFD8B36A.toInt())
        })

        root.addView(TextView(this).apply {
            text = "Estoicismo diario"
            textSize = 31f
            setTextColor(0xFFF5F1E8.toInt())
            setPadding(0, dp(4), 0, dp(6))
        })

        root.addView(TextView(this).apply {
            text = "Una idea al día. Sin ruido."
            textSize = 16f
            setTextColor(0xFF9EA2AA.toInt())
        })

        previewCard = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding(dp(22), dp(24), dp(22), dp(22))
            background = getDrawable(R.drawable.card_dark)
        }

        root.addView(previewCard, LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, dp(250)
        ).apply { topMargin = dp(24) })

        dateText = TextView(this).apply {
            textSize = 12f
            letterSpacing = 0.12f
            setTextColor(0xFFB8B8B8.toInt())
        }

        quoteText = TextView(this).apply {
            textSize = 25f
            setTextColor(0xFFFFFFFF.toInt())
            setPadding(0, dp(16), 0, dp(14))
        }

        sourceText = TextView(this).apply {
            textSize = 15f
            setTextColor(0xFFD8B36A.toInt())
        }

        previewCard.addView(dateText)
        previewCard.addView(quoteText, LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f
        ))
        previewCard.addView(sourceText)

        val nav = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
        }
        nav.addView(button("←") { previewOffset--; showQuote() }, LinearLayout.LayoutParams(0, dp(48), 1f))
        nav.addView(button("Hoy") { previewOffset = 0; showQuote() }, LinearLayout.LayoutParams(0, dp(48), 1.2f).apply { marginStart = dp(8); marginEnd = dp(8) })
        nav.addView(button("→") { previewOffset++; showQuote() }, LinearLayout.LayoutParams(0, dp(48), 1f))
        root.addView(nav, LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply { topMargin = dp(12) })

        val actions = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }

        favoriteButton = button("♡ Favorito") {
            val isFav = WidgetThemeStore.toggleFavorite(this@MainActivity, currentQuote)
            favoriteButton.text = if (isFav) "♥ Favorito" else "♡ Favorito"
        }

        val shareButton = button("Compartir") { shareCurrentQuote() }

        actions.addView(favoriteButton, LinearLayout.LayoutParams(0, dp(50), 1f))
        actions.addView(shareButton, LinearLayout.LayoutParams(0, dp(50), 1f).apply { marginStart = dp(8) })
        root.addView(actions, LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply { topMargin = dp(10) })

        root.addView(section("ESTILO DEL WIDGET"))
        val themes = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        themes.addView(themeButton("Oscuro", WidgetTheme.DARK), LinearLayout.LayoutParams(0, dp(48), 1f))
        themes.addView(themeButton("Claro", WidgetTheme.LIGHT), LinearLayout.LayoutParams(0, dp(48), 1f).apply { marginStart = dp(8) })
        themes.addView(themeButton("Cristal", WidgetTheme.GLASS), LinearLayout.LayoutParams(0, dp(48), 1f).apply { marginStart = dp(8) })
        root.addView(themes)

        textSizeLabel = section("TAMAÑO DEL TEXTO: ${WidgetThemeStore.loadTextSize(this)}")
        root.addView(textSizeLabel)

        val seek = SeekBar(this).apply {
            max = 10
            progress = WidgetThemeStore.loadTextSize(this@MainActivity) - 14
            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    val size = 14 + progress
                    textSizeLabel.text = "TAMAÑO DEL TEXTO: $size"
                    WidgetThemeStore.saveTextSize(this@MainActivity, size)
                    StoicWidgetProvider.updateAllWidgets(this@MainActivity)
                }
                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })
        }
        root.addView(seek)

        val widgetRow = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        widgetRow.addView(button("Añadir widget") { requestPinWidget() }, LinearLayout.LayoutParams(0, dp(54), 1f))
        widgetRow.addView(button("Actualizar") { StoicWidgetProvider.updateAllWidgets(this@MainActivity) },
            LinearLayout.LayoutParams(0, dp(54), 1f).apply { marginStart = dp(8) })
        root.addView(widgetRow, LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply { topMargin = dp(18) })

        root.addView(TextView(this).apply {
            text = "v3 • Favoritos • Compartir • Personalización"
            textSize = 12f
            gravity = Gravity.CENTER
            setTextColor(0xFF696D74.toInt())
            setPadding(0, dp(18), 0, 0)
        })

        setContentView(root)
    }

    private fun button(label: String, action: () -> Unit): Button =
        Button(this).apply {
            text = label
            setOnClickListener { action() }
        }

    private fun section(label: String): TextView =
        TextView(this).apply {
            text = label
            textSize = 12f
            letterSpacing = 0.14f
            setTextColor(0xFF8E9299.toInt())
            setPadding(0, dp(22), 0, dp(8))
        }

    private fun themeButton(label: String, theme: WidgetTheme): Button =
        button(label) {
            WidgetThemeStore.save(this, theme)
            applyPreviewTheme()
            StoicWidgetProvider.updateAllWidgets(this)
        }

    private fun showQuote() {
        val date = LocalDate.now().plusDays(previewOffset.toLong())
        currentQuote = QuoteRepository.quoteOfTheDay(date)
        val formatter = DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM", Locale("es", "ES"))

        dateText.text = date.format(formatter).uppercase(Locale("es", "ES"))
        quoteText.text = "“${currentQuote.text}”"
        sourceText.text = "— ${currentQuote.source}"
        favoriteButton.text = if (WidgetThemeStore.isFavorite(this, currentQuote)) "♥ Favorito" else "♡ Favorito"
    }

    private fun shareCurrentQuote() {
        val text = "“${currentQuote.text}”\n— ${currentQuote.source}\n\nMemento — Estoicismo Diario"
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        }
        startActivity(Intent.createChooser(intent, "Compartir frase"))
    }

    private fun requestPinWidget() {
        val manager = AppWidgetManager.getInstance(this)
        if (!manager.isRequestPinAppWidgetSupported) {
            Toast.makeText(this, "Tu launcher no permite añadir widgets desde la app.", Toast.LENGTH_LONG).show()
            return
        }

        val provider = ComponentName(this, StoicWidgetProvider::class.java)
        val successIntent = Intent(this, MainActivity::class.java)
        val successCallback = PendingIntent.getActivity(
            this,
            4501,
            successIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        manager.requestPinAppWidget(provider, null, successCallback)
    }

    private fun applyPreviewTheme() {
        when (WidgetThemeStore.load(this)) {
            WidgetTheme.DARK -> {
                previewCard.background = getDrawable(R.drawable.card_dark)
                quoteText.setTextColor(0xFFFFFFFF.toInt())
                sourceText.setTextColor(0xFFD8B36A.toInt())
                dateText.setTextColor(0xFFB8B8B8.toInt())
            }
            WidgetTheme.LIGHT -> {
                previewCard.background = getDrawable(R.drawable.card_light)
                quoteText.setTextColor(0xFF171717.toInt())
                sourceText.setTextColor(0xFF8A6226.toInt())
                dateText.setTextColor(0xFF666666.toInt())
            }
            WidgetTheme.GLASS -> {
                previewCard.background = getDrawable(R.drawable.card_glass)
                quoteText.setTextColor(0xFFFFFFFF.toInt())
                sourceText.setTextColor(0xFFF0C879.toInt())
                dateText.setTextColor(0xFFD5D5D5.toInt())
            }
        }
    }
}
