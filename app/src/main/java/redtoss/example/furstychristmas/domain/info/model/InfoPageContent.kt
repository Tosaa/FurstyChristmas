package redtoss.example.furstychristmas.domain.info.model

import redtoss.example.furstychristmas.ui.util.resolveImageId

data class InfoPageContent(val title: String, val imageid: String?, private val rawText: String) {
    val image: Int? = resolveImageId(imageid)
    val text = parseHTMLtoMarkdownText(rawText)

    private fun parseHTMLtoMarkdownText(text: String): String {
        val newText = text
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
