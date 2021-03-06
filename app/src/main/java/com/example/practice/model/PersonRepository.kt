package com.example.practice.model

class PersonRepository(private val dao: PersonDao){

    suspend fun insert(user: User){
        dao.insertPerson(user)
    }

    fun getPerson(email:String,password:String) : User{
        return dao.getPerson(email,password)
    }
}