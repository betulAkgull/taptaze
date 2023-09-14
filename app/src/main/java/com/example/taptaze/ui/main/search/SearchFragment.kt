package com.example.taptaze.ui.main.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.taptaze.R
import com.example.taptaze.common.viewBinding
import com.example.taptaze.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private val binding by viewBinding(FragmentSearchBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

        }
    }
}