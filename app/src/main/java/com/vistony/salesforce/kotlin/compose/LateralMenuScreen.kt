package com.vistony.salesforce.kotlin.compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.vistony.salesforce.R
import com.vistony.salesforce.View.NavigationHost
import com.vistony.salesforce.kotlin.compose.theme.VistonySalesForce_PedidosTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MenuLateral: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VistonySalesForce_PedidosTheme {
                Surface(

                ) {
                        MenuPrincipalPreview()
                }
            }
        }
    }

}

@Composable
fun MenuPrincipal(){
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val navigationItems = listOf(
        MenuOptions.Pantalla1,
        MenuOptions.Pantalla2,
        MenuOptions.Pantalla3,
        MenuOptions.Pantalla4,
        MenuOptions.Pantalla5,
        MenuOptions.Pantalla6,
        MenuOptions.Pantalla7,
        MenuOptions.Pantalla8,
        MenuOptions.Pantalla9,
        MenuOptions.Pantalla10,
        MenuOptions.Pantalla11
    )
    Scaffold(
        scaffoldState=scaffoldState,
        topBar = {TopBar(scope,scaffoldState)},
        drawerContent = {Drawer(
            scope,
            scaffoldState,
            navController,
            menu_items = navigationItems

        )}
    ) {
        NavigationHost(navController)

    }

}

@Composable
fun Drawer(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    navController: NavHostController,
    menu_items:List<MenuOptions> ){
    /*val menu_items = listOf(
        "Celda 1",
        "Celda 2",
        "Celda 3",
        "Celda 4",
        "Celda 5",
    )*/
    
    Column(
        modifier = Modifier.background(color = Color.Blue)
    ) {
        Image(painterResource(id = R.mipmap.logo_factura) ,
            contentDescription = "Usuario",
            modifier = Modifier
                .height(160.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.FillWidth,


        )

        Row() {
            Column(
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Código",color = Color.White)
                Text(text = "Usuario",color = Color.White)
                Text(text = "Conexion",color = Color.White)
                Text(text = "Zona",color = Color.White)
            }
            Column(
                modifier = Modifier.padding(20.dp,0.dp,0.dp,0.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Código1",color = Color.White)
                Text(text = "Usuario1",color = Color.White)
                Text(text = "Conexion1",color = Color.White)
                Text(text = "Zona1",color = Color.White)
            }
        }
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(15.dp)
        )
    }
    Column(
    ) {
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(15.dp)
        )

        val currentRoute = currentRoute(navController)

        menu_items.forEach{ item ->
            DrawerItem(item = item,
                selected = currentRoute == item.ruta
            )
            {
                navController.navigate(item.ruta){
                    launchSingleTop = true
                }
                scope.launch {
                    scaffoldState.drawerState.close()
                }
            }
        }
    }
}
@Composable
fun currentRoute(navController: NavHostController):String?{
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

@Composable
fun DrawerItem(item : MenuOptions,
               selected: Boolean,
               onItemClick: (MenuOptions)->Unit

){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(6.dp)
            .clip(RoundedCornerShape(12))
            .background(
                /*if (selected) MaterialTheme.colors.primaryVariant.copy(alpha = 0.25f)
                else Color.Transparent*/
                Color.Transparent
            )
            .padding(8.dp)
            .clickable { onItemClick(item) },
        verticalAlignment = Alignment.CenterVertically

    ) {
        Image(
            painterResource(id = item.icon),
            contentDescription = item.title)
        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = item.title,
            //style = MaterialTheme.typography.body1,
            /*color = if(selected) MaterialTheme.colors.secondaryVariant
                    else MaterialTheme.colors.onBackground*/
            color = Color.Black
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MenuPrincipalPreview(){
    VistonySalesForce_PedidosTheme {
        MenuPrincipal()
    }
}

@Composable
fun TopBar(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState
){
    TopAppBar(
        title = { Text(text = "Menu Lateral") },
        navigationIcon = {
            IconButton(onClick = {
            scope.launch { scaffoldState.drawerState.open()
            }
            }) {

                Icon (imageVector = Icons.Filled.Menu, contentDescription = "Icono de menú")
            }
        }
    )
}