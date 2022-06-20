package com.example.practice.viewmodel

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.practice.error.UserErrorModel
import com.example.practice.model.PersonRepository
import com.example.practice.model.User
import com.example.practice.utils.Valid
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignupViewModel(private val repository: PersonRepository) : ViewModel(){

    var firstName : MutableLiveData<String> = MutableLiveData()
    var lastName : MutableLiveData<String> = MutableLiveData()
    var email : MutableLiveData<String> = MutableLiveData()
    var phone : MutableLiveData<String> = MutableLiveData()
    var password : MutableLiveData<String> = MutableLiveData()
    var cPassword : MutableLiveData<String> = MutableLiveData()
    val onSignUpResponse : MutableLiveData<Boolean> = MutableLiveData()
    val onLoginResponse : MutableLiveData<Boolean> = MutableLiveData()
    val userError : MutableLiveData<UserErrorModel> = MutableLiveData()
    private lateinit var user: User

    init {
        onSignUpResponse.value = false
        onLoginResponse.value = false
        userError.value = UserErrorModel()
    }



    private fun validate() :Boolean{
        val userErrorMessage = UserErrorModel()
        val isValidFirstName = TextUtils.isEmpty(firstName.value)
        val isValidLastName = TextUtils.isEmpty(lastName.value)
        val isValidEmail = Valid.isValidEmail(email.value.toString())
        val isValidPhone = Valid.isValidPhone(phone.value.toString())
        val isValidPassword = Valid.isValidPassword(password.value.toString())
        val isValidConfirmPassword = Valid.isConfirmPassword(password.value.toString(),cPassword.value.toString())

        if (isValidFirstName){
            userErrorMessage.firstNameErrorMessage = "Field Cannot be Empty"
        }
        if(isValidLastName){
            userErrorMessage.lastNameErrorMessage = "Field Cannot be Empty"
        }
        if(!isValidEmail){
            userErrorMessage.emailErrorMessage = "Please Enter a valid Email"
        }
        if(!isValidPhone){
            userErrorMessage.phoneErrorMessage = "Please Enter a valid Moblie Number"
        }
        if(!isValidPassword){
            userErrorMessage.passwordErrorMessage = "Please Enter a Valid Passsword"
        }
        if(!isValidConfirmPassword){
            userErrorMessage.confirmPasswordErrorMessage = "PLease Confirm Password"
        }
        userError.value = userErrorMessage
        return !isValidFirstName && !isValidLastName && isValidEmail && isValidEmail && isValidPassword
                && isValidConfirmPassword
     }

    fun signupButton() {
        if (validate()) {
            insertUser()
            onSignUpResponse.value = true
        }
    }

    fun loginButton(){
        onLoginResponse.value = true
    }
    fun insertUser(){
        CoroutineScope(Dispatchers.IO).launch {
            user = User(0,firstName.value.toString().trim(),lastName.value.toString().trim(),email.value.toString().trim(),phone.value.toString()
                        ,password.value.toString()
            )
            repository.insert(user)

        }
    }
}

