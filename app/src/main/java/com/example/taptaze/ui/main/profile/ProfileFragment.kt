package com.example.taptaze.ui.main.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.taptaze.R
import com.example.taptaze.common.viewBinding
import com.example.taptaze.data.model.request.ClearCartRequest
import com.example.taptaze.databinding.FragmentProfileBinding
import com.example.taptaze.ui.login.AuthViewModel
import com.example.taptaze.ui.main.cart.CartFragmentDirections
import com.example.taptaze.ui.main.cart.CartViewModel
import com.example.taptaze.ui.main.favorite.FavoriteViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding by viewBinding(FragmentProfileBinding::bind)
    private val viewModel by viewModels<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }

            tvUserEmail.setText(
                viewModel.currentUser?.email.toString()
            )

            btnLogout.setOnClickListener {
                viewModel.logout()
                findNavController().navigate(ProfileFragmentDirections.profileToSplash())
            }
        }
    }
}