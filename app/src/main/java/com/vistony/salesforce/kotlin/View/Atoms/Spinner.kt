package com.vistony.salesforce.kotlin.View.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.vistony.salesforce.R
import com.vistony.salesforce.kotlin.View.Atoms.TableCell
import com.vistony.salesforce.kotlin.View.Atoms.TableCellBody
import com.vistony.salesforce.kotlin.View.Atoms.TextLabel
import com.vistony.salesforce.kotlin.View.Atoms.theme.Typography
import java.lang.reflect.Type

@Composable
fun Spinner(
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.selectableGroup()
    ) {
        Surface(
            shape = RoundedCornerShape(4.dp),
            elevation = 2.dp,
            modifier = Modifier
                .selectable(
                    selected = expanded,
                    onClick = { expanded = !expanded }
                )
                .fillMaxWidth()
        ) {
                Row(modifier = Modifier
                    .padding(10.dp)
                ) {
                    TableCell(text = selectedItem, weight = 9f,title=true)
                    Icon(
                        imageVector =
                        if (expanded) {
                            ImageVector.vectorResource(R.drawable.ic_baseline_arrow_drop_up_24_black)
                        } else {
                            ImageVector.vectorResource(R.drawable.ic_baseline_arrow_drop_down_24_black)
                        },
                        contentDescription = null,
                        modifier = Modifier
                            .weight(1f)
                    )
                }
        }

        if (expanded) {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items.forEach { item ->
                    item {
                        Surface(
                            elevation = 2.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    expanded = false
                                    onItemSelected(item)
                                }
                                //.padding(0.5.dp)
                        ) {
                            Box(modifier = Modifier
                               .padding(10.dp)
                            ) {
                                TableCellBody(text = item)
                            }
                        }
                    }
                }
            }
        }
    }
}

