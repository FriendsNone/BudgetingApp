package com.middlename.budgetingapp.di

import android.content.Context
import androidx.room.Room
import com.middlename.budgetingapp.db.BudgetDatabase
import com.middlename.budgetingapp.util.Constatnts.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesBudgetDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        BudgetDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun providesProfileDao(db: BudgetDatabase) = db.getProfileDao()

    @Provides
    @Singleton
    fun providesBudgetDao(db: BudgetDatabase) = db.getBudgetDao()
}