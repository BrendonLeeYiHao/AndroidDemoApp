import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.profileapplication.R
import com.example.profileapplication.di.datastore.DataStoreManager
import com.example.profileapplication.navigation.BottomNavigationItem
import com.example.profileapplication.navigation.NavigateToKey
import com.example.profileapplication.navigation.NavigationItem
import com.example.profileapplication.navigation.Navigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoAppScaffold(
    title: String,
    selectedBottomItemIndex: MutableState<Int>,
    navController: NavController?,
    drawerState: DrawerState,
    scope: CoroutineScope,
    content: @Composable (PaddingValues) -> Unit
) {
    val items = enumValues<NavigationItem>().toList()
    val bottomItems = enumValues<BottomNavigationItem>().toList()
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    val context = LocalContext.current
    val dataStoreManager = remember { DataStoreManager(context) }
    var showLanguageDialog by remember { mutableStateOf(false) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(16.dp))
                Text(stringResource(id = R.string.demoApp), modifier = Modifier.padding(16.dp))
                Divider(modifier = Modifier.padding(bottom = 8.dp))
                items.forEachIndexed { index, item ->
                    NavigationDrawerItem(
                        label = { Text(text = stringResource(id = item.title)) },
                        selected = index == selectedItemIndex,
                        onClick = {
                            selectedItemIndex = index
                            if (item.title == R.string.language) {
                                showLanguageDialog = true
                            }
//                            scope.launch {
//                                drawerState.close()
//                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (index == selectedItemIndex) {
                                    item.selectedIcon
                                } else item.unselectedIcon,
                                contentDescription = stringResource(id = item.title)
                            )
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = title) },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            scope.launch {
                                dataStoreManager.clearUsername()
                                Navigator.performNavigation(context, navController, NavigateToKey.App.LoginActivity)
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Filled.AccountCircle,
                                contentDescription = stringResource(id = R.string.account),
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }
                )
            },
            bottomBar = {
                NavigationBar {
                    bottomItems.forEachIndexed { index, bottomNavigationItem ->
                        NavigationBarItem(
                            label = {
                                Text(text = stringResource(id = bottomNavigationItem.title))
                            },
                            selected = selectedBottomItemIndex.value == index,
                            onClick = {
                                selectedBottomItemIndex.value = index
                                Navigator.performNavigation(null, navController, bottomNavigationItem.navigateToKey)
                            },
                            icon = {
                                Icon(
                                    imageVector = if (index == selectedBottomItemIndex.value) {
                                        bottomNavigationItem.selectedIcon
                                    } else bottomNavigationItem.unselectedIcon,
                                    contentDescription = stringResource(id = bottomNavigationItem.title)
                                )
                            }
                        )
                    }
                }
            },
            content = content
        )
    }

    if (showLanguageDialog) {
        LanguageSelectionDialog(
            onDismiss = { showLanguageDialog = false }
        )
    }
}

@Composable
fun LanguageSelectionDialog(
    onDismiss: () -> Unit,
) {
    val languages = listOf(stringResource(id = R.string.english), stringResource(id = R.string.chinese),
        stringResource(id = R.string.malay)
    )
    val selectedIndex = remember { mutableIntStateOf(0) }

    AlertDialog(
        onDismissRequest = { },
        title = { Text(text = stringResource(id = R.string.selectLanguage)) },
        text = {
            Column {
                languages.forEachIndexed { index, language ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        RadioButton(
                            selected = selectedIndex.intValue == index,
                            onClick = { selectedIndex.intValue = index }
                        )
                        Text(
                            text = language,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(stringResource(id = R.string.okay))
            }
        }
    )
}