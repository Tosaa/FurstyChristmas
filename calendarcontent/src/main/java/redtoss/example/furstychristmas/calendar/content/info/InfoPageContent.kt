package redtoss.example.furstychristmas.calendar.content.info

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InfoPageContent(
    @SerialName("subtitle")
    val title: String,
    val imageid: String? = null,
    @SerialName("htmltext")
    val rawText: String
) {
    val text = parseHTMLtoMarkdownText(rawText)

    private fun parseHTMLtoMarkdownText(text: String): String {
        val newText = text
            .replace("<br/> ", "\n")
            .replace("<br/>", "\n")
            .replace(Regex("<.*>"), "")
        return newText
    }

    override fun toString(): String {
        val titleString = if (title.length > 15)
            "${title.take(5)}...${title.takeLast(5)}"
        else
            title
        val imageString = if (imageid.isNullOrBlank()) {
            "No image"
        } else {
            imageid
        }
        val textString = if (text.length > 15)
            "${text.take(5)}...${text.takeLast(5)}"
        else
            text
        return "InfoPageContent(title=$titleString, image=$imageString, text=$textString)"
    }
}
