package com.middlename.budgetingapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.middlename.budgetingapp.entities.Budget
import com.middlename.budgetingapp.repositories.BudgetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(val budgetRepository: BudgetRepository) : ViewModel() {
    val allBudgetEntries: LiveData<List<Budget>> = budgetRepository.getAllBudgetEntries()
    val totalCredit: LiveData<Float> = budgetRepository.getTotalCredit()
    val totalSpending: LiveData<Float> = budgetRepository.getTotalSpending()

    var _dateRangeBudgetEntries: MutableLiveData<List<Budget>> = MutableLiveData()
    val dateRangeBudgetEntries: LiveData<List<Budget>> = _dateRangeBudgetEntries

    fun insertBudget(budget: Budget) = viewModelScope.launch {
        budgetRepository.insertBudget(budget)
    }

    fun updateBudget(amount: Float, purpose: String, id: Int) = viewModelScope.launch {
        budgetRepository.updateBudget(amount, purpose, id)
    }

    fun deleteEntry(budget: Budget) = viewModelScope.launch {
        budgetRepository.deleteEntry(budget)
    }

    fun getReportsBetweenDates(startDate: Long, endDate: Long) = viewModelScope.launch {
        val response = budgetRepository.getBudgetEntriesBetweenDates(startDate, endDate)
        _dateRangeBudgetEntries.postValue(response)
    }
}