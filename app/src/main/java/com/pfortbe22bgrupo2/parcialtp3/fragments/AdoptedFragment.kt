package com.pfortbe22bgrupo2.parcialtp3.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pfortbe22bgrupo2.parcialtp3.databinding.FragmentAdoptedBinding
import com.pfortbe22bgrupo2.parcialtp3.viewmodels.AdoptedViewModel

class AdoptedFragment : Fragment() {
    private lateinit var viewModel: AdoptedViewModel
    lateinit var binding : FragmentAdoptedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdoptedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AdoptedViewModel::class.java)
        // TODO: Use the ViewModel
    }

}