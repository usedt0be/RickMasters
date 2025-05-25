package com.example.rickmasters.presentation.screens.statistics.top_visitors

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.rickmasters.R
import com.example.rickmasters.domain.models.User
import com.example.rickmasters.theme.AppTheme
import timber.log.Timber


@Composable
fun VisitorItem(
    visitor: User,
    modifier: Modifier = Modifier
) {
    val painter = rememberAsyncImagePainter(
        model = visitor.files.first().url
    )

    Row(
        modifier = modifier
            .heightIn(min = 62.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier.align(Alignment.CenterVertically)
        ){
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(38.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.FillBounds
            )

            if (visitor.isOnline) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_online_status_point),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.BottomEnd),
                    tint = Color.Unspecified
                )
            }
        }

        Text(
            text = visitor.username,
            modifier = Modifier,
            style = AppTheme.typography.bodyRegular,
            color = AppTheme.colors.textPrimary
        )

        Spacer(
            modifier = Modifier.weight(1f),
        )

        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_forward),
            contentDescription = null,
            modifier = Modifier.padding(
                end = 16.dp
            ),
            tint = Color.Unspecified
        )
    }
}



@Preview
@Composable
fun TopVisitorItemPreview(){
    VisitorItem(
        visitor = User(
            id = 1,
            sex = "M",
            username = "Legenda",
            isOnline = true,
            age = 88,
            files = emptyList()
        )
    )
}