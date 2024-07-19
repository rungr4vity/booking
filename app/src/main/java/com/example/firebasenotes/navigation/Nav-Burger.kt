import android.annotation.SuppressLint
import android.content.Intent
import androidx.activity.compose.BackHandler
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
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Output
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.firebasenotes.MainActivity
import com.example.firebasenotes.presentation.view.DetalleOficinas
import com.example.firebasenotes.presentation.view.EditarOficinas


import com.example.firebasenotes.oficinas.viewmodels.OficinasViewModel
import com.example.firebasenotes.presentation.view.ReservaOficinas_extension
import com.example.firebasenotes.presentation.view.DDViaticos
import com.example.firebasenotes.R
import com.example.firebasenotes.WidgetsCardView.Listing.ListingDrawer.Detalle
import com.example.firebasenotes.presentation.viewModel.SharedPreferencesManager
import com.example.firebasenotes.presentation.view.detalleUser

import com.example.firebasenotes.presentation.view.listUsers

import com.example.firebasenotes.presentation.view.crearViaje
import com.example.firebasenotes.presentation.view.ViaticosScreen
import com.example.firebasenotes.estacionamientos.views.AltCajon
import com.example.firebasenotes.presentation.view.MiPerfil

import com.example.firebasenotes.presentation.view.ReservacionCajones_extension
import com.example.firebasenotes.presentation.view.ActualizarPerfil
import com.example.firebasenotes.presentation.viewModel.DDViewModel
import com.example.firebasenotes.presentation.view.PerfilScreen
import com.example.firebasenotes.presentation.view.AltaArea
import com.example.firebasenotes.presentation.view.AreaScreen



