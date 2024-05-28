import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.firebasenotes.pruebass.DataTypeId
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Preview
@Composable
fun TypedScreen(viewModel: TypedViewModel = viewModel()) {
    // Observa los datos de Firebase utilizando el ViewModel
    val typedDataState = viewModel.typedData.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Mostrar datos recuperados de Firebase
        when (val typedData = typedDataState.value) {
            is TypedDataState.Loading -> {
                // Muestra una indicación de carga mientras se obtienen los datos
                Text("Cargando datos...")
            }
            is TypedDataState.Success -> {
                // Muestra los datos en un ExposedDropdownMenuBox
                TypedDropdownMenu(typedData.dataList)
            }
            is TypedDataState.Error -> {
                // Muestra un mensaje de error si ocurrió un problema al obtener los datos
                Text("Error al obtener los datos: ${typedData.message}")
            }

            else -> {}
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TypedDropdownMenu(dataList: List<DataTypeId>) {
    val expanded = remember { mutableStateOf(false) }
    val selectedDescripcion = remember { mutableStateOf("") }

    ExposedDropdownMenuBox(
        expanded = expanded.value,
        onExpandedChange = { expanded.value = it },
        modifier = Modifier.padding(horizontal = 40.dp)
    ) {
        OutlinedTextField(
            value = selectedDescripcion.value,
            onValueChange = { selectedDescripcion.value = it },
            readOnly = false,
            label = { Text("Seleccione una opción") },
            modifier = Modifier.fillMaxWidth()
        )
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            dataList.forEach { data ->
                DropdownMenuItem(
                    onClick = {
                        selectedDescripcion.value = data.descripcion
                        expanded.value = false
                    }
                ) {
                    Text(data.descripcion)
                }
            }
        }
    }
}

sealed class TypedDataState {
    object Loading : TypedDataState()
    data class Success(val dataList: List<DataTypeId>) : TypedDataState()
    data class Error(val message: String) : TypedDataState()
}

class TypedViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()

    private val _typedData = MutableStateFlow<TypedDataState>(TypedDataState.Loading)
    val typedData: StateFlow<TypedDataState> = _typedData

    init {
        fetchTypedData()
    }

    private fun fetchTypedData() {
        firestore.collection("TypeId")
            .get()
            .addOnSuccessListener { result ->
                val dataList = mutableListOf<DataTypeId>()
                for (document in result) {
                    val descripcion = document.getString("descripcion") ?: ""
                    val typeId = document.getLong("typeId")?.toInt() ?: 0
                    dataList.add(DataTypeId(descripcion, typeId))
                }
                _typedData.value = TypedDataState.Success(dataList)
            }
            .addOnFailureListener { exception ->
                _typedData.value = TypedDataState.Error(exception.message ?: "Error desconocido")
            }
    }

    fun updateTypeId(documentId: String, newTypeId: Int) {
        firestore.collection("TypeId").document(documentId)
            .update("typeId", newTypeId)
            .addOnSuccessListener {
                // Handle success if needed
            }
            .addOnFailureListener { exception ->
                // Handle failure if needed
            }
    }
}
