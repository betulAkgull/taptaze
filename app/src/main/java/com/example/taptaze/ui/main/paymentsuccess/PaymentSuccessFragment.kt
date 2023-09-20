package com.example.taptaze.ui.main.paymentsuccess

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.taptaze.R
import com.example.taptaze.common.invisible
import com.example.taptaze.common.visible
import com.example.taptaze.data.model.request.ClearCartRequest
import com.example.taptaze.databinding.FragmentPaymentSuccessBinding
import com.example.taptaze.ui.main.cart.CartState
import com.example.taptaze.ui.main.cart.CartViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentSuccessFragment : Fragment(R.layout.fragment_payment_success) {

    private lateinit var binding: FragmentPaymentSuccessBinding
    private val viewModel by viewModels<CartViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPaymentSuccessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val req = ClearCartRequest(FirebaseAuth.getInstance().currentUser!!.uid)
        viewModel.clearCart(req)

        observers()

        with(binding) {
            buttonContinue.setOnClickListener {
                findNavController().navigate(
                    R.id.homeFragment,
                    null,
                    NavOptions.Builder()
                        .setPopUpTo(R.id.cartFragment, true)
                        .build()
                )

            }
        }
    }

    private fun observers() {

        viewModel.cartState.observe(viewLifecycleOwner) { state ->

            when (state) {

                CartState.Loading -> {
                    binding.progressBar.visible()
                }

                is CartState.PostResponse -> {
                    binding.progressBar.visible()
                    Toast.makeText(
                        requireContext(),
                        state.crud.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is CartState.Error -> {
                    Toast.makeText(
                        requireContext(),
                        state.throwable.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.progressBar.invisible()
                }

                else -> {

                }
            }

        }
    }

}