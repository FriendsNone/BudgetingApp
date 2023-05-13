package com.middlename.budgetingapp.repositories

import com.middlename.budgetingapp.db.BudgetDao
import com.middlename.budgetingapp.entities.Budget
import javax.inject.Inject

class BudgetRepository @Inject constructor(val budgetDao: BudgetDao) {
    suspend fun insertBudget(budget: Budget) = budgetDao.insertBudget(budget)

    fun getAllBudgetEntries() = budgetDao.getAllData()

    suspend fun getBudgetEntriesBetweenDates(startDate: Long, endDate: Long) = budgetDao.getReportsBetweenDates(startDate, endDate)

    suspend fun updateBudget(amount: Float, purpose: String, id: Int) = budgetDao.updateBudget(amount, purpose, id)

    suspend fun deleteEntry(budget: Budget) = budgetDao.deleteEntry(budget)

    fun getTotalCredit() = budgetDao.getTotalCredit()
    fun getTotalSpending() = budgetDao.getTotalSpending()
}
