package com.wallet.project.ui.views.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.wallet.project.R
import com.wallet.project.ui.components.AccountBalanceCard
import com.wallet.project.ui.components.AlertDialog
import com.wallet.project.ui.components.ShimmerContent
import com.wallet.project.ui.components.TransactionItem
import com.wallet.project.ui.events.MainEvent
import com.wallet.project.ui.navigation.Screen
import com.wallet.project.ui.navigation.TXN
import com.wallet.project.ui.navigation.UID
import com.wallet.project.ui.theme.LocalDim
import com.wallet.project.ui.viewmodels.MainViewModel
import com.wallet.project.util.extensions.debounceClick
import com.wallet.project.util.extensions.safeNavigate
import com.wallet.project.util.extensions.toTwoDecimals

private const val CURRENCY_CODE = "USD"

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel,
) {
    val uid: String = remember(Unit) {
        navController.currentBackStackEntry?.arguments?.getString(UID).orEmpty()
    }
    val dimensions = LocalDim.current
    val eventFlow = mainViewModel.eventFlow
    var openLogoutAlertDialog by remember { mutableStateOf(false) }
    val dismissLogoutAlertDialog: () -> Unit = { openLogoutAlertDialog = false }
    val userProfileState = mainViewModel.userProfileState.value
    val userProfile = userProfileState.userProfile
    val userTransactionData = userProfileState.userTransactionData
    val isLoading = userProfileState.isLoading
    val listState = rememberLazyListState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isLoading,
        onRefresh = { mainViewModel.loadUserProfile(uid = uid) },
    )
    val noResultLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            R.raw.no_results,
        ),
    )
    val noResultLottie by animateLottieCompositionAsState(
        noResultLottieComposition,
        iterations = 1,
        isPlaying = true,
    )
    val emptyTransactions = !isLoading && userTransactionData?.transactions.isNullOrEmpty()

    LaunchedEffect(eventFlow) {
        eventFlow.collect { event ->
            when (event) {
                is MainEvent.ActionEvent -> {
                    openLogoutAlertDialog = !openLogoutAlertDialog
                }
            }
        }
    }

    if (openLogoutAlertDialog) {
        AlertDialog(
            confirmButtonText = stringResource(id = R.string.button_log_out),
            dismissButtonText = stringResource(id = R.string.button_cancel),
            dialogTitle = stringResource(id = R.string.button_log_out),
            dialogText = stringResource(id = R.string.log_out_message_title),
            onConfirmation = mainViewModel::signOut,
            onDismissRequest = dismissLogoutAlertDialog,
        )
    }

    Box(
        Modifier
            .pullRefresh(pullRefreshState),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = dimensions.paddingLarge),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ShimmerContent(
                modifier = Modifier.padding(vertical = dimensions.paddingSmall),
                showShimmer = isLoading || userProfile == null,
                shape = MaterialTheme.shapes.extraSmall,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(vertical = dimensions.paddingSmall),
                    horizontalArrangement = Arrangement.Start,
                ) {
                    if (!isLoading) {
                        Text(
                            modifier = Modifier.alpha(if (userProfile == null) 0f else 1f),
                            text = "Hi, ${userProfile?.name} !",
                            maxLines = 1,
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontSize = 21.sp,
                                fontWeight = FontWeight.ExtraBold,
                            ),
                        )
                    }
                }
            }

            ShimmerContent(
                showShimmer = isLoading,
                shape = MaterialTheme.shapes.small,
            ) {
                AccountBalanceCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .alpha(if (isLoading) 0f else 1f),
                    title = stringResource(id = R.string.account_balance),
                    currencyFlag = R.drawable.ic_usa_flag,
                    accountBalance = {
                        ShimmerContent(
                            showShimmer = isLoading,
                            shape = MaterialTheme.shapes.small,
                        ) {
                            Text(
                                modifier = Modifier.alpha(if (isLoading) 0f else 1f),
                                text = "${userTransactionData?.amount?.toTwoDecimals() ?: 00.00} $CURRENCY_CODE",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 25.sp,
                                    letterSpacing = 1.3.sp,
                                ),
                            )
                        }
                    },
                )
            }

            Box(
                modifier = Modifier.padding(vertical = dimensions.spaceMedium),
                contentAlignment = Alignment.Center,
            ) { Divider() }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(vertical = dimensions.paddingSmall),
                horizontalArrangement = Arrangement.Start,
            ) {
                Text(
                    text = stringResource(id = R.string.transactions),
                    style = MaterialTheme.typography.labelLarge,
                )
            }

            if (isLoading) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(dimensions.paddingMedium),
                    contentPadding = PaddingValues(vertical = dimensions.paddingMedium),
                ) {
                    repeat(3) {
                        item {
                            ShimmerContent(
                                shape = MaterialTheme.shapes.large,
                            ) {
                                Spacer(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(dimensions.spaceExtraLarge),
                                )
                            }
                        }
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(dimensions.paddingMedium),
                    contentPadding = PaddingValues(vertical = dimensions.paddingMedium),
                    state = listState,
                ) {
                    userTransactionData?.transactions?.let { transactions ->
                        items(transactions) { item ->
                            with(item) {
                                TransactionItem(
                                    modifier = Modifier.fillMaxWidth(),
                                    transactionType = when (transactionType) {
                                        1 -> stringResource(id = R.string.type_transaction_sent_short)
                                        0 -> stringResource(id = R.string.type_transaction_received_short)
                                        else -> null
                                    }?.uppercase(),
                                    transactionAmount = "${
                                        when (transactionType) {
                                            0 -> "+"
                                            else -> ""
                                        }
                                    }$transactionAmount $CURRENCY_CODE",
                                    transactionTypeIcon = when (transactionType) {
                                        0 -> R.drawable.ic_txn_in
                                        1 -> R.drawable.ic_txn_out
                                        else -> R.drawable.ic_receipt
                                    },
                                    onClick = {
                                        synchronized(this) {
                                            navController.safeNavigate(
                                                route = Screen.Details.route,
                                                argument = TXN to transactionNumber.toString(),
                                            )
                                        }
                                    }.debounceClick(),
                                )
                            }
                        }
                    }
                }
                if (emptyTransactions) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxWidth(0.8f),
                        contentAlignment = Alignment.Center,
                    ) {
                        LottieAnimation(
                            modifier = Modifier.fillMaxSize(0.65f),
                            composition = noResultLottieComposition,
                            progress = noResultLottie,
                            contentScale = ContentScale.Fit,
                        )
                    }
                }
            }
        }

        PullRefreshIndicator(
            refreshing = isLoading,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
        )
    }
}
