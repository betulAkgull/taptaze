package com.example.taptaze.ui.main.search

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.taptaze.R
import com.example.taptaze.common.invisible
import com.example.taptaze.common.viewBinding
import com.example.taptaze.common.visible
import com.example.taptaze.data.model.ProductUI
import com.example.taptaze.data.model.request.AddToCartRequest
import com.example.taptaze.databinding.FragmentSearchBinding
import com.example.taptaze.ui.login.AuthViewModel
import com.example.taptaze.ui.main.home.HomeState
import com.example.taptaze.ui.main.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search),
    SearchProductsAdapter.SearchProductListener {

    private val binding by viewBinding(FragmentSearchBinding::bind)

    private val viewModel by viewModels<SearchViewModel>()

    private val viewModelHome by viewModels<HomeViewModel>()

    private val viewModelFirebase by viewModels<AuthViewModel>()

    private val searchProductsAdapter by lazy { SearchProductsAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            rvSearch.adapter = searchProductsAdapter

            searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    viewModel.getProductSearch(p0.toString())
                    return false
                }
            })
        }
        observeData()
    }

    private fun observeData() {
        viewModel.searchState.observe(viewLifecycleOwner) { state ->

            when (state) {

                SearchState.Loading -> {
                    binding.progressBar2.visible()
                }

                is SearchState.Data -> {
                    searchProductsAdapter.submitList(state.products)
                    binding.progressBar2.invisible()
                }

                is SearchState.Error -> {
                    Toast.makeText(
                        requireContext(),
                        state.throwable.message.orEmpty(),
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.progressBar2.invisible()
                }
            }

        }

    }

    private fun cartObserver() {

        viewModelHome.homeState.observe(viewLifecycleOwner) {
            if (it is HomeState.PostResponse) {
                binding.progressBar2.invisible()
                Toast.makeText(
                    requireContext(),
                    it.crud.message,
                    Toast.LENGTH_SHORT
                ).show()
            } else if (it is HomeState.Error) {
                binding.progressBar2.invisible()
                Toast.makeText(
                    requireContext(),
                    it.throwable.message.orEmpty(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onProductClick(id: Int) {
        val action = SearchFragmentDirections.searchToDetail(id)
        findNavController().navigate(action)
    }

    override fun onCartButtonClick(id: Int) {
        val addToCartRequest = AddToCartRequest(viewModelFirebase.currentUser!!.uid, id)
        viewModelHome.addToCart(addToCartRequest)
        cartObserver()
    }

    override fun onFavButtonClick(product: ProductUI) {
        viewModel.addToFavorites(product)
    }
}