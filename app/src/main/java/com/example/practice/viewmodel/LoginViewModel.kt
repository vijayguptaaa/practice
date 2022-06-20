package com.example.practice.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.practice.error.UserErrorModel
import com.example.practice.model.PersonRepository
import com.example.practice.model.User
import com.example.practice.utils.Valid
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(private val repository: PersonRepository) :  ViewModel(){

    var email : MutableLiveData<String> = MutableLiveData()
    var password : MutableLiveData<String> = MutableLiveData()
    var liveUser : MutableLiveData<User> = MutableLiveData()
    var onLoginResponse:MutableLiveData<Boolean> = MutableLiveData()
    var onSignResponse:MutableLiveData<Boolean> = MutableLiveData()
    var userError : MutableLiveData<UserErrorModel> = MutableLiveData()

    init {
        onLoginResponse.value = false
        onSignResponse.value = false
        userError.value = UserErrorModel()
    }


    fun login(){
        if (validate()){
            getPerson(email.value.toString(),password.value.toString())
        }
    }
    private fun validate() : Boolean{
        val userErrorMessage = UserErrorModel()
        val isValidEmail = Valid.isValidEmail(email.value.toString())
        val isValidPassword = Valid.isValidPassword(password.value.toString())

        if (!isValidEmail){
            userErrorMessage.emailErrorMessage = "Invalid Email"
        }
        if (!isValidPassword){
            userErrorMessage.passwordErrorMessage= "Invalid Password"
        }
        userError.value = userErrorMessage

        return isValidEmail && isValidPassword
    }

    private fun getPerson(email : String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val user : User = repository.getPerson(email,password)
            withContext(Dispatchers.Main){
                liveUser.value = user
            }
        }
    }

    fun newAccount(){
        onSignResponse.value = true
    }
}