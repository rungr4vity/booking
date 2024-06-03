package com.example.firebasenotes.Oficinas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasenotes.models.oficinasDTO
import kotlinx.coroutines.launch

class OficinasListadoViewModel:ViewModel(

) {


    fun getOficinas(username: String) {
        viewModelScope.launch {


        }
    }
    suspend fun listadoOficinas(username: String): List<oficinasDTO> {

        return emptyList()
    }


} // end class OficinasListadoViewModel