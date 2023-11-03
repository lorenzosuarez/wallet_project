package com.stori.challenge.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.stori.challenge.ui.theme.LocalDim

@Composable
fun AccountBalanceCard(
    modifier: Modifier = Modifier,
    title: String,
    accountBalance: @Composable () -> Unit? = {},
) {
    val dimens = LocalDim.current
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(dimens.paddingMedium),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(dimens.paddingSmall),
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
            )
            accountBalance()
        }
    }
}
