package redtoss.example.furstychristmas.screen.day.info

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import redtoss.example.furstychristmas.BuildConfig
import timber.log.Timber

@BindingAdapter("android:srcname")
fun setImageViewResourceByName(imageView: ImageView, name: String?) {
    if (!name.isNullOrEmpty()) {
        val imageId = imageView.resources.getIdentifier(name, "drawable", BuildConfig.APPLICATION_ID)
        Timber.d("bind: $name:$imageId")
        imageView.setImageResource(imageId)
    }
}
