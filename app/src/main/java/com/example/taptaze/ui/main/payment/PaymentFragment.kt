package com.example.taptaze.ui.main.payment

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.taptaze.R
import com.example.taptaze.common.viewBinding
import com.example.taptaze.databinding.FragmentPaymentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentFragment : Fragment(R.layout.fragment_payment) {

    private val binding by viewBinding(FragmentPaymentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }

        getData()

    }


    private fun getData() {


        val monthList = listOf(
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
        )
        var selectedMonth = ""

        val yearList = listOf(
            2023,
            2024,
            2023,
            2025,
            2026,
            2027,
            2028,
            2029,
            2030,
            2031,
            2032,
            2033,
            2034,
            2035,
            2036,
            2037,
            2038
        )

        var selectedYear = 0

        val yearAdapter = ArrayAdapter(
            requireContext(),
            com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
            yearList
        )


        val monthAdapter = ArrayAdapter(
            requireContext(),
            com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
            monthList
        )

        with(binding) {
            actMonth.setAdapter(monthAdapter)
            actYear.setAdapter(yearAdapter)

            actMonth.setOnItemClickListener { _, _, position, _ ->
                selectedMonth = monthList[position]
            }

            actYear.setOnItemClickListener { _, _, position, _ ->
                selectedYear = yearList[position]
            }

            buttonPayment.setOnClickListener {
                val owner = etCardName.text.toString()
                val number = etCardNumber.text.toString()
                val cvv = etCvv.text.toString()
                val addressName = etCardAdressName.text.toString()
                val addressDesc = etCardAdress.text.toString()

                if (checkInfos(
                        owner,
                        number,
                        selectedMonth,
                        selectedYear.toString(),
                        cvv,
                        addressName,
                        addressDesc
                    )
                ) {
                    Toast.makeText(requireContext(), "Successful", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(PaymentFragmentDirections.paymentToPaymentSuccess())
                } else {
                    Toast.makeText(requireContext(), "Missing Fields !", Toast.LENGTH_SHORT).show()
                }

            }
        }

    }

    private fun checkInfos(
        owner: String,
        number: String,
        selectedMonth: String,
        selectedYear: String,
        cvv: String,
        addressName: String,
        addressDesc: String
    ): Boolean {

        val checkInfos = when {
            owner.isNullOrEmpty() -> false
            number.isNullOrEmpty() -> false
            selectedMonth.isNullOrEmpty() -> false
            selectedYear.isNullOrEmpty() -> false
            cvv.isNullOrEmpty() -> false
            addressName.isNullOrEmpty() -> false
            addressDesc.isNullOrEmpty() -> false
            else -> true
        }
        return checkInfos
    }


}