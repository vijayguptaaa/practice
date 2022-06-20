package com.example.practice.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.practice.R
import com.example.practice.databinding.ActivityLoginBinding
import com.example.practice.model.PersonDatabase
import com.example.practice.model.PersonRepository
import com.example.practice.viewmodel.LoginViewModel
import com.example.practice.viewmodelfactory.LoginViewModelFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        var dao = PersonDatabase.getInstance(application).personDao()
        var repository = PersonRepository(dao)
        viewModel = ViewModelProvider(this, LoginViewModelFactory(repository))[LoginViewModel::class.java]
        binding.loginViewModel = viewModel
        binding.lifecycleOwner =this

        viewModel.onSignResponse.observe(this, Observer {
            if(it){
                startActivity(Intent(this,SignupActivity::class.java))
            }
        })
        viewModel.liveUser.observe(this, Observer {
            if(it!=null){
                startActivity(Intent(this,MainActivity::class.java))
            }
            else{
                Toast.makeText(this,"Email and Password not matched",Toast.LENGTH_SHORT).show()
            }
        })
    }


}