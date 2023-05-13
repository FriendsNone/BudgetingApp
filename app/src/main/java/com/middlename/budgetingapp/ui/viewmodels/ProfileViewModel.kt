package com.middlename.budgetingapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.middlename.budgetingapp.entities.Profile
import com.middlename.budgetingapp.repositories.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(val profileRepository: ProfileRepository) : ViewModel() {
    val profileLiveData: LiveData<List<Profile>> = profileRepository.getProfile()

    fun insertProfileData(profile: Profile) = viewModelScope.launch {
        profileRepository.insertProfileData(profile)
    }

    fun updateCurrentBalance(revisedBalance: Float) = viewModelScope.launch {
        profileRepository.updateCurrentBalance(revisedBalance)
    }
}