package com.stori.challenge.ui.views.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.stori.challenge.R
import com.stori.challenge.ui.components.DottedDivider
import com.stori.challenge.ui.navigation.TXN
import com.stori.challenge.ui.theme.LocalDim
import com.stori.challenge.ui.viewmodels.MainViewModel

@Composable
fun DetailsScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel,
) {
    val txn: String = remember(navController.currentBackStackEntry) {
        navController.currentBackStackEntry?.arguments?.getString(TXN).orEmpty()
    }
    val dimensions = LocalDim.current
    val colors = MaterialTheme.colorScheme
    val userProfileState = mainViewModel.userProfileState.value
    val userTransactionData = userProfileState.userTransactionData
    val transaction by remember {
        mutableStateOf(
            userTransactionData
                ?.transactions
                ?.firstOrNull { t -> t.transactionNumber.toString() == txn },
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensions.paddingLarge),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimensions.spaceMedium),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = dimensions.paddingLarge),
            horizontalArrangement = Arrangement.Start,
        ) {
            Text(
                text = "Transaction details",
                maxLines = 1,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.5.sp,
                    color = colors.outline,
                ),
            )
        }

        DottedDivider()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = dimensions.paddingSmall),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            transaction?.let { t ->
                Text(
                    text = stringResource(
                        id = when (t.transactionType) {
                            1 -> R.string.type_transaction_sent
                            else -> R.string.type_transaction_received
                        },
                    ),
                    maxLines = 1,
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold,
                        color = colors.outline,
                    ),
                )

                Text(
                    text = "$ ${t.transactionAmount}",
                    maxLines = 1,
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = colors.outline,
                        fontSize = 20.sp,
                        letterSpacing = 1.0.sp,
                    ),
                )
            }
        }

        DottedDivider()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = dimensions.paddingSmall),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            transaction?.let { t ->
                Text(
                    text = stringResource(R.string.transaction_number),
                    maxLines = 1,
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = colors.outline,
                    ),
                )

                Text(
                    text = t.transactionNumber.toString().padStart(6, '0'),
                    maxLines = 1,
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = colors.outline,
                        fontSize = 17.sp,
                        letterSpacing = 1.0.sp,
                    ),
                )
            }
        }

        DottedDivider()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = dimensions.paddingSmall),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            transaction?.let { t ->
                Text(
                    text = stringResource(R.string.transaction_detail),
                    maxLines = 1,
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = colors.outline,
                    ),
                )

                Text(
                    text = t.transactionDetail,
                    maxLines = 1,
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = colors.outline,
                        fontSize = 16.sp,
                        letterSpacing = 1.0.sp,
                    ),
                )
            }
        }

        DottedDivider()
    }
}
