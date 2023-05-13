package com.middlename.budgetingapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.middlename.budgetingapp.R
import com.middlename.budgetingapp.databinding.UpdateBudgetBottomSheetBinding
import com.middlename.budgetingapp.entities.Budget
import com.middlename.budgetingapp.ui.viewmodels.BudgetViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateBudgetBottomSheetFragment (val currentBudgetItem: Budget) : BottomSheetDialogFragment() {
    lateinit var binding: UpdateBudgetBottomSheetBinding
    val budgetViewModel: BudgetViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.update_budget_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = UpdateBudgetBottomSheetBinding.bind(view)

        binding.updateAmount.setText(currentBudgetItem.amount.toString())
        binding.updatePurpose.setText(currentBudgetItem.purpose)
        binding.updateBudgetEntry.setOnClickListener {
            val updatedAmount = binding.updateAmount.text.toString()
            val updatedPurpose = binding.updatePurpose.text.toString()

            budgetViewModel.updateBudget(updatedAmount.toFloat(), updatedPurpose, currentBudgetItem.id!!)

            dismiss()
        }
    }
}