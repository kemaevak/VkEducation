package io.mmaltsev.vkeducation.presentation.appdetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import io.mmaltsev.vkeducation.presentation.theme.VkEducationTheme

@Composable
internal fun Toolbar(
    onBackClick: () -> Unit,
    onWishlistClick: () -> Unit,
    onShareClick: () -> Unit,
    isInWishlist: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
            )
        }
        Row {
            IconButton(onClick = onWishlistClick) {
                Icon(
                    imageVector = if (isInWishlist) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    tint = if (isInWishlist) Color.Red else MaterialTheme.colorScheme.primary,
                )
            }
            IconButton(onClick = onShareClick) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    VkEducationTheme {
        Toolbar(
            onBackClick = {},
            onWishlistClick = {},
            onShareClick = {},
            isInWishlist = false,
        )
    }
}
