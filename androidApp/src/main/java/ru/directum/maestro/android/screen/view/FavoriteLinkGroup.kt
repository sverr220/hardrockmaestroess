package ru.directum.maestro.android.screen.view

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class FavoriteLinkGroup {

    private sealed class FavoriteLink(val text: String, val url: String) {
        data object OtpgClient : FavoriteLink(
            "otpgclient",
            "https://irnomad.directum.ru/otpgclient/#/"
        )
        data object GoogleDocs : FavoriteLink(
            "GoogleDocs",
            "https://drive.google.com/drive/u/0/folders/1J3xTmCkyUKgsHsWgGJ9ZDiO5kBlZf7rR"
        )

        data object DotNet :
            FavoriteLink("DotNet", "https://dotnet.microsoft.com/en-us/download/dotnet/scripts")

        data object Yandex : FavoriteLink(
            "Yandex",
            "https://ya.ru/images/search?lr=44&redircnt=1712224821.1&source=tabbar&text=hr%20pro"
        )

        data object HRPro : FavoriteLink("HRPro", "https://lk.directum.ru/")
        data object Directum : FavoriteLink("Directum", "https://irnomad.directum.ru/rxpg/web/#/")
    }

    companion object {
        @Composable
        fun FavoriteRows(connectToUrl: (url: String) -> Unit) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .wrapContentSize(Alignment.Center)
                    .verticalScroll(rememberScrollState())
            ) {
                FavoriteRow(FavoriteLink.OtpgClient, connectToUrl)
                FavoriteRow(FavoriteLink.GoogleDocs, connectToUrl)
                FavoriteRow(FavoriteLink.DotNet, connectToUrl)
                FavoriteRow(FavoriteLink.Yandex, connectToUrl)
                FavoriteRow(FavoriteLink.HRPro, connectToUrl)
                FavoriteRow(FavoriteLink.Directum, connectToUrl)
            }
        }

        @Composable
        private fun FavoriteRow(info: FavoriteLink, connectToUrl: (url: String) -> Unit) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(align = Alignment.Top)
                .padding(10.dp)
                .clickable { connectToUrl(info.url) }) {
                Text(
                    text = info.text,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    Icons.Default.PlayArrow,
                    contentDescription = "next",
                )
            }
        }
    }
}