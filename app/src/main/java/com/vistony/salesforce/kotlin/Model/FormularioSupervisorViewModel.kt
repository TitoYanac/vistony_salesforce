package com.vistony.salesforce.kotlin.Model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.vistony.salesforce.Entity.SesionEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class AtributosFormulario() {
    var isMainDataOpen = false
    var isVisitDataOpen = false
    var isQuestionDataOpen = false

}

class FormularioSupervisorViewModel(
    private val formularioSupervisorRepository: FormularioSupervisorRepository
) : ViewModel() {
    private val _resultDB = MutableStateFlow(ApiResponseEntity())
    val resultDB: StateFlow<ApiResponseEntity> get() = _resultDB

    private val _resultAPI = MutableStateFlow(ApiResponseEntity())
    val resultAPI = _resultAPI.asStateFlow()


    private val _atributosFormulario = MutableStateFlow(AtributosFormulario())
    val atributosFormulario = _atributosFormulario.asStateFlow()
    class FormularioSupervisorViewModelFactory( private val formularioSupervisorRepository: FormularioSupervisorRepository ): ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FormularioSupervisorViewModel(
                    formularioSupervisorRepository
            ) as T
        }
    }

    init{
        val newObject = AtributosFormulario()
        newObject.isMainDataOpen = true
        newObject.isVisitDataOpen = true
        newObject.isQuestionDataOpen = true
        _atributosFormulario.value = newObject
        viewModelScope.launch {
            _resultAPI.value = formularioSupervisorRepository.conseguir_desde_API_FormularioSupervisor(SesionEntity.imei,getDateCurrentFormulario())
        }
        //observador de la variable resultDB del repositorio
        viewModelScope.launch {
            formularioSupervisorRepository.resultDB.collectLatest { newResult ->
                _resultDB.value = newResult
                Log.e("jesusdebug", "FormularioSupervisorViewModel-init-_result.value: ${_resultDB.value}")
            }
        }

    }

    fun enviar_a_ROOM_FormularioSupervisor()
    {
        if(_resultAPI.value.Data == null) return
        viewModelScope.launch {
            formularioSupervisorRepository.enviar_a_ROOM_FormularioSupervisor(_resultAPI.value.Data!!)
        }
    }

    fun enviar_a_API_FormularioSupervisor()
    {
        viewModelScope.launch {
            formularioSupervisorRepository.sendFormularioToApi(_resultAPI.value.Data!!)
        }
    }
    fun getDateCurrentFormulario(): String {
        val format = "yyyy-MM-dd"
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern(format)
        return currentDate.format(formatter)
    }


    private val _isSection2Expanded = MutableStateFlow(true)
    val isSection2Expanded: StateFlow<Boolean> get() = _isSection2Expanded

    private val _isSection3Expanded = MutableStateFlow(true)
    val isSection3Expanded: StateFlow<Boolean> get() = _isSection3Expanded


    private val _isButtonEnabled = MutableStateFlow(true)
    val isButtonEnabled: StateFlow<Boolean> get() = _isButtonEnabled

    private val _isButtonEnabled2 = MutableStateFlow(false)
    val isButtonEnabled2: StateFlow<Boolean> get() = _isButtonEnabled2

    private val _shouldShowPopup = MutableStateFlow(false)
    val shouldShowPopup: StateFlow<Boolean> get() = _shouldShowPopup

    private val _popupMessage = MutableStateFlow("")
    val popupMessage: StateFlow<String> get() = _popupMessage

    // Funciones para actualizar los estados
    fun updateButtonEnabled(isEnabled: Boolean) {
        _isButtonEnabled.value = isEnabled
    }

    fun updateButtonEnabled2(isEnabled: Boolean) {
        _isButtonEnabled2.value = isEnabled
    }

    fun updateShouldShowPopup(shouldShow: Boolean) {
        _shouldShowPopup.value = shouldShow
    }

    fun updatePopupMessage(valor: String) {
        _popupMessage.value = valor
    }
    fun updateSection2Expanded(expanded: Boolean) {
        _isSection2Expanded.value = expanded
    }
    fun updateSection3Expanded(expanded: Boolean) {
        _isSection3Expanded.value = expanded
    }

    fun toggleMainSection() {
        val newObject = AtributosFormulario()
        newObject.isMainDataOpen = !(_atributosFormulario.value.isMainDataOpen)
        newObject.isVisitDataOpen = _atributosFormulario.value.isVisitDataOpen
        newObject.isQuestionDataOpen = _atributosFormulario.value.isQuestionDataOpen
        _atributosFormulario.value = newObject
    }
    fun toggleVisitSection() {
        val newObject = AtributosFormulario()
        newObject.isMainDataOpen = _atributosFormulario.value.isMainDataOpen
        newObject.isVisitDataOpen = !(_atributosFormulario.value.isVisitDataOpen)
        newObject.isQuestionDataOpen = _atributosFormulario.value.isQuestionDataOpen
        _atributosFormulario.value = newObject
    }
    fun toggleQuestionSection() {
        val newObject = AtributosFormulario()
        newObject.isMainDataOpen = _atributosFormulario.value.isMainDataOpen
        newObject.isVisitDataOpen = _atributosFormulario.value.isVisitDataOpen
        newObject.isQuestionDataOpen = !(_atributosFormulario.value.isQuestionDataOpen)
        _atributosFormulario.value = newObject
    }

    fun actualizarMarcadoTipoSalida(index: Int, checked: Boolean) {
        val newObject = ApiResponseEntity()
        newObject.StatusCode = _resultAPI.value.StatusCode
        newObject.Data = _resultAPI.value.Data
        newObject.Data?.datosPrincipales?.tipoSalida?.get(index)?.marcado = checked
        _resultAPI.value = newObject
    }

    fun updateZona(zona: String) {
        val newObject = ApiResponseEntity()
        newObject.StatusCode = _resultAPI.value.StatusCode
        newObject.Data = _resultAPI.value.Data
        newObject.Data?.datosVisita?.zona = zona
        _resultAPI.value = newObject
    }

    fun updateObservacionZona(observation: String) {
        val newObject = ApiResponseEntity()
        newObject.StatusCode = _resultAPI.value.StatusCode
        newObject.Data = _resultAPI.value.Data
        newObject.Data?.datosVisita?.observacionZona = observation
        _resultAPI.value = newObject
    }

    fun updateHoraInicio(horainicio: String) {
        val newObject = ApiResponseEntity()
        newObject.StatusCode = _resultAPI.value.StatusCode
        newObject.Data = _resultAPI.value.Data
        newObject.Data?.datosVisita?.horaInicio = horainicio
        _resultAPI.value = newObject
    }

    fun updateHoraFin(horafin: String) {
        val newObject = ApiResponseEntity()
        newObject.StatusCode = _resultAPI.value.StatusCode
        newObject.Data = _resultAPI.value.Data
        newObject.Data?.datosVisita?.horaFin = horafin
        _resultAPI.value = newObject
    }

    fun updateClientesNuevos(clientesnuevos: String) {
        val newObject = ApiResponseEntity()
        newObject.StatusCode = _resultAPI.value.StatusCode
        newObject.Data = _resultAPI.value.Data
        newObject.Data?.datosVisita?.clientesNuevos = clientesnuevos
        _resultAPI.value = newObject
    }

    fun updateClientesEmpadronados(clientesempadronados: String) {
        val newObject = ApiResponseEntity()
        newObject.StatusCode = _resultAPI.value.StatusCode
        newObject.Data = _resultAPI.value.Data
        newObject.Data?.datosVisita?.clientesEmpadronados = clientesempadronados
        _resultAPI.value = newObject
    }

    fun updateGaleria(imagenesGaleria: List<FormularioGaleria>) {
        val newObject = ApiResponseEntity()
        newObject.StatusCode = _resultAPI.value.StatusCode
        newObject.Data = _resultAPI.value.Data
        newObject.Data?.galeria = imagenesGaleria
        _resultAPI.value = newObject

    }

    fun updateComentario(comentario: String) {
        val newObject = ApiResponseEntity()
        newObject.StatusCode = _resultAPI.value.StatusCode
        newObject.Data = _resultAPI.value.Data
        newObject.Data?.comentario = comentario
        _resultAPI.value = newObject
    }

    fun updateRespuesta(index: Int, opcion: String?, valor: String?) {
        val newObject = ApiResponseEntity()
        newObject.StatusCode = _resultAPI.value.StatusCode
        newObject.Data = _resultAPI.value.Data
        newObject.Data?.formulario?.get(index)?.respuesta = opcion
        newObject.Data?.formulario?.get(index)?.valor = valor
        _resultAPI.value = newObject

    }
}