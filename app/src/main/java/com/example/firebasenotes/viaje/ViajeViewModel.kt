package com.example.firebasenotes.viaje

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.firebasenotes.utils.viajesInterface
import kotlinx.coroutines.launch

class ViajeViewModel: ViewModel() {

    init {
        viewModelScope.launch {
            var data = viajesInterface.getViajes()
        }
    }


}