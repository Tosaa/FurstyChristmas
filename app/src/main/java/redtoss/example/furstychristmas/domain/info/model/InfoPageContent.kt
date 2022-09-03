package redtoss.example.furstychristmas.domain.info.model

import redtoss.example.furstychristmas.R

data class InfoPageContent(val title: String, val imageid: String?, val text: String) {
    val image = when (imageid) {
        "dline" -> R.drawable.dline
        "gaps" -> R.drawable.gaps
        else -> null
    }
}