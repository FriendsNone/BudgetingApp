package com.middlename.budgetingapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.middlename.budgetingapp.R
import com.middlename.budgetingapp.databinding.StatasticsBottomSheetBinding
import com.middlename.budgetingapp.ui.viewmodels.BudgetViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatisticsBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: StatasticsBottomSheetBinding
    private val budgetViewModel: BudgetViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.statastics_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = StatasticsBottomSheetBinding.bind(view)

        budgetViewModel.totalCredit.observe(viewLifecycleOwner) {
            binding.totalCredit.text = it.toString()
        }

        budgetViewModel.totalSpending.observe(viewLifecycleOwner) {
            binding.totalSpending.text = (-1 * it).toString()
        }
    }
}