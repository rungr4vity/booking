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
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.firebasenotes.Viaticos.FacturaAdd.DDViaticos
import com.example.firebasenotes.R
import com.example.firebasenotes.UsersAdmin.detalleUser

import com.example.firebasenotes.UsersAdmin.listUsers
import com.example.firebasenotes.Viaticos.DataViaticos
import com.example.firebasenotes.Viaticos.ViaticosScreen
import com.example.firebasenotes.ViewMenu.AltCajon
import com.example.firebasenotes.ViewMenu.MiPerfil
import com.example.firebasenotes.ViewMenu.MiReservas
import com.example.firebasenotes.ViewMenu.Mipefil.ActualizarPerfil
import com.example.firebasenotes.ViewMenu.Mipefil.PerfilScreen
import com.example.firebasenotes.WidgetsCardView.Listing.ListAreas.AltaArea
import com.example.firebasenotes.WidgetsCardView.Listing.ListAreas.AreaScreen
import com.example.firebasenotes.WidgetsCardView.Listing.ListAreas.DetalleAlta
import com.example.firebasenotes.WidgetsCardView.Listing.ListAreas.ModAreaEliminar
import com.example.firebasenotes.WidgetsCardView.Listing.ListingDrawer.DeleteDrawer
import com.example.firebasenotes.WidgetsCardView.Listing.ListingDrawer.Detalle
import com.example.firebasenotes.WidgetsCardView.Listing.ListingDrawer.DrawerScreen
import com.example.firebasenotes.pruebass.DataTypeId


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Preview
@Composable
fun App() {
    val navController = rememberNavController()
    var isAdmin: Int = 2

    val drawerItems = listOf(
        Triple("Mi Perfil", Icons.Default.Person, 2),
        Triple("Alta de Cajon", Icons.Default.Add, 2),
        Triple("Cat Areas", Icons.Default.Menu, 2),
        Triple("Alta de area", Icons.Default.Add, 1),
//        Triple("Reservar cajon", Icons.Default.AddCircle, 2),
//        Triple("Reservar Area", Icons.Default.AddCircle, 2),
//        Triple("Cat Cajones", Icons.Default.Menu, 2),
        Triple("Viaticos", Icons.Default.DateRange, 2),
        Triple("Lista de Usuarios", Icons.Default.DateRange, 2),



    )
        .filter { it.third == isAdmin }

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
            DrawerContent(drawerItems, navController)
        },
        content = {
            NavHost(
                navController = navController as NavHostController,
                startDestination = "Home"
            ) {
                composable("Home") {
                    //MiPerfil()
                }
                composable("Reservar cajon") {
                    DrawerScreen(navController = navController)
                }
                composable("Mis reservas") {
                    MiReservas()
                }

                    composable("Alta de Cajon") {
                        AltCajon()
                    }

                composable("DetalleCajon") {
                    Detalle()
                }
                composable("Cat Areas") {
                    ModAreaEliminar(areaViewModelDOS = viewModel(), navController = navController)
                }
                composable("Alta de area") {
                    AltaArea(navController = navController)

                }
                composable("DetalleAlta") {
                    //DetalleAlta()


                }
                composable("Actualizar") {
                    ActualizarPerfil()
                }
                composable("Mi Perfil") {
                    PerfilScreen(navController =navController)
                }
                composable("Cat Cajones") {
                    DeleteDrawer(navController = navController)
                }
                composable("Reservar Area") {
                    AreaScreen(areaViewModel = viewModel(), navController = navController)
                }
                composable("Viaticos") {
                    ViaticosScreen(navController = navController)
                }
                composable("imgViaticos") {
                    UploadImageScreen()
                }
                composable("viaticosAdd") {
                    DDViaticos(navController = navController)
                }
                composable("Lista de Usuarios") {
                    listUsers(navController = navController)
                }
                composable(
                    "detalleUser/{nombres}/{apellidos}/{empresa}/{email}/{puedeFacturar}/{usuarioHabilitado}",
                    arguments = listOf(
                        navArgument("nombres") { type = NavType.StringType },
                        navArgument("apellidos") { type = NavType.StringType },
                        navArgument("empresa") { type = NavType.StringType },
                        navArgument("email") { type = NavType.StringType },
                        navArgument("puedeFacturar") { type = NavType.BoolType },
                        navArgument("usuarioHabilitado") { type = NavType.BoolType }
                    )
                ) { backStackEntry ->
                    val nombres = backStackEntry.arguments?.getString("nombres") ?: ""
                    val apellidos = backStackEntry.arguments?.getString("apellidos") ?: ""
                    val empresa = backStackEntry.arguments?.getString("empresa") ?: ""
                    val email = backStackEntry.arguments?.getString("email") ?: ""
                    val puedeFacturar = backStackEntry.arguments?.getBoolean("puedeFacturar") ?: false
                    val usuarioHabilitado = backStackEntry.arguments?.getBoolean("usuarioHabilitado") ?: false

                    // Llama a la función detalleUser y pasa los datos del usuario
                    detalleUser(
                        navController = navController,
                        nombres = nombres,
                        apellidos = apellidos,
                        empresa = empresa,
                        email = email,
                        puedeFacturar = puedeFacturar,
                        usuarioHabilitado = usuarioHabilitado
                    )
                }
            }
        }
    )
}

@Composable
fun DrawerContent(
    drawerItems: List<Triple<String, ImageVector, Any>>,
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .padding(6.dp)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(2.dp))

        Image(
            painter = painterResource(R.drawable.img), // Reemplaza R.drawable.logo_image con el ID de tu imagen
            contentDescription = "",
            modifier = Modifier
                .padding(6.dp)
                .align(Alignment.CenterHorizontally)
                .size(100.dp)
                .clip(CircleShape)
        )
        Text(text = "ADMIN", fontSize = 12.sp)
        Text(text = "admin@ut.com", fontSize = 12.sp)
        Spacer(modifier = Modifier.height(22.dp))
        drawerItems.forEach { (title, icon) ->
            DrawerItem(title = title, icon = icon) {
                navController.navigate(title)
            }
            Spacer(modifier = Modifier.height(26.dp))
        }
    }
}

@Composable
fun DrawerItem(title: String, icon: ImageVector, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
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
        Text(
            text = "Sección seleccionada: $selectedSection",
            style = MaterialTheme.typography.labelMedium
        )
    }
}