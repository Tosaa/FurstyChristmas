
package redtoss.example.furstychristmas.ui.util

import redtoss.example.furstychristmas.R
import redtoss.example.furstychristmas.domain.info.model.InfoPageContent

internal fun InfoPageContent.resolveImageId(imageid: String?): Int? {
    return when (imageid) {
		"emptyright" -> R.drawable.emptyright
		"motions" -> R.drawable.motions
		"cover1man" -> R.drawable.cover1man
		"offensepositions" -> R.drawable.offensepositions
		"sanfranciscomotion" -> R.drawable.sanfranciscomotion
		"gaps" -> R.drawable.gaps
		"dline" -> R.drawable.dline
		"smash" -> R.drawable.smash
		"solidright" -> R.drawable.solidright
		"dagger" -> R.drawable.dagger
		"acerighttreyright" -> R.drawable.acerighttreyright
		"cover3man" -> R.drawable.cover3man
		"cover2man" -> R.drawable.cover2man
		"splitleft" -> R.drawable.splitleft
		"defensebacks" -> R.drawable.defensebacks
		"linebacker" -> R.drawable.linebacker
		"kickoff" -> R.drawable.kickoff
		"defensebasics" -> R.drawable.defensebasics
		"flood" -> R.drawable.flood
        else -> {
            if (!imageid.isNullOrBlank()) {
                Timber.w("Imageid: $imageid could not be resolved!")
            }
            null
        }
}
    