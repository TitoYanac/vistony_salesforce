package com.vistony.salesforce.kotlin.compose.components

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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vistony.salesforce.R


@Composable
fun PreviewSpinner(
    list: List<String>
    ,currentSelected: MutableState<String>)
{
            Spinner(
                items = list,
                selectedItem = currentSelected.value,
                onItemSelected = { item ->
                    currentSelected.value = item
                }
            )
}

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
                    .padding(10.dp)) {
                    Text(
                        text = selectedItem,
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .weight(9f)
                            //.padding(10.dp, 0.dp, 0.dp, 0.dp)
                    )
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
                        ) {
                            Box(modifier = Modifier
                                .padding(10.dp)
                            ) {
                                Text(
                                    text = item,
                                    style = MaterialTheme.typography.body1
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

