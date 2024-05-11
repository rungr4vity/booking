import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable

import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.firebasenotes.R
import com.example.firebasenotes.ViewMenu.AltCajon

import com.example.firebasenotes.ViewMenu.MiPerfil
import com.example.firebasenotes.ViewMenu.MiReservas
import com.example.firebasenotes.ViewMenu.Reservar
import com.example.firebasenotes.WidgetsCardView.Listing.ListAreas.AltaArea
import com.example.firebasenotes.WidgetsCardView.Listing.ListAreas.AreaScreen
import com.example.firebasenotes.WidgetsCardView.Listing.ListAreas.DetalleAlta
import com.example.firebasenotes.WidgetsCardView.Listing.ListingDrawer.Detalle
import com.example.firebasenotes.WidgetsCardView.Listing.ListingDrawer.DrawerScreen


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Preview
@Composable
fun App() {
    // Controlador de navegación
    val navController = rememberNavController()
    var isAdmin : Int = 2

    // Lista de secciones del Navigation Drawer
    val drawerItems = listOf(
        Triple("Home", Icons.Default.Person, 2),
        Triple("Reservar cajon", Icons.Default.AddCircle, 2),
        Triple("Mis reservas", Icons.Default.DateRange, 2),



//        Triple("Alta de Cajon", Icons.Default.Add,2),
//        Triple("Cat Areas", Icons.Default.Menu,2),
//        Triple("Alta de area", Icons.Default.Add,2),
        



//        Pair("Cat Cajones", Icons.Default.Info)
    )
        .filter { it.third == isAdmin }
    val scaffoldState = rememberScaffoldState()
    // Crear el Navigation Drawer
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "PERFIL ADMIN") },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }

                }
            )
        },
        drawerContent = {
            // Contenido del Navigation Drawer
            DrawerContent(drawerItems, navController)
        },
        content = {
            NavHost(
                navController = navController as NavHostController,
                startDestination = "Home"
            ) {

                composable("Home") {
                    MiPerfil()
                }
                composable("Reservar cajon") {
                    DrawerScreen(navController = navController)
                }
                composable("Mis reservas") {
                    MiReservas()
                }


                if (isAdmin==2) {
                composable("Alta de Cajon") {
                    AltCajon()
                }
                }
                composable("DetalleCajon") {
                    Detalle()
                }

                composable("Cat Areas") {
                  AreaScreen(navController = navController)
                }
                composable("Alta de area") {
                    AltaArea(navController = navController)
                }

                composable("DetalleAlta") {
                    DetalleAlta()
                }
            }
        }
    )
}

@Composable
fun DrawerContent(drawerItems: List<Triple<String, ImageVector, Int>>, navController: NavHostController) {
    Column(modifier = Modifier
        .padding(6.dp)
        .fillMaxSize()) {
        Spacer(modifier = Modifier.height(2.dp))

        Image(
            painter = painterResource(R.drawable.img), // Reemplaza R.drawable.logo_image con el ID de tu imagen
            contentDescription = "",
            modifier = Modifier
                .padding(6.dp)
                .align(Alignment.CenterHorizontally)
                .size(150.dp)
                .clip(
                    CircleShape
                )
        )
Text(text = "ADMIN", fontSize = 25.sp)
        Text(text = "admin@ut.com", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(54.dp))
        // Mostrar los elementos del Navigation Drawer con iconos
        drawerItems.forEach { (title, icon) ->
            DrawerItem(title = title, icon = icon) {
                // Navegar a la pantalla correspondiente al seleccionar un elemento del Drawer
                navController.navigate(title)
            }
            Spacer(modifier = Modifier.height(36.dp))
        }
    }
}

@Composable
fun DrawerItem(title: String, icon: ImageVector, onClick: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable(onClick = onClick)) {
        Icon(imageVector = icon, contentDescription = null)
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
        )
    }
}

@Composable
fun MainContent(selectedSection: String) {
    Column(modifier = Modifier.padding(16.dp)) {

        Text(text = "Sección seleccionada: $selectedSection", style = MaterialTheme.typography.labelMedium)
    }
}