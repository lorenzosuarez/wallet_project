package com.stori.challenge.ui.views.home

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.stori.challenge.R
import com.stori.challenge.ui.components.AccountBalanceCard
import com.stori.challenge.ui.components.LogoutAlertDialog
import com.stori.challenge.ui.components.ShimmerContent
import com.stori.challenge.ui.components.TransactionItem
import com.stori.challenge.ui.events.MainEvent
import com.stori.challenge.ui.navigation.Screen
import com.stori.challenge.ui.navigation.TXN
import com.stori.challenge.ui.navigation.UID
import com.stori.challenge.ui.theme.LocalDim
import com.stori.challenge.ui.viewmodels.MainViewModel
import com.stori.challenge.util.extensions.debounceClick
import com.stori.challenge.util.extensions.safeNavigate
import com.stori.challenge.util.extensions.toTwoDecimals

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
    val pullRefreshState =
        rememberPullRefreshState(isLoading, { mainViewModel.loadUserProfile(uid = uid) })

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
        LogoutAlertDialog(
            onDismissRequest = dismissLogoutAlertDialog,
            onConfirmation = mainViewModel::signOut,
            dialogTitle = "Log out",
            dialogText = "You will be returned to the login screen",
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
                    title = "Account balance",
                    currencyFlag = R.drawable.ic_usa_flag,
                    accountBalance = {
                        ShimmerContent(
                            showShimmer = isLoading,
                            shape = MaterialTheme.shapes.small,
                        ) {
                            Text(
                                modifier = Modifier.alpha(if (isLoading) 0f else 1f),
                                text = "${userTransactionData?.amount?.toTwoDecimals() ?: 00.00} USD",
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
                    text = "Transactions",
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
                                    transactionType = stringResource(
                                        id = when (transactionType) {
                                            1 -> R.string.type_transaction_sent_short
                                            else -> R.string.type_transaction_received_short
                                        },
                                    ).uppercase(),
                                    transactionAmount = transactionAmount,
                                    transactionTypeIcon = if (transactionNumber == 1) R.drawable.ic_txn_out else R.drawable.ic_txn_in,
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
            }
        }

        PullRefreshIndicator(
            refreshing = isLoading,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
        )
    }
}
