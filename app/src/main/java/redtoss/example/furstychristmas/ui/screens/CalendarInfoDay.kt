
package redtoss.example.furstychristmas.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import redtoss.example.furstychristmas.R
import redtoss.example.furstychristmas.calendar.content.info.InfoPageContent
import redtoss.example.furstychristmas.ui.theme.DayCompleted
import redtoss.example.furstychristmas.ui.util.image
import redtoss.example.furstychristmas.ui.viewmodel.InfoViewModel

@Composable
fun CalendarInfoDay(
    viewmodel: InfoViewModel = koinViewModel(),
    day: LocalDate,
    onClose: () -> Unit,
) {
    viewmodel.date = day
    val title = viewmodel.title.collectAsState(initial = "")
    val pages = viewmodel.pages.collectAsState(initial = emptyList())
    Info(date = viewmodel.date, isDone = viewmodel.isDone, title = title, pages = pages) {
        viewmodel.setDateAsDone(day)
        onClose()
    }
}

@Composable
private fun Info(
    date: LocalDate,
    isDone: Boolean,
    title: State<String>,
    pages: State<List<InfoPageContent>>,
    onDayCompleted: () -> Unit = {}
) {
    Surface {
        Column {
            // CalendarHeader(date = date, isDone = isDone)
            ContentTitle(title = title)
            ContentPager(pages, onDayCompleted)
        }
    }
}

@Composable
private fun ContentPager(pages: State<List<InfoPageContent>>, onDayCompleted: () -> Unit) {
    val pagerState = rememberPagerState(pageCount = {pages.value.size})
    val pagerScope = rememberCoroutineScope()
    HorizontalPager(
        verticalAlignment = Alignment.Top,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxHeight(),
        state = pagerState
    ) { page ->
        val pageContent = pages.value[page]
        // Timber.d("Show Page: $page - $pageContent")
        DailyContent(
            title = pageContent.title,
            content = pageContent.text,
            imageID = pageContent.image ?: 0,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxHeight(),
            isLastPage = page == pages.value.size - 1,
            onNextPage = { pagerScope.launch { pagerState.animateScrollToPage(page + 1) } },
            onContentRead = onDayCompleted
        )
    }
}

@Composable
private fun ContentTitle(title: State<String>) {
    Text(
        text = title.value,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        textDecoration = TextDecoration.Underline,
        fontSize = 26.sp
    )
}

@Composable
private fun DailyContent(
    modifier: Modifier = Modifier,
    title: String = "",
    content: String = "",
    imageID: Int = 0,
    isLastPage: Boolean = false,
    onNextPage: () -> Unit,
    onContentRead: () -> Unit
) {
    val cardScroll = rememberScrollState()
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            if (title.isNotBlank())
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            Column(
                modifier = Modifier
                    .verticalScroll(cardScroll)
                    .weight(1f)
            ) {
                if (imageID != 0) {
                    Image(
                        painter = painterResource(id = imageID),
                        contentDescription = imageID.toString(),
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.FillWidth,
                    )
                }
                if (content.isNotBlank()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = content)
                }
            }
            if (isLastPage) {
                Button(
                    onClick = onContentRead,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = DayCompleted,
                        contentColor = MaterialTheme.colors.onPrimary
                    ),
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text(stringResource(R.string.calendar_info_completed))
                }
            } else {
                Button(
                    onClick = onNextPage,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.secondary,
                        contentColor = MaterialTheme.colors.onSecondary
                    ),
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text(stringResource(R.string.calendar_info_next_page))
                }
            }
        }
    }
}
