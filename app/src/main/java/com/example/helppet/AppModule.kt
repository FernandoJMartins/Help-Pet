package com.example.helppet

import com.example.helppet.clientservice.OccurrenceDao
import com.example.helppet.clientservice.UserDao
import com.example.helppet.data.repository.FirebaseOccurrenceDao
import com.example.helppet.data.repository.FirebaseUserDao
import com.example.helppet.viewmodels.OccurrenceViewModel
import com.example.helppet.viewmodels.UserViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module{

    single<UserDao> { FirebaseUserDao() }
    single<OccurrenceDao> { FirebaseOccurrenceDao() }

    viewModel {
        UserViewModel(get())
    }
    
    viewModel {
        OccurrenceViewModel(get())
    }


}