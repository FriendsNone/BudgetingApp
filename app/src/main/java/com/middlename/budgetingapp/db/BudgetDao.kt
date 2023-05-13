package com.middlename.budgetingapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.middlename.budgetingapp.entities.Budget

@Dao
interface BudgetDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBudget(budget: Budget)

    @Query("SELECT * FROM budget ORDER BY id DESC")
    fun getAllData(): LiveData<List<Budget>>

    @Query("UPDATE budget SET amount = :amount, purpose = :purpose WHERE id = :id")
    suspend fun updateBudget(amount: Float, purpose: String, id: Int)

    @Delete
    suspend fun deleteEntry(budget: Budget)

    @Query("SELECT SUM(amount) FROM budget WHERE creditOrDebit = 'Credit'")
    fun getTotalCredit(): LiveData<Float>

    @Query("SELECT SUM(amount) FROM budget WHERE creditOrDebit = 'Debit'")
    fun getTotalSpending(): LiveData<Float>

    @Query("SELECT * FROM budget WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    suspend fun getReportsBetweenDates(startDate: Long, endDate: Long): List<Budget>
}