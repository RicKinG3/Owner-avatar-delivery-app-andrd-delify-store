package com.swmpire.delifyit.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor() : ViewModel() {
    private val _liveData: MutableLiveData<String> = MutableLiveData("")
    val liveData: LiveData<String> get() = _liveData

    fun setText(str: String) {
        _liveData.value = str
    }
}
