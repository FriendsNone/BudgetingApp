package com.middlename.budgetingapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.middlename.budgetingapp.entities.Budget
import com.middlename.budgetingapp.entities.Profile

@Database(entities = [Budget::class, Profile::class], version = 1)
abstract class BudgetDatabase : RoomDatabase() {
    abstract fun getBudgetDao(): BudgetDao
    abstract fun getProfileDao(): ProfileDao
}