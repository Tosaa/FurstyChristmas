package redtoss.example.furstychristmas.ui.util

import redtoss.example.furstychristmas.R
import redtoss.example.furstychristmas.domain.info.model.InfoPageContent
import timber.log.Timber

internal fun InfoPageContent.resolveImageId(imageid: String?): Int? {
    return when (imageid) {
        "emptyright" -> R.drawable.emptyright
        "cover1man" -> R.drawable.cover1man
        "dline" -> R.drawable.dline
        "solidright" -> R.drawable.solidright
        "cover3man" -> R.drawable.cover3man
        "cover2man" -> R.drawable.cover2man
        "splitleft" -> R.drawable.splitleft
        "defensebacks" -> R.drawable.defensebacks
        "linebacker" -> R.drawable.linebacker
        "defensebasics" -> R.drawable.defensebasics
        else -> {
            if (!imageid.isNullOrBlank()) {
                Timber.w("Imageid: $imageid could not be resolved!")
            }
            null
        }
    }
}
