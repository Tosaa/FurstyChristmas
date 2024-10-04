package redtoss.example.furstychristmas.calendar.content.workout

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(ExecutionSerializer::class)
sealed class Execution(val amount: Int) {

    fun formattedAmount(): String = amount.toString()

    abstract fun withOther(execution: Execution): Execution

    abstract fun unit(): String

    class Repetition(amount: Int) : Execution(amount) {
        override fun withOther(execution: Execution): Execution {
            return Repetition(execution.amount + amount)
        }

        override fun unit(): String = " Wdh"
    }

    class RepetitionPerSide(amount: Int) : Execution(amount) {
        override fun withOther(execution: Execution): Execution {
            return RepetitionPerSide(execution.amount + amount)
        }

        override fun unit(): String = " Wdh/Seite"
    }

    class Seconds(amount: Int) : Execution(amount) {
        override fun withOther(execution: Execution): Execution {
            return Seconds(execution.amount + amount)
        }

        override fun unit(): String = " Sek"
    }

    class SecondsPerSide(amount: Int) : Execution(amount) {
        override fun withOther(execution: Execution): Execution {
            return SecondsPerSide(execution.amount + amount)
        }

        override fun unit(): String = " Sek/Seite"
    }

}


class ExecutionSerializer : KSerializer<Execution> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor(this.javaClass.simpleName, PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Execution {
        val decoded = decoder.decodeString()
        return when {
            decoded.startsWith("duration") -> Execution.Seconds(decoded.split(":")[1].trim().toInt())
            decoded.startsWith("reps") -> Execution.Repetition(decoded.split(":")[1].toInt())
            else -> Execution.Repetition(0)
        }
    }

    override fun serialize(encoder: Encoder, value: Execution) {
        val encoded = when (value) {
            is Execution.Seconds -> "duration : ${value.amount}"
            is Execution.Repetition -> "reps : ${value.amount}"
            else -> "reps : ${value.amount}"
        }
        encoder.encodeString(encoded)
    }

}

