package com.example.practice.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.practice.R
import com.example.practice.databinding.ActivitySignupBinding
import com.example.practice.model.PersonDatabase
import com.example.practice.model.PersonRepository
import com.example.practice.viewmodel.SignupViewModel
import com.example.practice.viewmodelfactory.SignupViewModelFactory

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var viewModel: SignupViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup)
        val dao = PersonDatabase.getInstance(application).personDao()
        val repository = PersonRepository(dao)

        viewModel = ViewModelProvider(this,SignupViewModelFactory(repository))[SignupViewModel::class.java]

        binding.signUpViewModel = viewModel
        binding.lifecycleOwner= this
        viewModel.onSignUpResponse.observe(this, Observer {
            if (it) {
                finish()
                startActivity(Intent(this, MainActivity::class.java))
            }
        })
        viewModel.onLoginResponse.observe(this, Observer {
            if(it){
                finish()
                startActivity(Intent(this,LoginActivity::class.java))
            }
        })
    }

}