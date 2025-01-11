package com.saba.ui_home_screen

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalUseFallbackRippleImplementation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.saba.base_android.uiles.hideKeyboard

import com.saba.design_system.actionbar.HomeActionBar
import com.saba.design_system.animations.RippleLoadingAnimation
import com.saba.design_system.bottomsheet.EditTaskBottomSheetView
import com.saba.design_system.dialog.AddDialog
import com.saba.design_system.item.TaskItem
import com.saba.design_system.text.CustomText
import com.saba.design_system.theme.Gray_300
import com.saba.design_system.theme.Gray_400
import com.saba.design_system.theme.Gray_600
import com.saba.design_system.theme.Gray_900
import com.saba.design_system.theme.Primary
import com.saba.design_system.theme.White
import com.saba.presentation.HomeViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController, isDark: Boolean,
    viewModel: HomeViewModel = hiltViewModel(),
    changeThemeClick: () -> Unit
) {
    val search by viewModel.search.collectAsStateWithLifecycle()
    val categories by viewModel.categories.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            skipHiddenState = false
        )
    )
    val coroutineScope = rememberCoroutineScope()
    BackHandler {
        if (scaffoldState.bottomSheetState.currentValue == SheetValue.Expanded){
            coroutineScope.launch {
                scaffoldState.bottomSheetState.hide()
            }
        }
    }
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetDragHandle = {
        },
        sheetContent = {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.72f)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Primary.copy(alpha = 0.8f),
                                Gray_900.copy(alpha = 0f),
                                Gray_900.copy(alpha = 0f),
                                Gray_900.copy(alpha = 0f),
                                Gray_900.copy(alpha = 0.2f),
                                Gray_900.copy(alpha = 0f)
                            )
                        )
                    )
            ) {
                EditTaskBottomSheetView(
                    taskCategory = uiState.taskCategory,
                    categories = categories,
                    selectedCategory = {
                        viewModel.onValueChange(HomeViewModel.VALUE_CAHNGE_TYPE.CATEGORY, it)
                    },
                    titleValue = uiState.bottomSheeTitle,
                    descriptionValue = uiState.bottomSheetDescription,
                    time = uiState.timePicker,
                    date = uiState.datePicker,
                    isDark = isDark,
                    onDateConfirm = {
                        viewModel.onValueChange(HomeViewModel.VALUE_CAHNGE_TYPE.DATE, it)
                    },
                    onTitleChange = {
                        viewModel.onValueChange(
                            HomeViewModel.VALUE_CAHNGE_TYPE.BOTTOM_SHEET_TITLE,
                            it
                        )
                    },
                    onTimeConfirm = {
                        viewModel.onValueChange(HomeViewModel.VALUE_CAHNGE_TYPE.TIME, it)
                    },
                    onDescriptionChange = {
                        viewModel.onValueChange(
                            HomeViewModel.VALUE_CAHNGE_TYPE.BOTTOM_SHEET_DESCRIPTION,
                            it
                        )
                    },
                    updateTask = {
                        coroutineScope.launch {
                            viewModel.updateTask()
                            delay(500)
                            if (uiState.updateComplete) {
                                scaffoldState.bottomSheetState.hide()
                            }
                        }

                    }
                )
            }

        },
        sheetPeekHeight = 0.dp
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp),
            containerColor = MaterialTheme.colorScheme.background,
            topBar = {
                HomeActionBar(
                    value = search,
                    onValueChange = {
                        viewModel.searchQuery(it)
                    },
                    onThemeClick = {
                        changeThemeClick()
                    },
                )
            },
            bottomBar = {
                Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {
                    RippleLoadingAnimation(circlesWave = 1, animationDelay = 2000, onAddClick = {
                        viewModel.showAddTaskDialog(true)
                    })
                }
            }
        ) { innerPadding ->
            AddDialog(
                showDialog = uiState.dialogAddTask,
                dissmissRequired = { viewModel.showAddTaskDialog(false) },
                titleValue = uiState.title,
                isDark = isDark,
                descriptionValue = uiState.description,
                onTitleChange = {
                    viewModel.onValueChange(HomeViewModel.VALUE_CAHNGE_TYPE.TITLE, it)
                },
                onDescriptionChange = {
                    viewModel.onValueChange(HomeViewModel.VALUE_CAHNGE_TYPE.DESCRIPTION, it)
                },
                onCreateClick = {
                    viewModel.addTask()
                }
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = (innerPadding.calculateTopPadding() + 20.dp)),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item("CATEGORY_ITEMS") {
                    LazyRow(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        items(categories) {
                            Box(
                                modifier = modifier
                                    .height(30.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (uiState.activeCategory.equals(it.category)) White else Gray_600)
                                    .border(width = 1.dp, Gray_600, RoundedCornerShape(8.dp))
                                    .clickable {
                                        viewModel.onCategoryClick(it.category)
                                    }
                            ) {
                                CustomText(
                                    text = it.category,
                                    //    textDecoration = TextDecoration.LineThrough,
                                    modifier = modifier
                                        .padding(horizontal = 16.dp)
                                        .align(Alignment.Center),
                                    color = if (uiState.activeCategory.equals(it.category)) Gray_900 else White
                                )
                            }
                        }
                    }
                }
                itemsIndexed(uiState.tasks, key = { index, item ->
                    item.id
                }) { index, item ->
                    TaskItem(item.title, isReminderEnabled = item.isReminderEnabled, onItemClick = {
                        viewModel.fetchTaskById(item.id)
                        coroutineScope.launch {
                            scaffoldState.bottomSheetState.expand()
                        }
                    }, index = index, onRemove = {
                        viewModel.deleteTask(item)
                    }, onCheckdClick = {
                        viewModel.updateReminder(item.id, it)
                    })
                }
            }
        }
    }
}