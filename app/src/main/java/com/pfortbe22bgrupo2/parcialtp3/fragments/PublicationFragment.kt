package com.pfortbe22bgrupo2.parcialtp3.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pfortbe22bgrupo2.parcialtp3.viewmodels.PublicationViewModel
import com.pfortbe22bgrupo2.parcialtp3.databinding.FragmentPublicationBinding

class PublicationFragment : Fragment() {
    private lateinit var viewModel: PublicationViewModel
    lateinit var binding : FragmentPublicationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPublicationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PublicationViewModel::class.java)
        // TODO: Use the ViewModel
    }

}