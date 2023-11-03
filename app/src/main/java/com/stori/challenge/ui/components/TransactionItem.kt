package com.stori.challenge.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.stori.challenge.R
import com.stori.challenge.ui.theme.LocalDim

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionItem(
    modifier: Modifier = Modifier,
    transactionTitle: String,
    transactionAmount: String,
    @DrawableRes transactionTypeIcon: Int,
    onClick: () -> Unit,
) {
    val dimens = LocalDim.current

    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimens.paddingMedium),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                modifier = Modifier.heightIn(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimens.spaceMedium),
            ) {
                Icon(
                    painter = painterResource(transactionTypeIcon),
                    contentDescription = "transactionTypeIcon",
                )
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(dimens.spaceXXSmall),
                ) {
                    Text(
                        text = transactionTitle,
                        style = MaterialTheme.typography.labelMedium,
                    )
                    Text(
                        text = "$$transactionAmount",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = 1.0.sp,
                        ),
                    )
                }
            }

            IconButton(onClick = onClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_chevron_right),
                    contentDescription = "ic_chevron_right",
                )
            }
        }
    }
}
