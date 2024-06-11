import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
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
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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

import com.example.firebasenotes.Oficinas.DetalleOficinas
import com.example.firebasenotes.Oficinas.ListadoOficinas
import com.example.firebasenotes.Oficinas.OficinasViewModel

import com.example.firebasenotes.Oficinas.ReservaOficinas_extension
import com.example.firebasenotes.Viaticos.FacturaAdd.DDViaticos
import com.example.firebasenotes.R
import com.example.firebasenotes.SharedPreferencesManager
import com.example.firebasenotes.UsersAdmin.detalleUser

import com.example.firebasenotes.UsersAdmin.listUsers

import com.example.firebasenotes.Viaticos.Viaje.crearViaje
import com.example.firebasenotes.Viaticos.ViaticosScreen
import com.example.firebasenotes.ViewMenu.AltCajon
import com.example.firebasenotes.ViewMenu.MiPerfil

import com.example.firebasenotes.ViewMenu.ReservacionCajones_extension
import com.example.firebasenotes.ViewMenu.Mipefil.ActualizarPerfil
import com.example.firebasenotes.ViewMenu.Mipefil.DDViewModel
import com.example.firebasenotes.ViewMenu.Mipefil.PerfilScreen
import com.example.firebasenotes.WidgetsCardView.Listing.ListAreas.AltaArea
import com.example.firebasenotes.WidgetsCardView.Listing.ListAreas.AreaScreen
import com.example.firebasenotes.WidgetsCardView.Listing.ListAreas.ModAreaEliminar
import com.example.firebasenotes.WidgetsCardView.Listing.ListingDrawer.DeleteDrawer
import com.example.firebasenotes.WidgetsCardView.Listing.ListingDrawer.Detalle
import com.example.firebasenotes.WidgetsCardView.Listing.ListingDrawer.DrawerScreen
import com.example.firebasenotes.bill.FilePickerForm
import com.example.firebasenotes.viaje.ViajeDetalle
import com.example.firebasenotes.viewModels.LoginViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun App(ddViewModel: DDViewModel = viewModel()) {
    val userData = ddViewModel.state.value
    val navController = rememberNavController()
    var expanded by remember { mutableStateOf(false) }

    val drawerItems = listOf(
        Triple("Mi Perfil", Icons.Default.Person, 2 ),
        Triple("Alta de area", Icons.Default.AddCircle, 1),
        Triple("Cat Areas", Icons.Default.Menu, 1),
        Triple("Reservar cajon", Icons.Default.AddCircle, 2),
        Triple("Reservar Area", Icons.Default.AddCircle, 2),
        Triple("Alta de Cajon", Icons.Default.AddCircle, 2),
        Triple("Cat Cajones", Icons.Default.Menu, 1),
        Triple("Viaticos", Icons.Default.DateRange, 2),
        Triple("Lista de Usuarios", Icons.Default.Person, 2),
//        Triple("Mis reservas", Icons.Default.DateRange, 1),
//        Triple("XML", Icons.Default.DateRange, 4),


    ).filter { it.third == userData.typeId }

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                actions = {


//                    IconButton(onClick = { expanded = true }) {
//                        Icon(Icons.Default.Menu, contentDescription = "Menu")
//                    }
//
//                    DropdownMenu(
//                        expanded = expanded,
//                        onDismissRequest = { expanded = false }
//                    ) {
//                        DropdownMenuItem(
//                            text = { Text("Mi Perfil") },
//                            onClick = {
//                                expanded = false
//                                // Handle action for first item
//                            })
//                        DropdownMenuItem(
//                            trailingIcon = {
//                                Icon(Icons.Default.Image, contentDescription = null)
//                            },
//                            text = { Text("Mi tytytyttyjjjjjjjjjjjj") },
//                            onClick = {
//                                expanded = false
//                                // Handle action for first item
//                            })
//                        DropdownMenuItem(
//                            text = { Text("Mi tytyyt") },
//                            onClick = {
//                                expanded = false
//                                // Handle action for first item
//                            })
//                        DropdownMenuItem(
//                            text = { Text("Mi ok") },
//                            onClick = {
//                                expanded = false
//                                // Handle action for first item
//                            })
//                        DropdownMenuItem(
//                            text = { Text("Mi fdg") },
//                            onClick = {
//                                expanded = false
//                                // Handle action for first item
//                            })
//                        DropdownMenuItem(
//                            text = { Text("Mi gh") },
//                            onClick = {
//                                expanded = false
//                                // Handle action for first item
//                            })
//                    }
                },
                //¡Bienvenido!, ${userData.nombres}
                title = { Text(text = "") },
                navigationIcon = {

                    IconButton(onClick = {
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menu")
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
                composable("XML") {
                    FilePickerForm()
                }
                composable("Home") {
                    MiPerfil()
                }
                composable("Reservar cajon") {
                    DrawerScreen(navController = navController)
                }
                composable("Mis reservas") {
                    Reservacion()
                }
                composable("Alta de Cajon") {
                    AltCajon()
                }
                composable("Mis Oficinas") {
                    ListadoOficinas()
                }
                composable("DetalleOficinas/{capacidad}/{descripcion}/{id}/{mobilaria}/{nombre}/{idArea}",arguments = listOf(
                    navArgument("capacidad",)  { type = NavType.StringType },
                    navArgument("descripcion",)  { type = NavType.StringType },
                    navArgument("id",)  { type = NavType.StringType },
                    navArgument("mobilaria",)  { type = NavType.StringType },
                    navArgument("nombre",)  { type = NavType.StringType },
                    navArgument("idArea",)  { type = NavType.StringType },
                )){
                    var viewModel = OficinasViewModel()
                    val capacidad = it.arguments?.getString("capacidad") ?: ""
                    val descripcion = it.arguments?.getString("descripcion") ?: ""
                    val id = it.arguments?.getString("id") ?: ""
                    val mobilaria = it.arguments?.getString("mobilaria") ?: ""
                    val nombre = it.arguments?.getString("nombre") ?: ""
                    val idArea = it.arguments?.getString("idArea") ?: ""

                    DetalleOficinas(
                        navController = navController,
                        LocalContext.current,
                        capacidad,
                        descripcion,
                        id,
                        mobilaria,
                        nombre,
                        idArea,
                    )
                }
                composable("DetalleCajon/{nombre}/{company}/{cajon}/{piso}/{esEspecial}/{idEstacionamiento}",arguments = listOf(
                    navArgument("nombre",)  { type = NavType.StringType },
                    navArgument("company",) { type = NavType.StringType },
                    navArgument("cajon",) { type = NavType.StringType },
                    navArgument("piso",) { type = NavType.StringType },
                    navArgument("esEspecial",) { type = NavType.BoolType },
                    navArgument("idEstacionamiento",) { type = NavType.StringType }
                )) {

                    val nombre = it.arguments?.getString("nombre") ?: ""
                    val company = it.arguments?.getString("company") ?: ""
                    val cajon = it.arguments?.getString("cajon") ?: ""
                    val piso = it.arguments?.getString("piso") ?: ""
                    val esEspecial = it.arguments?.getBoolean("esEspecial") ?: false

                    val esEspecialString = if (esEspecial) "1" else "0"
                    val idEstacionamiento = it.arguments?.getString("idEstacionamiento") ?: ""


                    val context = LocalContext.current
                    Detalle(navController = navController,context, nombre,company,cajon,piso,esEspecial,idEstacionamiento)
                }
                composable("Cat Areas") {
                    ModAreaEliminar(areaViewModelDOS = viewModel(), navController = navController)
                }
                composable("Alta de area") {
                    AltaArea(navController = navController)
                }
                composable("Actualizar") {
                    val sharedPreferencesManager = SharedPreferencesManager(LocalContext.current)

                    ActualizarPerfil(ddViewModel=ddViewModel,sharedPreferencesManager = sharedPreferencesManager)
                }
                composable("Mi Perfil") {
                    PerfilScreen(navController = navController, userData = userData)
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
                    UploadImageScreen()                }
                composable("Crear viaje"){
                    crearViaje()
                }
                composable("viaticosAdd/{viajeId}", arguments = listOf(
                    navArgument("viajeId") { type = NavType.StringType },
                )) {
                    backStackEntry ->
                    val viajeId = backStackEntry.arguments?.getString("viajeId") ?: ""
                    DDViaticos(navController = navController,viajeId)
                }

                composable("Lista de Usuarios") {
                    listUsers(navController = navController)
                }
                composable(
                    "detalleUser/{nombres}/{apellidos}/{empresa}/{email}/{puedeFacturar}/{usuarioHabilitado}/{typeId}",
                    arguments = listOf(
                        navArgument("nombres") { type = NavType.StringType },
                        navArgument("apellidos") { type = NavType.StringType },
                        navArgument("empresa") { type = NavType.StringType },
                        navArgument("email") { type = NavType.StringType },
                        navArgument("puedeFacturar") { type = NavType.BoolType },
                        navArgument("usuarioHabilitado") { type = NavType.BoolType },
                        navArgument("typeId") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val nombres = backStackEntry.arguments?.getString("nombres") ?: ""
                    val apellidos = backStackEntry.arguments?.getString("apellidos") ?: ""
                    val empresa = backStackEntry.arguments?.getString("empresa") ?: ""
                    val email = backStackEntry.arguments?.getString("email") ?: ""
                    val puedeFacturar = backStackEntry.arguments?.getBoolean("puedeFacturar") ?: false
                    val usuarioHabilitado = backStackEntry.arguments?.getBoolean("usuarioHabilitado") ?: false
                    val typeId = backStackEntry.arguments?.getString("typeId") ?: "2"

                    // Llama a la función detalleUser y pasa los datos del usuario
                    detalleUser(
                        navController = navController,
                        nombres = nombres,
                        apellidos = apellidos,
                        empresa = empresa,
                        email = email,
                        puedeFacturar = puedeFacturar,
                        usuarioHabilitado = usuarioHabilitado,
                        typeId = typeId.toInt()
                    )
                }


                composable("ReservaOficinas_extension/{capacidad}/{descripcion}/{id}/{mobilaria}/{nombre}/{idArea}",arguments = listOf(
                    navArgument("capacidad",)  { type = NavType.StringType },
                    navArgument("descripcion",)  { type = NavType.StringType },
                    navArgument("id",)  { type = NavType.StringType },
                    navArgument("mobilaria",)  { type = NavType.StringType },
                    navArgument("nombre",)  { type = NavType.StringType },
                    navArgument("idArea",)  { type = NavType.StringType },
                )){

                    val capacidad = it.arguments?.getString("capacidad") ?: ""
                    val descripcion = it.arguments?.getString("descripcion") ?: ""
                    val id = it.arguments?.getString("id") ?: ""
                    val mobilaria = it.arguments?.getString("mobilaria") ?: ""
                    val nombre = it.arguments?.getString("nombre") ?: ""
                    val idArea = it.arguments?.getString("idArea") ?: ""

                ReservaOficinas_extension(
                    navController = navController,
                    LocalContext.current,
                    capacidad,
                    descripcion,
                    id,
                    mobilaria,
                    nombre,
                    idArea)
                }

                composable("ReservacionCajones_extension/{nombre}/{company}/{cajon}/{piso}/{esEspecial}/{idEstacionamiento}",
                    arguments = listOf(
                        navArgument("nombre",)  { type = NavType.StringType },
                        navArgument("company",) { type = NavType.StringType },
                        navArgument("cajon",) { type = NavType.StringType },
                        navArgument("piso",) { type = NavType.StringType },
                        navArgument("esEspecial",) { type = NavType.BoolType },
                        navArgument("idEstacionamiento",) { type = NavType.StringType }
                    )){

                    val nombre = it.arguments?.getString("nombre") ?: ""
                    val company = it.arguments?.getString("company") ?: ""
                    val cajon = it.arguments?.getString("cajon") ?: "0"
                    val piso = it.arguments?.getString("piso") ?: ""
                    val esEspecial = it.arguments?.getBoolean("esEspecial") ?: false

                    val esEspecialString = if (esEspecial) "1" else "0"
                    val idEstacionamiento = it.arguments?.getString("idEstacionamiento") ?: ""

                    val context = LocalContext.current
                    var vm = LoginViewModel()
                    ReservacionCajones_extension(vm,context,nombre,company,cajon,piso,esEspecial,idEstacionamiento)
                }


                composable("Viaje2/{motivo}", arguments = listOf(
                    navArgument("motivo",) { type = NavType.StringType },
                )) {

                    val motivo = it.arguments?.getString("motivo") ?: "Viaje de negocios"
                    ViajeDetalle("")
                }

                composable("Viaje") {
                    ViajeDetalle("Viaje de negocios")
                }

            }
        }
    )
}

@Composable
fun DrawerContent(
    drawerItems: List<Triple<String, ImageVector, Any>>,
    navController: NavHostController,
    ddViewModel: DDViewModel = viewModel()
) {
    val userData = ddViewModel.state.value
    Column(
        modifier = Modifier
            .padding(6.dp)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(2.dp))

        Image(
            painter = painterResource(R.drawable.isita2), // Reemplaza R.drawable.logo_image con el ID de tu imagen
            contentDescription = "",
            modifier = Modifier
                .padding(6.dp)
                .align(Alignment.CenterHorizontally)
                .size(140.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(22.dp))

        Text(text = "${userData.nombres} ${userData.apellidos}", fontSize = 18.sp, fontWeight = FontWeight.Bold
                )
        Text(text = userData.email, fontSize = 15.sp, fontWeight = FontWeight.Medium
        )

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