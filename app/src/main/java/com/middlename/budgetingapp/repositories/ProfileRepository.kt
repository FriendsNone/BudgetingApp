package com.middlename.budgetingapp.repositories

import com.middlename.budgetingapp.db.ProfileDao
import com.middlename.budgetingapp.entities.Profile
import javax.inject.Inject

class ProfileRepository @Inject constructor(val profileDao: ProfileDao) {
    fun getProfile() = profileDao.getProfileData()

    suspend fun insertProfileData(profile: Profile) = profileDao.insertProfileData(profile)

    suspend fun updateCurrentBalance(revisedBalance: Float) = profileDao.updateCurrentBalance(revisedBalance)
}