package com.example.taptaze.ui.main.detail

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.taptaze.R
import com.example.taptaze.common.invisible
import com.example.taptaze.common.loadImage
import com.example.taptaze.common.visible
import com.example.taptaze.data.model.request.AddToCartRequest
import com.example.taptaze.databinding.FragmentDetailBinding
import com.example.taptaze.ui.login.AuthViewModel
import com.example.taptaze.ui.main.home.HomeState
import com.example.taptaze.ui.main.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private lateinit var binding: FragmentDetailBinding

    private val args by navArgs<DetailFragmentArgs>()

    private val viewModel by viewModels<DetailViewModel>()

    private val viewModelHome by viewModels<HomeViewModel>()

    private val viewModelFirebase by viewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getProductDetail(args.id)
        observeData()

        with(binding) {

            toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }

            ivCart.setOnClickListener {
                findNavController().navigate(
                    R.id.cartFragment,
                    null,
                    findNavController().previousBackStackEntry?.destination?.id?.let { it1 ->
                        NavOptions.Builder()
                            .setPopUpTo(it1, true)
                            .build()
                    }
                )
            }

            btnAddToCart.setOnClickListener {
                val addToCartRequest =
                    AddToCartRequest(viewModelFirebase.currentUser!!.uid, args.id)
                viewModelHome.addToCart(addToCartRequest)
                viewModelHome.homeState.observe(viewLifecycleOwner) { state ->
                    if (state is HomeState.PostResponse) {
                        Toast.makeText(
                            requireContext(),
                            state.crud.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun observeData() = with(binding) {
        viewModel.detailState.observe(viewLifecycleOwner) { state ->
            when (state) {

                DetailState.Loading -> {
                    progressBarDetail.visible()
                }

                is DetailState.Data -> {
                    progressBarDetail.invisible()

                    with(state) {
                        ivProduct.loadImage(product.imageOne)
                        tvProductTitle.text = product.title
                        tvProductDesc.text = product.description
                        ratingbar.rating = product.rate!!.toFloat()
                        if (product.saleState == true) {
                            tvProductPrice.textSize = 16f
                            tvProductSalePrice.visible()
                            //bunlar düzeltilcek
                            tvProductSalePrice.text = "₺${product.salePrice}"
                            tvProductPrice.setText(Html.fromHtml("<s>₺${product.price}</s>"))
                        } else {
                            tvProductPrice.text = "₺${product.price}"
                        }
                    }
                }

                is DetailState.Error -> {
                    progressBarDetail.invisible()
                    Toast.makeText(
                        requireContext(),
                        state.throwable.message.orEmpty(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

        }
    }
}