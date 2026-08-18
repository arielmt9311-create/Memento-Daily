package com.ariel.mementoestoico

import java.time.LocalDate
import java.time.ZoneId

data class StoicQuote(
    val text: String,
    val source: String
)

object QuoteRepository {

    private val quotes = listOf(
        StoicQuote("No controles el día; controla la forma en que lo recibes.", "Reflexión estoica"),
        StoicQuote("Lo que depende de ti merece tu energía. Lo demás merece aceptación.", "Inspirada en Epicteto"),
        StoicQuote("Una mente tranquila convierte el obstáculo en práctica.", "Inspirada en Marco Aurelio"),
        StoicQuote("Antes de reaccionar, pregúntate si esto realmente está bajo tu control.", "Reflexión estoica"),
        StoicQuote("Tu carácter se construye en las decisiones pequeñas que nadie ve.", "Reflexión estoica"),
        StoicQuote("No necesitas que el mundo coopere para actuar con virtud.", "Inspirada en Marco Aurelio"),
        StoicQuote("La incomodidad de hoy puede ser el entrenamiento de mañana.", "Inspirada en Séneca"),
        StoicQuote("No discutas con la realidad. Decide qué hacer con ella.", "Reflexión estoica"),
        StoicQuote("El juicio que haces de un problema puede pesar más que el problema.", "Inspirada en Epicteto"),
        StoicQuote("La calma también es una forma de fuerza.", "Reflexión estoica"),
        StoicQuote("Haz bien lo que tienes delante y deja que el resultado llegue cuando deba.", "Reflexión estoica"),
        StoicQuote("Una buena vida se construye con buenas respuestas, no con circunstancias perfectas.", "Reflexión estoica"),
        StoicQuote("Quien domina su impulso gana una batalla que nadie más puede ganar por él.", "Reflexión estoica"),
        StoicQuote("No pidas menos dificultades; desarrolla más fortaleza.", "Inspirada en Séneca"),
        StoicQuote("Hoy puedes elegir entre alimentar la queja o fortalecer el carácter.", "Reflexión estoica"),
        StoicQuote("El pasado ya no obedece. El futuro todavía no existe. Trabaja con este momento.", "Reflexión estoica"),
        StoicQuote("No todo merece una respuesta; algunas cosas merecen silencio.", "Reflexión estoica"),
        StoicQuote("Tu paz no debe depender de que otros actúen como esperas.", "Inspirada en Epicteto"),
        StoicQuote("Recuerda que perder tiempo también es gastar vida.", "Inspirada en Séneca"),
        StoicQuote("Haz lo correcto aunque nadie aplauda.", "Reflexión estoica"),
        StoicQuote("La paciencia no es pasividad; es fuerza sin desperdicio.", "Reflexión estoica"),
        StoicQuote("Cuando no puedas cambiar la situación, cambia la calidad de tu respuesta.", "Reflexión estoica"),
        StoicQuote("La disciplina de hoy protege la libertad de mañana.", "Reflexión estoica"),
        StoicQuote("No conviertas una mala hora en un mal día.", "Reflexión estoica"),
        StoicQuote("Acepta el hecho, examina tu juicio y elige tu acción.", "Reflexión estoica"),
        StoicQuote("La opinión ajena no es una orden.", "Inspirada en Epicteto"),
        StoicQuote("Lo suficiente puede sentirse abundante cuando dejas de compararlo.", "Inspirada en Séneca"),
        StoicQuote("No esperes sentirte listo para actuar correctamente.", "Reflexión estoica"),
        StoicQuote("Un contratiempo puede quitarte comodidad, pero no tiene por qué quitarte dignidad.", "Reflexión estoica"),
        StoicQuote("La mente entrenada pregunta: ¿qué puedo aprender de esto?", "Reflexión estoica"),
        StoicQuote("La mejor respuesta al caos es una acción sencilla y correcta.", "Reflexión estoica"),
        StoicQuote("No cargues dos veces con el dolor: una por el hecho y otra por imaginarlo sin descanso.", "Inspirada en Séneca"),
        StoicQuote("El enojo promete poder, pero muchas veces entrega el control.", "Reflexión estoica"),
        StoicQuote("Haz espacio entre lo que ocurre y lo que decides hacer.", "Reflexión estoica"),
        StoicQuote("La fortuna cambia; el carácter puede permanecer.", "Reflexión estoica"),
        StoicQuote("No necesitas ganar cada discusión para conservar tu respeto propio.", "Reflexión estoica"),
        StoicQuote("Una vida simple deja más espacio para una mente clara.", "Reflexión estoica"),
        StoicQuote("El obstáculo revela qué parte de ti todavía necesita entrenamiento.", "Inspirada en Marco Aurelio"),
        StoicQuote("No te preguntes qué mereces; pregúntate qué exige de ti la virtud.", "Reflexión estoica"),
        StoicQuote("La preocupación sin acción es interés pagado por una deuda que quizá nunca llegue.", "Reflexión estoica"),
        StoicQuote("Sé exigente con tus actos y paciente con lo que no controlas.", "Reflexión estoica"),
        StoicQuote("No confundas urgencia con importancia.", "Reflexión estoica"),
        StoicQuote("Hoy también es una oportunidad para practicar templanza.", "Reflexión estoica"),
        StoicQuote("La adversidad no pregunta si estás preparado; por eso entrenas antes.", "Reflexión estoica"),
        StoicQuote("Respira, observa y después decide.", "Reflexión estoica"),
        StoicQuote("La dignidad no necesita ruido.", "Reflexión estoica"),
        StoicQuote("Si algo puede resolverse, actúa. Si no puede, acepta sin rendirte por dentro.", "Reflexión estoica"),
        StoicQuote("No hagas de la comodidad tu brújula.", "Reflexión estoica"),
        StoicQuote("El día mejora cuando dejas de exigirle que sea distinto.", "Reflexión estoica"),
        StoicQuote("Tu atención es parte de tu vida. Gástala con cuidado.", "Inspirada en Séneca"),
        StoicQuote("La serenidad nace cuando distingues deseo de necesidad.", "Reflexión estoica"),
        StoicQuote("Una persona difícil también puede ser una práctica de paciencia.", "Inspirada en Marco Aurelio"),
        StoicQuote("No esperes controlar a otros para sentirte en control de ti.", "Inspirada en Epicteto"),
        StoicQuote("La virtud no necesita condiciones ideales.", "Reflexión estoica"),
        StoicQuote("El cansancio explica una reacción; no siempre la justifica.", "Reflexión estoica"),
        StoicQuote("Haz menos promesas y más acciones coherentes.", "Reflexión estoica"),
        StoicQuote("Lo que hoy parece pérdida puede enseñarte qué era realmente importante.", "Reflexión estoica"),
        StoicQuote("Tu primer deber es gobernarte a ti mismo.", "Reflexión estoica"),
        StoicQuote("Que algo sea difícil no significa que sea malo.", "Reflexión estoica"),
        StoicQuote("No persigas tranquilidad evitando la vida; encuéntrala aprendiendo a enfrentarla.", "Reflexión estoica"),
        StoicQuote("La crítica útil se aprovecha; la inútil se deja pasar.", "Reflexión estoica"),
        StoicQuote("No te robes el presente ensayando desgracias futuras.", "Inspirada en Séneca"),
        StoicQuote("La libertad comienza cuando puedes decir no a tu propio impulso.", "Reflexión estoica"),
        StoicQuote("Una decisión virtuosa vale incluso cuando el resultado no sale como querías.", "Reflexión estoica"),
        StoicQuote("Tu mente es tu territorio más cercano. Empieza por ordenarlo.", "Inspirada en Marco Aurelio"),
        StoicQuote("No necesitas tener la última palabra para tener razón contigo mismo.", "Reflexión estoica"),
        StoicQuote("Practica la gratitud antes de que la pérdida te enseñe el valor.", "Reflexión estoica"),
        StoicQuote("La vida no promete facilidad; tú puedes prometerte integridad.", "Reflexión estoica"),
        StoicQuote("Cada demora es una ocasión para practicar paciencia.", "Reflexión estoica"),
        StoicQuote("Antes de culpar, revisa qué parte sí te corresponde.", "Reflexión estoica"),
        StoicQuote("La comparación convierte lo suficiente en poco.", "Reflexión estoica"),
        StoicQuote("No te definas por el golpe; defínete por cómo te levantas.", "Reflexión estoica"),
        StoicQuote("A veces avanzar significa dejar de pelear con lo inevitable.", "Reflexión estoica"),
        StoicQuote("El autocontrol protege lo que la impulsividad puede destruir en segundos.", "Reflexión estoica"),
        StoicQuote("La opinión de hoy puede cambiar mañana. Tu carácter viaja contigo.", "Reflexión estoica"),
        StoicQuote("No esperes que el miedo desaparezca; actúa con criterio a pesar de él.", "Reflexión estoica"),
        StoicQuote("Vive de forma que tus decisiones puedan explicarse sin excusas.", "Reflexión estoica"),
        StoicQuote("Lo que repites se convierte en parte de ti.", "Reflexión estoica"),
        StoicQuote("El tiempo es limitado; elige bien qué merece enfadarte.", "Inspirada en Séneca"),
        StoicQuote("No conviertas el deseo de justicia en permiso para perder la templanza.", "Reflexión estoica"),
        StoicQuote("La realidad no te debe comodidad, pero tú sí te debes una respuesta digna.", "Reflexión estoica"),
        StoicQuote("Quien sabe qué puede controlar deja de desperdiciar fuerza.", "Inspirada en Epicteto"),
        StoicQuote("Una pausa breve puede evitar un arrepentimiento largo.", "Reflexión estoica"),
        StoicQuote("No midas el día solo por lo que lograste, sino por cómo actuaste.", "Reflexión estoica"),
        StoicQuote("Agradece lo que tienes sin convertirlo en algo que temes perder.", "Reflexión estoica"),
        StoicQuote("La humildad acepta corrección sin sentirse destruida.", "Reflexión estoica"),
        StoicQuote("Ningún día es pequeño si lo usas para mejorar tu carácter.", "Reflexión estoica"),
        StoicQuote("El control de uno mismo vale más que el control de una conversación.", "Reflexión estoica"),
        StoicQuote("Haz tu parte completa, aunque tu parte sea pequeña.", "Reflexión estoica"),
        StoicQuote("No negocies tus principios por una emoción momentánea.", "Reflexión estoica"),
        StoicQuote("Lo inevitable se vuelve más pesado cuando además lo rechazas.", "Reflexión estoica"),
        StoicQuote("La tranquilidad no es ausencia de problemas; es claridad frente a ellos.", "Reflexión estoica"),
        StoicQuote("No todo pensamiento merece convertirse en creencia.", "Reflexión estoica"),
        StoicQuote("Puedes perder una ventaja sin perder tu capacidad de actuar bien.", "Reflexión estoica"),
        StoicQuote("El presente pide atención, no dramatización.", "Reflexión estoica"),
        StoicQuote("La fortaleza se practica antes de necesitarla.", "Reflexión estoica"),
        StoicQuote("Habla menos desde el impulso y más desde el propósito.", "Reflexión estoica"),
        StoicQuote("No juzgues toda tu vida por un día difícil.", "Reflexión estoica"),
        StoicQuote("Si puedes corregirlo, corrígelo. Si no, aprende y continúa.", "Reflexión estoica"),
        StoicQuote("La constancia vence muchas veces a la intensidad.", "Reflexión estoica")
    )

    fun quoteOfTheDay(date: LocalDate = LocalDate.now(ZoneId.systemDefault())): StoicQuote {
        val index = Math.floorMod(date.toEpochDay(), quotes.size.toLong()).toInt()
        return quotes[index]
    }

    fun quoteForOffset(offset: Int): StoicQuote {
        val date = LocalDate.now(ZoneId.systemDefault()).plusDays(offset.toLong())
        return quoteOfTheDay(date)
    }
}
