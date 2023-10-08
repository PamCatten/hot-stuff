package com.example.hotstuffkotlin.ui.items

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ItemsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is the items Fragment"
    }
    val text: LiveData<String> = _text
}