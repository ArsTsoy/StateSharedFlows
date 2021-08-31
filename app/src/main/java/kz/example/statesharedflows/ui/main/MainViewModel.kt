package kz.example.statesharedflows.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    //region LiveData
    //TODO: показать почему именно Single
    private val navigation: MutableSharedFlow<Navigation?> = MutableSharedFlow(replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    fun observeNavigation(): SharedFlow<Navigation?> = navigation.asSharedFlow()

    private val text: MutableStateFlow<String> = MutableStateFlow(arrayText[0])
    //TODO: рассказать о важности метода asStateFlow()
    fun observeText(): StateFlow<String> = text.asStateFlow()

    //TODO: можно симитировать StateFlow
//    private val text: MutableSharedFlow<String> = MutableSharedFlow<String>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST).apply {
//        tryEmit(arrayText[0])
//    }
//    fun observeText(): SharedFlow<String> = text.asSharedFlow()
    //endregion

    fun onSwitchClicked(isChosen: Boolean) {
//        viewModelScope.launch {
//            if(isChosen) {
//                text.emit(arrayText[0])
//            } else {
//                text.emit(arrayText[1])
//            }
//        }
        if(isChosen) {
            text.value = arrayText[0]
        } else {
            text.value = arrayText[1]
        }
    }

    fun onBtnNextClicked() {
        //TODO: рассказать что можно даже на других потоках
        viewModelScope.launch(Dispatchers.IO) {
//            //TODO: можно даже ассинхронные переходы делать
            delay(3_000)
//            Thread.sleep(3_000)
            navigation.emit(Navigation.TO_SECOND_FRAGMENT)
        }
    }

}

private val arrayText = arrayOf("Привет", "как дела?")
enum class Navigation {
    TO_SECOND_FRAGMENT
}