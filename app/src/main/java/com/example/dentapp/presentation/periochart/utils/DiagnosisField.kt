package com.example.dentapp.presentation.periochart.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dentapp.R
import com.example.dentapp.ui.theme.DarkBlue
import com.example.dentapp.ui.theme.Grey
import com.example.dentapp.ui.theme.LightBlue
import com.example.dentapp.ui.theme.Montserrat
import com.example.dentapp.ui.theme.White

@Composable
fun DiagnosisField(
    text: String,
    diagnosis: List<String>,
    onDeleteDiagnose: (String) -> Unit,
    onAddDiagnosis: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }
    val allDiseases = remember { getAllDiseases() }

    val filteredDiseases = remember(searchQuery) {
        if (searchQuery.isEmpty()) {
            emptyList()
        } else {
            allDiseases.filter {
                it.contains(searchQuery, ignoreCase = true) &&
                        it !in diagnosis
            }
        }
    }

    val exactMatchExists = remember(searchQuery, allDiseases) {
        searchQuery.isNotEmpty() &&
                allDiseases.any { it.equals(searchQuery, ignoreCase = true) }
    }

    val canAddNewDisease = remember(searchQuery, diagnosis, exactMatchExists) {
        searchQuery.trim().isNotEmpty() &&
                !exactMatchExists &&
                searchQuery.trim() !in diagnosis
    }

    Column {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .border(
                    width = 1.dp,
                    color = Grey,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text,
                        color = DarkBlue,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.width(24.dp))

                    if (diagnosis.isNotEmpty()) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(5.dp),
                            modifier = Modifier.wrapContentWidth()
                        ) {
                            diagnosis.forEach {
                                DiagnosisChip(
                                    text = it,
                                    onDelete = { onDeleteDiagnose(it) }
                                )
                            }
                        }
                    }

                    if (!isSearchActive) {
                        IconButton(
                            onClick = { isSearchActive = true },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.plus_icon),
                                contentDescription = "Добавить диагноз",
                                tint = DarkBlue
                            )
                        }
                    }
                }

                if (isSearchActive) {
                    SearchField(
                        query = searchQuery,
                        onQueryChange = { searchQuery = it },
                        onClose = {
                            isSearchActive = false
                            searchQuery = ""
                        },
                        onAddCustom = {
                            if (canAddNewDisease) {
                                onAddDiagnosis(searchQuery.trim())
                                searchQuery = ""
                                isSearchActive = false
                            }
                        },
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }

        if (isSearchActive && (filteredDiseases.isNotEmpty() || canAddNewDisease)) {
            DiseasesDropdownList(
                diseases = filteredDiseases,
                customDisease = if (canAddNewDisease) searchQuery.trim() else null,
                onDiseaseClick = { disease ->
                    onAddDiagnosis(disease)
                    searchQuery = ""
                    isSearchActive = false
                },
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
fun SearchField(
    query: String,
    onQueryChange: (String) -> Unit,
    onClose: () -> Unit,
    onAddCustom: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        TextField(
            value = query,
            onValueChange = onQueryChange,
            placeholder = {
                Text(
                    "Поиск болезни...",
                    color = Grey,
                    fontSize = 14.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Normal,
                )
            },
            modifier = Modifier.weight(1f),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = DarkBlue,
                unfocusedIndicatorColor = Color.Transparent
            ),
            singleLine = true,
            keyboardActions = KeyboardActions(
                onDone = {
                    if (query.isNotEmpty()) {
                        onAddCustom()
                    }
                }
            )
        )

        IconButton(
            onClick = onClose,
            modifier = Modifier.size(32.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.cancel_icon),
                contentDescription = "Закрыть",
                tint = Grey
            )
        }
    }
}

@Composable
fun DiagnosisChip(
    text: String,
    onDelete: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(LightBlue)
            .padding(5.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text,
                color = DarkBlue,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
            )

            Icon(
                painter = painterResource(id = R.drawable.cancel_icon),
                contentDescription = null,
                tint = DarkBlue,
                modifier = Modifier
                    .clickable(onClick = onDelete)
                    .padding(4.dp)
            )
        }
    }
}

@Composable
fun DiseasesDropdownList(
    diseases: List<String>,
    customDisease: String?,
    onDiseaseClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = White,
        shadowElevation = 4.dp,
        modifier = modifier.fillMaxWidth()
    ) {
        LazyColumn(
            modifier = Modifier
                .heightIn(max = 200.dp)
                .padding(vertical = 4.dp)
        ) {
            items(diseases) { disease ->
                DropdownItem(
                    text = disease,
                    isExisting = true,
                    onClick = { onDiseaseClick(disease) }
                )

                if (disease != diseases.last() || customDisease != null) {
                    Divider(
                        color = Grey.copy(alpha = 0.3f),
                        thickness = 0.5.dp
                    )
                }
            }

            if (customDisease != null) {
                item {
                    if (diseases.isNotEmpty()) {
                        Divider(
                            color = DarkBlue,
                            thickness = 1.dp
                        )
                    }

                    DropdownItem(
                        text = customDisease,
                        isExisting = false,
                        onClick = { onDiseaseClick(customDisease) }
                    )
                }
            }
        }
    }
}

@Composable
fun DropdownItem(
    text: String,
    isExisting: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            color = if (isExisting) DarkBlue else DarkBlue.copy(alpha = 0.8f),
            fontFamily = Montserrat,
            fontSize = 14.sp,
            modifier = Modifier.weight(1f)
        )

        if (isExisting) {
            Icon(
                painter = painterResource(id = R.drawable.plus_icon),
                contentDescription = "Добавить",
                tint = DarkBlue,
                modifier = Modifier.size(16.dp)
            )
        } else {
            // Показываем, что это новая болезнь
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Добавить новую",
                    color = DarkBlue,
                    fontSize = 12.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Medium
                )
                Icon(
                    painter = painterResource(id = R.drawable.plus_icon),
                    contentDescription = "Добавить новую",
                    tint = DarkBlue,
                    modifier = Modifier.size(14.dp)
                )
            }
        }
    }
}

fun getAllDiseases(): List<String> {
    return listOf(
        "Кариес",
        "Пульпит",
        "Периодонтит",
        "Гингивит",
        "Стоматит",
        "Пародонтоз",
        "Пародонтит",
        "Флюороз",
        "Эрозия эмали",
        "Киста зуба",
        "Абсцесс",
        "Альвеолит"
    )
}