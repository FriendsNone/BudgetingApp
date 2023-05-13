package com.middlename.budgetingapp.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.middlename.budgetingapp.R
import com.middlename.budgetingapp.databinding.FragmentReportsBinding
import com.middlename.budgetingapp.ui.adapter.ReportsAdapter
import com.middlename.budgetingapp.ui.viewmodels.BudgetViewModel
import com.middlename.budgetingapp.util.UtilityFunctions
import com.middlename.budgetingapp.util.UtilityFunctions.dateMillisToString
import com.middlename.budgetingapp.util.UtilityFunctions.getEndDate
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ReportsFragment : Fragment(R.layout.fragment_reports), ReportsAdapter.MyOnClickListener {
    lateinit var binding: FragmentReportsBinding
    private val budgetViewModel: BudgetViewModel by viewModels()
    private lateinit var reportsAdapter: ReportsAdapter
    private val dateRangeArray = arrayOf("Select Date Range", "1 Week", "1 Month", "6 Month", "Show All")
    private lateinit var startDate: String

    private fun initializeRecyclerView() {
        reportsAdapter = ReportsAdapter(this)
        binding.rcvReports.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = reportsAdapter
        }
    }

    private fun getAllEntries() {
        budgetViewModel.allBudgetEntries.observe(viewLifecycleOwner) {
            reportsAdapter.differ.submitList(it)
        }
    }

    private fun setStartDate(): String {
        val dateInMillis = Calendar.getInstance().timeInMillis
        return dateMillisToString(dateInMillis)
    }

    private fun setSpinnerValues() {
        val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, dateRangeArray)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.dateRangeReportSpinner.adapter = arrayAdapter
    }

    private fun getReportsBetweenDates(startDate: String, endDate: String) {
        val start = UtilityFunctions.dateStringToMillis(endDate)
        val end = UtilityFunctions.dateStringToMillis(startDate)

        budgetViewModel.getReportsBetweenDates(start, end)
        budgetViewModel.dateRangeBudgetEntries.observe(viewLifecycleOwner) {
            reportsAdapter.differ.submitList(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentReportsBinding.bind(view)
        activity?.title = "Spending Reports"
        startDate = setStartDate()

        initializeRecyclerView()

        setSpinnerValues()

        val itemTouchHelperCallback = object: ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true;
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.adapterPosition
                val budget = reportsAdapter.differ.currentList[pos]

                budgetViewModel.deleteEntry(budget)

                Snackbar.make(view, "Item Deleted", Snackbar.LENGTH_LONG).apply {
                    setAction("UNDO") {
                        budgetViewModel.insertBudget(budget)
                    }
                    show()
                }
            }

        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rcvReports)
        }

        getAllEntries()

        binding.statistics.setOnClickListener {
            val bottomSheet = StatisticsBottomSheetFragment()
            bottomSheet.show(requireActivity().supportFragmentManager, "BottomSheet")
        }

        binding.dateRangeReportSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(parent?.getItemAtPosition(position)) {
                    "1 Week" -> getReportsBetweenDates(startDate, getEndDate(7))
                    "1 Month" -> getReportsBetweenDates(startDate, getEndDate(30))
                    "6 Month" -> getReportsBetweenDates(startDate, getEndDate(180))
                    "Show All" -> getAllEntries()
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    override fun OnClick(position: Int) {
        val currentBudgetItem = reportsAdapter.differ.currentList[position]
        val bottomSheet = UpdateBudgetBottomSheetFragment(currentBudgetItem)
        bottomSheet.show(requireActivity().supportFragmentManager, "UpdateBudget")
    }
}