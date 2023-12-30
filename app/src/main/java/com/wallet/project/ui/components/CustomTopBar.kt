package com.wallet.project.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.wallet.project.R
import com.wallet.project.ui.navigation.Screen
import com.wallet.project.ui.navigation.Screen.Companion.hasActionIcon
import com.wallet.project.ui.theme.LocalDim

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(
    currentScreen: Screen,
    scrollBehavior: TopAppBarScrollBehavior,
    onActionClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    MediumTopAppBar(
        title = {
            Text(
                modifier = Modifier.padding(horizontal = LocalDim.current.paddingSmall),
                text = currentScreen.resTitle?.let { title -> stringResource(id = title) }
                    .orEmpty(),
                maxLines = 1,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 30.sp,
                ),
            )
        },
        navigationIcon = {
            AnimatedVisibility(visible = currentScreen.showBack) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = "back",
                        tint = MaterialTheme.colorScheme.inversePrimary,
                    )
                }
            }
        },
        actions = {
            AnimatedVisibility(visible = currentScreen.hasActionIcon) {
                IconButton(onClick = onActionClick) {
                    currentScreen.actionIcon?.let { actionIcon ->
                        Icon(
                            painter = painterResource(id = actionIcon),
                            contentDescription = "actionIcon",
                        )
                    }
                }
            }
        },
        scrollBehavior = scrollBehavior,
    )
}
