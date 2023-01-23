package com.example.chatcompose.data

class UserRepository(
    val userData: User
) {
    fun getUserName() = userData.name
    fun getUserProfile() = userData.profileImage
}

/*
interface UserRepository {
    val userName: String
    val userProfile: String
}*/
