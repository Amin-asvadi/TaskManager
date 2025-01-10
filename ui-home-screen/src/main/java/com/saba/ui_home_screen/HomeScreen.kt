package com.saba.ui_home_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalUseFallbackRippleImplementation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.saba.design_system.theme.Gray_600
import com.saba.design_system.theme.Gray_900
import com.saba.design_system.theme.White
import com.saba.presentation.HomeViewModel

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
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    if (uiState.bottomSheetView) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                viewModel.bottomSheetState(false)
            },
            modifier = Modifier
                .fillMaxWidth(),
            containerColor = Color.White,
            contentColor = Color.Black,
            dragHandle = {
                BottomSheetDefaults.DragHandle()
            }

        ) {
            EditTaskBottomSheetView(
                item = uiState.singleTask,
                titleValue = uiState.bottomSheeTitle,
                descriptionValue = uiState.bottomSheetDescription,
                onTitleChange = {
                    viewModel.onValueChange(HomeViewModel.VALUE_CAHNGE_TYPE.BOTTOM_SHEET_TITLE, it)
                },
                onDescriptionChange = {
                    viewModel.onValueChange(
                        HomeViewModel.VALUE_CAHNGE_TYPE.BOTTOM_SHEET_DESCRIPTION,
                        it
                    )
                }
            )
        }
    }
    Scaffold(
        modifier = Modifier
            .hideKeyboard()
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
                RippleLoadingAnimation(circlesWave = 1, animationDelay = 3000, onAddClick = {
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
                TaskItem(item.title, onItemClick = {
                    viewModel.fetchTaskById(item.id)
                    uiState.singleTask?.let {
                        viewModel.bottomSheetState(true)
                    }
                }, index = index, onRemove = {

                })
            }
        }
    }
}