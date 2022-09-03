@file:OptIn(ExperimentalPagerApi::class)

package redtoss.example.furstychristmas.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import org.koin.androidx.compose.get
import redtoss.example.furstychristmas.ui.viewmodel.InfoViewModel
import timber.log.Timber
import java.time.LocalDate

@Composable
fun CalendarInfoDay(
    viewmodel: InfoViewModel = get(),
    day: LocalDate,
    onClose: () -> Unit,
) {
    viewmodel.date = day
    val title = viewmodel.title.collectAsState(initial = "")
    val pages = viewmodel.pages.collectAsState(initial = emptyList())

    Column {
        Text(
            text = title.value,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            textDecoration = TextDecoration.Underline,
            fontSize = 26.sp
        )
        HorizontalPager(
            count = pages.value?.size ?: 0,
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxHeight()
        ) { page ->
            val pageContent = pages.value?.get(page)
            Timber.d("Show Page: $pageContent")
            DailyContent(
                title = pageContent?.title ?: "",
                content = pageContent?.text ?: "",
                imageID = pageContent?.image ?: 0,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun DailyContent(modifier: Modifier = Modifier, title: String = "", content: String = "", imageID: Int = 0) {
    Card(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column {
            if (title.isNotBlank())
                Text(
                    text = title,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
            if (content.isNotBlank())
                Text(text = content)
            if (imageID != 0)
                Image(
                    painter = painterResource(id = imageID),
                    contentDescription = imageID.toString(),
                    modifier = Modifier.padding(8.dp)
                )
        }
    }
}

