package com.example.practice.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.practice.model.PersonRepository
import com.example.practice.viewmodel.SignupViewModel

class SignupViewModelFactory(val repository: PersonRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SignupViewModel(repository)as T
    }
}