package com.wallet.project.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.wallet.project.R
import com.wallet.project.ui.theme.LocalDim
import com.wallet.project.ui.theme.WalletTheme

/**
 * A card component that displays an account balance with a currency flag.
 *
 * @param modifier Modifier to be applied to the card.
 * @param title The title of the card, usually displayed as "Account Balance".
 * @param currencyFlag A drawable resource identifier for the currency flag image.
 * @param accountBalance A composable lambda that provides the content displaying the account balance.
 *
 * @author Lorenzo Suarez
 * @sample PreviewAccountBalanceCard
 */
@Composable
fun AccountBalanceCard(
    modifier: Modifier = Modifier,
    title: String,
    @DrawableRes currencyFlag: Int,
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
                style = MaterialTheme.typography.bodyMedium,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimens.spaceSmall),
            ) {
                Image(
                    painter = painterResource(id = currencyFlag),
                    contentDescription = "currencyFlag",
                )
                accountBalance()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAccountBalanceCard() {
    WalletTheme {
        AccountBalanceCard(
            title = "Account Balance",
            currencyFlag = R.drawable.ic_usa_flag,
            accountBalance = {
                Text(text = "$1,234.56 USD")
            },
        )
    }
}
