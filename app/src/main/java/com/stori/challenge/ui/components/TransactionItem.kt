package com.stori.challenge.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stori.challenge.R
import com.stori.challenge.ui.theme.LocalDim
import com.stori.challenge.ui.theme.StoriTheme

/**
 * A composable that displays a transaction item.
 *
 * @param modifier Modifier to be applied to the composable.
 * @param transactionType The title of the transaction.
 * @param transactionAmount The amount of the transaction.
 * @param transactionTypeIcon The icon resource ID representing the type of transaction.
 * @param onClick The action to perform when the item is clicked.
 *
 * @sample TransactionItemPreview
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionItem(
    modifier: Modifier = Modifier,
    transactionType: String?,
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

                Spacer(
                    modifier = Modifier
                        .width(0.6.dp)
                        .height(28.dp)
                        .background(MaterialTheme.colorScheme.outline),
                )

                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(dimens.spaceXSmall),
                ) {
                    transactionType?.let { type ->
                        Text(
                            text = type,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Normal,
                                fontSize = 10.sp,
                                letterSpacing = 2.sp,
                            ),
                        )
                    }
                    Text(
                        text = transactionAmount,
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

@Preview(showBackground = true, name = "Transaction Item Preview")
@Composable
fun TransactionItemPreview() {
    StoriTheme {
        TransactionItem(
            transactionType = "Purchase",
            transactionAmount = "100.00",
            transactionTypeIcon = R.drawable.ic_txn_in,
            onClick = {},
        )
    }
}