import com.example.firebasenotes.presentation.view.DrawerScreen
import com.example.firebasenotes.presentation.viewModel.DrawerViewModel
import com.example.firebasenotes.estacionamientos.views.UpdateEstacionamiento
import com.example.firebasenotes.presentation.view.RevoficinasScreen
import com.example.firebasenotes.domain.bill.FilePickerForm
import com.example.firebasenotes.estacionamientos.viewmodels.EstacionamientosViewModel
import com.example.firebasenotes.presentation.view.ViajeDetalle
import com.example.firebasenotes.login.viewmodels.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import java.util.Calendar


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun App(ddViewModel: DDViewModel = viewModel()) {
    val userData = ddViewModel.state.value
    val navController = rememberNavController()
    var expanded by remember { mutableStateOf(false) }

    val drawerItems = listOf(
        //Admin
        Triple("Home", Icons.Default.Home, 0),
        Triple("Mi Perfil", Icons.Default.Person, 0),
//        Triple("Alta de area", Icons.Default.AddCircle, 0),
//        Triple("Cat Areas", Icons.Default.Menu, 0),
        Triple("Estacionamientos", Icons.Default.AddCircle, 0),
        Triple("Oficinas", Icons.Default.AddCircle, 0),
//        Triple("Alta de Cajon", Icons.Default.AddCircle, 0),
//        Triple("Cat Cajones", Icons.Default.Menu, 0),
        Triple("Viáticos", Icons.Default.DateRange, 0),
        Triple("Lista de Usuarios", Icons.Default.Person, 0),
        Triple("Cerrar sesión", Icons.Default.Output, 0),

        //Usuario
        Triple("Home", Icons.Default.Home, 2),
        Triple("Mi Perfil", Icons.Default.Person, 2),
        Triple("Estacionamientos", Icons.Default.AddCircle, 2),
        Triple("Oficinas", Icons.Default.AddCircle, 2),
        Triple("Viáticos", Icons.Default.DateRange, 2),
        Triple("Cerrar sesión", Icons.Default.Output, 2),


//        Triple("Mis reservas", Icons.Default.DateRange, 1),
//        Triple("XML", Icons.Default.DateRange, 4),


    ).filter { it.third == userData.typeId }

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = "¡Bienvenido!") },
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
            DrawerContent(drawerItems, navController, closeDrawer = {
                scope.launch {
                    scaffoldState.drawerState.close()
                }
            })
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
                composable("Estacionamientos") {

                    LaunchedEffect(Unit) {

                        val drawerViewModel = DrawerViewModel()
                        val year = Calendar.getInstance().get(Calendar.YEAR)
                        val dayOfYear_ = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)

                        drawerViewModel.reload()
                        drawerViewModel.getAll(year,dayOfYear_)
                    }

                    DrawerScreen(navController = navController)

                }
                composable("Mis reservas") {
                    ReservacionEstacionamientoScreen()
                }
                composable("Mis reservasOficina") {
                    RevoficinasScreen()
                }


                composable("Alta de Cajon") {
                    AltCajon()
                }



                composable(
                    "DetalleOficinas/{capacidad}/{descripcion}/{id}/{mobilaria}/{nombre}/{idArea}/{imageUrl}",
                    arguments = listOf(
                        navArgument("capacidad") { type = NavType.StringType },
                        navArgument("descripcion") { type = NavType.StringType },
                        navArgument("id") { type = NavType.StringType },
                        navArgument("mobilaria") { type = NavType.StringType },
                        navArgument("nombre") { type = NavType.StringType },
                        navArgument("idArea") { type = NavType.StringType },
                        navArgument("imageUrl") { type = NavType.StringType }
                    )
                ) {


                    var viewModel = OficinasViewModel()
                    val capacidad = it.arguments?.getString("capacidad") ?: ""
                    val descripcion = it.arguments?.getString("descripcion") ?: ""
                    val id = it.arguments?.getString("id") ?: ""
                    val mobilaria = it.arguments?.getString("mobilaria") ?: ""
                    val nombre = it.arguments?.getString("nombre") ?: ""
                    val idArea = it.arguments?.getString("idArea") ?: ""
                    val imageUrl = it.arguments?.getString("imageUrl") ?: ""

                    DetalleOficinas(
                        navController = navController,
                        LocalContext.current,
                        capacidad,
                        descripcion,
                        id,
                        mobilaria,
                        nombre,
                        idArea,
                        imageUrl,
                    )
                }


                composable("DetalleCajon/{nombre}/{company}/{cajon}/{piso}/{esEspecial}/{idEstacionamiento}/{imagen}",
                    arguments = listOf(
                        navArgument("nombre") { type = NavType.StringType },
                        navArgument("company") { type = NavType.StringType },
                        navArgument("cajon") { type = NavType.StringType },
                        navArgument("piso") { type = NavType.StringType },
                        navArgument("esEspecial") { type = NavType.BoolType },
                        navArgument("idEstacionamiento") { type = NavType.StringType },
                        navArgument("imagen") { type = NavType.StringType }
                    )) {

                    val nombre = it.arguments?.getString("nombre") ?: ""
                    val company = it.arguments?.getString("company") ?: ""
                    val cajon = it.arguments?.getString("cajon") ?: ""
                    val piso = it.arguments?.getString("piso") ?: ""
                    val esEspecial = it.arguments?.getBoolean("esEspecial") ?: false

                    val esEspecialString = if (esEspecial) "1" else "0"
                    val idEstacionamiento = it.arguments?.getString("idEstacionamiento") ?: ""
                    val imagen = it.arguments?.getString("imagen") ?: ""


                    val context = LocalContext.current
                    Detalle(
                        navController = navController,
                        context,
                        nombre,
                        company,
                        cajon,
                        piso,
                        esEspecial,
                        idEstacionamiento,
                        imagen
                    )
                }


                composable("UpdateEstacionamiento/{idEstacionamiento}/{imagen}/{nombre}/{numero}/{company}/{piso}/{esEspecial}"
                       , arguments = listOf(
                            navArgument("idEstacionamiento") { type = NavType.StringType },
                            navArgument("imagen") { type = NavType.StringType },
                            navArgument("nombre") { type = NavType.StringType },
                            navArgument("numero") { type = NavType.StringType },
                            navArgument("company") { type = NavType.StringType },
                            navArgument("piso") { type = NavType.StringType },
                            navArgument("esEspecial") { type = NavType.StringType },
                    ))
                {

                    val idEstacionamiento = it.arguments?.getString("idEstacionamiento") ?: ""
                    val imagen = it.arguments?.getString("imagen") ?: ""
                    val nombre = it.arguments?.getString("nombre") ?: ""
                    val company = it.arguments?.getString("company") ?: ""
                    val numero = it.arguments?.getString("numero") ?: ""
                    val piso = it.arguments?.getString("piso") ?: ""
                    val esEspecial = it.arguments?.getBoolean("esEspecial") ?: false
                    val esEspecialString = if (esEspecial) "1" else "0"

                    val context = LocalContext.current
                    val viewModel = EstacionamientosViewModel()
                    UpdateEstacionamiento(
                        viewModel,
                        navController = navController,
                        context,idEstacionamiento,imagen,nombre,numero,company,piso,esEspecial
                    )
                }



                composable("UpdateEstacionamiento_/{idEstacionamiento/{imagen}" +
                        "/{nombre}/{company}/{cajon}/{piso}/{esEspecial}", arguments = listOf(
                    navArgument("idEstacionamiento") { type = NavType.StringType },
                    navArgument("imagen") { type = NavType.StringType },
                    navArgument("nombre") { type = NavType.StringType },
                    navArgument("company") { type = NavType.StringType },
                    navArgument("cajon") { type = NavType.StringType },
                    navArgument("piso") { type = NavType.StringType },
                    navArgument("esEspecial") { type = NavType.BoolType },

                ))
                {

                    val idEstacionamiento = it.arguments?.getString("idEstacionamiento") ?: ""
                    val nombre = it.arguments?.getString("nombre") ?: ""
                    val company = it.arguments?.getString("company") ?: ""
                    val cajon = it.arguments?.getString("cajon") ?: ""
                    val piso = it.arguments?.getString("piso") ?: ""
                    val esEspecial = it.arguments?.getBoolean("esEspecial") ?: false
                    val esEspecialString = if (esEspecial) "1" else "0"
                    val imagen = it.arguments?.getString("imagen") ?: ""

                    val context = LocalContext.current

                }




                composable("Alta de area") {
                    AltaArea(navController = navController)
                }
