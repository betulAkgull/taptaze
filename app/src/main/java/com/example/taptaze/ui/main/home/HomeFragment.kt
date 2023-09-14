package com.example.taptaze.ui.main.home

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
import com.example.taptaze.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), ProductsAdapter.ProductListener {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>()
    private val productsAdapter by lazy { ProductsAdapter(this) }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvProducts.adapter = productsAdapter

        viewModel.getAllProducts()
        observeData()
    }

    private fun observeData() {

        viewModel.homeState.observe(viewLifecycleOwner) { state ->
            when (state) {
                HomeState.Loading -> {
                    binding.progressBarHome.visible()
                }

                is HomeState.Data -> {
                    binding.progressBarHome.invisible()
                    productsAdapter.submitList(state.products)
                }

                is HomeState.Error -> {
                    binding.progressBarHome.invisible()
                    Toast.makeText(
                        requireContext(),
                        state.throwable.message.orEmpty(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onProductClick(id: Int) {
        val direction = HomeFragmentDirections.homeToDetail(id)
        findNavController().navigate(direction)
    }


}