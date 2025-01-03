package com.example.recordkeeper.cycling

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.recordkeeper.databinding.FragmentCyclingBinding

class CyclingFragment : Fragment() {

    private lateinit var binding: FragmentCyclingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCyclingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        displayRecords()
    }

    private fun displayRecords() {
        val cyclingPreferences =
            requireContext().getSharedPreferences("Cycling", Context.MODE_PRIVATE)
        binding.textViewLongestRideValue.text =
            cyclingPreferences?.getString("Longest Ride record", null)
        binding.textViewLongestRideDate.text =
            cyclingPreferences?.getString("Longest Ride date", null)
        binding.textViewBiggestClimbValue.text =
            cyclingPreferences?.getString("Biggest Climb record", null)
        binding.textViewBiggestClimbDate.text =
            cyclingPreferences?.getString("Biggest Climb date", null)
        binding.textViewBestAveSpeedValue.text =
            cyclingPreferences?.getString("Best Average Speed record", null)
        binding.textViewBestAveSpeedDate.text =
            cyclingPreferences?.getString("Best Average Speed date", null)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.containerLongestRide.setOnClickListener { launchEditCyclingRecordScreen("Longest Ride") }
        binding.containerBiggestClimb.setOnClickListener { launchEditCyclingRecordScreen("Biggest Climb") }
        binding.containerBestAveSpeed.setOnClickListener { launchEditCyclingRecordScreen("Best Average Speed") }
    }

    private fun launchEditCyclingRecordScreen(distance: String) {
        val intent = Intent(context, EditCyclingRecordActivity::class.java)
        intent.putExtra("Distance", distance)
        intent.putExtra("Record", "$distance record")
        intent.putExtra("Date", "$distance date")

        startActivity(intent)
    }
}