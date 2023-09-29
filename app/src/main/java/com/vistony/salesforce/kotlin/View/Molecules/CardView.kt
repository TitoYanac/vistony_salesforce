package com.vistony.salesforce.kotlin.View.Molecules

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vistony.salesforce.R

@Composable
fun MessageCard(){
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(painter = painterResource(id = R.drawable.end_green)
            , contentDescription = "Contact Profile Picture"
            , modifier = Modifier
                //Set Image size to 40 dp
                .size(40.dp)
                //Clip Image to be shaped as a circle
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))

        var isExpanded by remember { mutableStateOf(false) }
        val surfaceColor by animateColorAsState(
            if(isExpanded)
                MaterialTheme.colors.primary
            else MaterialTheme.colors.surface,  )

        Column(modifier = Modifier.clickable {
            isExpanded = !isExpanded
        }
        ) {
            Text(text = "Prueba"
                , color = MaterialTheme.colors.secondaryVariant
                , style = MaterialTheme.typography.subtitle2)
            Spacer(modifier = Modifier.height(4.dp))
            Surface(
                shape = MaterialTheme.shapes.medium,
                elevation = 1.dp,
                color = surfaceColor,
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)
            ) {
                Text(text = "Prueba",
                    modifier = Modifier.padding(all = 4.dp),
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.body2)
            }

        }
    }
}


@Composable
fun ExpandableCard(
    title:String,
    listelementsinvoice: List<Pair<String, String>>
) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .clickable { expanded = !expanded },
        //.padding(20.dp),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(10.dp,0.dp,10.dp,0.dp)) {
            Row() {
                Text(title,modifier = Modifier.padding(0.dp,10.dp,10.dp,10.dp),color = Color.Black,
                    style = MaterialTheme.typography.subtitle2,
                    fontWeight = FontWeight.Bold)
                Icon(
                    imageVector =
                    if(expanded){
                        ImageVector.vectorResource(R.drawable.ic_baseline_arrow_drop_up_24_black)
                    }else{
                        ImageVector.vectorResource(R.drawable.ic_baseline_arrow_drop_down_24_black)
                    }
                    ,
                    contentDescription = null,
                    modifier = Modifier.padding(0.dp,10.dp,0.dp,0.dp)
                    //tint = Color.White,

                )

            }

            AnimatedVisibility(
                visible = expanded,
                enter = expandIn(),
                exit = shrinkOut()
            ) {
                Column()
                {
                    listelementsinvoice.forEachIndexed { index, step ->
                        Row {
                            Box(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = step.first,
                                    //color = MaterialTheme.colors.secondaryVariant,
                                    color = Color.Gray,
                                    fontSize = 13.sp
                                    ,
                                    style = MaterialTheme.typography.body1
                                )
                            }
                            Box(modifier = Modifier.weight(3f)) {
                                Text(
                                    text = step.second,
                                    //color = MaterialTheme.colors.secondaryVariant,
                                    color = Color.Black,
                                    style = MaterialTheme.typography.subtitle2,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CardView(
    cardtTittle: @Composable () -> Unit,
    cardContent: @Composable () -> Unit,
    cardBottom: @Composable () -> Unit
){
    Card(
        modifier = Modifier
        .padding(10.dp),
        elevation = 10.dp
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        )
        {
            cardtTittle()
            cardContent()
            cardBottom()
        }
    }
}

@Composable
fun CardView2(
    cardtTittle: @Composable () -> Unit,
    cardContent: @Composable () -> Unit,
    cardBottom: @Composable () -> Unit
){
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth() ,
        elevation = 10.dp

    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        )
        {
            cardtTittle()
            cardContent()
            cardBottom()
        }
    }
}