//                composable("DetalleAlta") {
//                    DetalleAlta()
//                }
                composable("Actualizar") {
                    val sharedPreferencesManager = SharedPreferencesManager(LocalContext.current)

                    ActualizarPerfil(
                        ddViewModel = ddViewModel,
                        sharedPreferencesManager = sharedPreferencesManager
                    )
                }
                composable("Mi Perfil") {
                    PerfilScreen(navController = navController, userData = userData)
                }

                composable("Oficinas") {
                    AreaScreen(areaViewModel = viewModel(), navController = navController)
                }
                composable("Viáticos") {
                    ViaticosScreen(navController = navController)
                }

                composable("Crear viaje") {
                    crearViaje()
                }


                composable(
                    "viaticosAdd/{viajeId}", arguments = listOf(
                        navArgument("viajeId") { type = NavType.StringType },
                    )
                ) { backStackEntry ->
                    val viajeId = backStackEntry.arguments?.getString("viajeId") ?: ""
                    DDViaticos(navController = navController, viajeId)
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
                    val puedeFacturar =
                        backStackEntry.arguments?.getBoolean("puedeFacturar") ?: false
                    val usuarioHabilitado =
                        backStackEntry.arguments?.getBoolean("usuarioHabilitado") ?: false
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


                composable(
                    "ReservaOficinas_extension/{capacidad}/{descripcion}/{id}/{mobilaria}/{nombre}/{idArea}",
                    arguments = listOf(
                        navArgument("capacidad") { type = NavType.StringType },
                        navArgument("descripcion") { type = NavType.StringType },
                        navArgument("id") { type = NavType.StringType },
                        navArgument("mobilaria") { type = NavType.StringType },
                        navArgument("nombre") { type = NavType.StringType },
                        navArgument("idArea") { type = NavType.StringType },
                    )
                ) {

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
                        idArea
                    )
                }

                composable("ReservacionCajones_extension/{nombre}/{company}/{cajon}/{piso}/{esEspecial}/{idEstacionamiento}",
                    arguments = listOf(
                        navArgument("nombre") { type = NavType.StringType },
                        navArgument("company") { type = NavType.StringType },
                        navArgument("cajon") { type = NavType.StringType },
                        navArgument("piso") { type = NavType.StringType },
                        navArgument("esEspecial") { type = NavType.BoolType },
                        navArgument("idEstacionamiento") { type = NavType.StringType }
                    )) {

                    val nombre = it.arguments?.getString("nombre") ?: ""
                    val company = it.arguments?.getString("company") ?: ""
                    val cajon = it.arguments?.getString("cajon") ?: "0"
                    val piso = it.arguments?.getString("piso") ?: ""
                    val esEspecial = it.arguments?.getBoolean("esEspecial") ?: false

                    val esEspecialString = if (esEspecial) "1" else "0"
                    val idEstacionamiento = it.arguments?.getString("idEstacionamiento") ?: ""

                    val context = LocalContext.current
                    var vm = LoginViewModel()
                    ReservacionCajones_extension(
                        vm,
                        context,
                        nombre,
                        company,
                        cajon,
                        piso,
                        esEspecial,
                        idEstacionamiento,
                        navController = navController
                    )
                }


                composable(
                    "Viaje2/{motivo}", arguments = listOf(
                        navArgument("motivo") { type = NavType.StringType },
                    )
                ) {

                    val motivo = it.arguments?.getString("motivo") ?: "Viaje de negocios"
                    ViajeDetalle("")
                }

                composable("Viaje") {
                    ViajeDetalle("Viaje de negocios")
                }


                composable("Cerrar sesión") {
                    FirebaseAuth.getInstance().signOut()
                    //var loginVM = LoginViewModel()
                    //LoginView(navController = navController, loginvm)
                    //TabsViews(navController,loginVM)

                    val intent = Intent(LocalContext.current, MainActivity::class.java)
                    LocalContext.current.startActivity(intent)


                    BackHandler(true) {
                        // Or do nothing
                        //Log.i("LOG_TAG", "Clicked back")
                    }

                }
                composable("Inicio") {
                    DrawerContent(navController = navController, drawerItems = drawerItems,ddViewModel = ddViewModel, closeDrawer = {})
                }
                composable("EditarOficinas/{idArea}/{capacidad}/{descripcion}/{id}/ {mobilaria}/{nombre}/{imagen}",
                    arguments = listOf(
                        navArgument("idArea") { type = NavType.StringType },
                        navArgument("capacidad") { type = NavType.StringType },
                        navArgument("descripcion") { type = NavType.StringType },
                        navArgument("id") { type = NavType.StringType },
                        navArgument("mobilaria") { type = NavType.StringType },
                        navArgument("nombre") { type = NavType.StringType },
                        navArgument("imagen") { type = NavType.StringType }

                    ) ){
                    val idArea = it.arguments?.getString("idArea") ?: ""
                    val capacidad = it.arguments?.getString("capacidad") ?: ""
                    val descripcion = it.arguments?.getString("descripcion") ?: ""
                    val id = it.arguments?.getString("id") ?: ""
                    val mobilaria = it.arguments?.getString("mobilaria") ?: ""
                    val nombre = it.arguments?.getString("nombre") ?: ""
                    val imagen = it.arguments?.getString("imagen") ?: ""

                    EditarOficinas(
                        idArea,
                        capacidad,
                        descripcion,
                        id,
                        mobilaria,
                        nombre,
                        imagen,
                    )
                }

            }
        }
    )
}

