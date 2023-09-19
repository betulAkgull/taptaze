package com.example.taptaze.ui.main.cart

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.taptaze.R
import com.example.taptaze.common.invisible
import com.example.taptaze.common.visible
import com.example.taptaze.data.model.request.ClearCartRequest
import com.example.taptaze.data.model.request.DeleteFromCartRequest
import com.example.taptaze.databinding.FragmentCartBinding
import com.example.taptaze.ui.login.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment(R.layout.fragment_cart), CartAdapter.CartListener {

    private lateinit var binding: FragmentCartBinding
    private val cartAdapter by lazy { CartAdapter(this) }
    private val viewModelFirebase by viewModels<AuthViewModel>()
    private val viewModel by viewModels<CartViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()

        with(binding) {

            rvCart.adapter = cartAdapter

            ivDelete.setOnClickListener {

                val alertDialog = AlertDialog.Builder(requireContext())

                alertDialog.setTitle("Deleting Cart")
                alertDialog.setMessage("Do you want to delete all items ?")
                alertDialog.setPositiveButton("Yes") { _, _ ->
                    val clearCartRequest = ClearCartRequest(viewModelFirebase.currentUser!!.uid)
                    viewModel.clearCart(clearCartRequest)
                }
                alertDialog.setNegativeButton("No") { _, _ ->
                    alertDialog.setCancelable(true)
                }
                alertDialog.show()
            }

            buttonOrder.setOnClickListener {
                findNavController().navigate(CartFragmentDirections.cartToPayment())
            }

        }

        with(viewModel) {
            getCartProducts(viewModelFirebase.currentUser!!.uid)
            totalAmount.observe(viewLifecycleOwner) { total ->
                binding.tvTotal.text = String.format("$%.2f", total)
            }
        }

    }

    private fun initAdapter() {

    }


    private fun observeData() {
        viewModel.cartState.observe(viewLifecycleOwner) { state ->

            when (state) {

                CartState.Loading -> {
                    binding.progressBarCart.visible()
                }

                is CartState.CartList -> {
                    cartAdapter.submitList(state.products)
                    binding.progressBarCart.invisible()
                }

                is CartState.PostResponse -> {
                    binding.progressBarCart.invisible()
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
                    binding.progressBarCart.invisible()
                }

            }

        }
    }

    override fun onCartClick(id: Int) {
        val action = CartFragmentDirections.cartToDetail(id)
        findNavController().navigate(action)
    }

    override fun onDeleteItemClick(id: Int) {
        viewModel.deleteFromCart(id)
    }

    override fun onIncreaseItemClick(price: Double) {
        viewModel.increase(price)
    }

    override fun onDecreaseItemClick(price: Double) {
        viewModel.decrease(price)
    }


}