package com.middlename.budgetingapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.middlename.budgetingapp.R
import com.middlename.budgetingapp.databinding.FragmentCalenderViewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CalendarViewFragment : Fragment(R.layout.fragment_calender_view) {
    lateinit var binding: FragmentCalenderViewBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCalenderViewBinding.bind(view)
        setHasOptionsMenu(true)
        activity?.title = "Select Budget Entry Date"

        binding.calView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val selectedDate = "${dayOfMonth}/${month+1}/${year}"
            val action = CalendarViewFragmentDirections.actionCalendarViewFragmentToBudgetEntryFragment()
                .setSelectedDate(selectedDate)
            findNavController().navigate(action)
        }
    }
}