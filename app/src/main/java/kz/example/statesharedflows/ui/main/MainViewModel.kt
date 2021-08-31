package kz.example.statesharedflows.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.example.statesharedflows.ui.SingleEventLiveData

class MainViewModel : ViewModel() {

    //region LiveData
    //TODO: показать почему именно Single
    private val navigation: SingleEventLiveData<Navigation> = SingleEventLiveData()
    fun observeNavigation(): LiveData<Navigation> = navigation

    private val text: MutableLiveData<String> = MutableLiveData()
    fun observeText(): LiveData<String> = text
    //endregion

    fun onSwitchClicked(isChosen: Boolean) {
        if(isChosen) {
            text.value = arrayText[0]
        } else {
            text.value = arrayText[1]
        }
    }

    fun onBtnNextClicked() {
        navigation.value = Navigation.TO_SECOND_FRAGMENT
    }

}

private val arrayText = arrayOf("Привет", "как дела?")
enum class Navigation {
    TO_SECOND_FRAGMENT
}