@Composable
fun DrawerContent(
    drawerItems: List<Triple<String, ImageVector, Any>>,
    navController: NavHostController,
    ddViewModel: DDViewModel = viewModel(),
    closeDrawer: () -> Unit
) {
    CompositionLocalProvider(LocalContentColor provides Color.Black) {
        val userData = ddViewModel.state.value
        val empresa = userData.empresa

        Column(
            modifier = Modifier
                .padding(6.dp)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(2.dp))

            Image(
                painter = painterResource(R.drawable.ofi), // Reemplaza R.drawable.logo_image con el ID de tu imagen
                contentDescription = "",
                modifier = Modifier
                    .padding(6.dp)
                    .align(Alignment.CenterHorizontally)
                    .size(140.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = "${userData.nombres} ${userData.apellidos}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = userData.email, fontSize = 15.sp, fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(22.dp))
            drawerItems.forEach { (title, icon, _) ->
                DrawerItem(title = title, icon = icon, onClick = {
                    navController.navigate(title)
                }, closeDrawer = closeDrawer)
                Spacer(modifier = Modifier.height(26.dp))
            }
        }
    }
}

@Composable
fun DrawerItem(title: String, icon: ImageVector, onClick: () -> Unit, closeDrawer: () -> Unit) {
     Row(
            verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier.clickable {
        onClick()
        closeDrawer() // Aquí cerramos el menú después de navegar
    }
    ){
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