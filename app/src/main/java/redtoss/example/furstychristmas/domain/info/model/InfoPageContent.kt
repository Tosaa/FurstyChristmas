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
}
