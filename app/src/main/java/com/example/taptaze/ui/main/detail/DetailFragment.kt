package com.example.taptaze.ui.main.detail

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.taptaze.R
import com.example.taptaze.common.invisible
import com.example.taptaze.common.loadImage
import com.example.taptaze.common.viewBinding
import com.example.taptaze.common.visible
import com.example.taptaze.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private lateinit var binding: FragmentDetailBinding

    private val args by navArgs<DetailFragmentArgs>()

    private val viewModel by viewModels<DetailViewModel>()

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

        with(binding){

            toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }

            btnAddToCart.setOnClickListener {

            }
        }
    }

    private fun observeData() = with(binding){
        viewModel.detailState.observe(viewLifecycleOwner) { state ->
            when (state) {

                DetailState.Loading -> {
                    binding.progressBarDetail.visible()
                }

                is DetailState.Data -> {
                    binding.progressBarDetail.invisible()

                    with(state) {
                        ivProduct.loadImage(product.imageOne)
                        tvProductTitle.text = product.title
                        tvProductDesc.text = product.description
                        ratingbar.rating = product.rate!!.toFloat()
                        if(product.saleState == true){
                            tvProductPrice.textSize = 16f
                            tvProductSalePrice.visible()
                            //bunlar düzeltilcek
                            tvProductSalePrice.text = "₺${product.salePrice}"
                            tvProductPrice.setText(Html.fromHtml("<s>₺${product.price}</s>"))
                        }else{
                            tvProductPrice.text = "₺${product.price}"
                        }
                    }
                }

                is DetailState.Error -> {
                    binding.progressBarDetail.invisible()
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