package com.pfortbe22bgrupo2.parcialtp3.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.pfortbe22bgrupo2.parcialtp3.R
import com.pfortbe22bgrupo2.parcialtp3.databinding.FragmentStartBinding
import com.pfortbe22bgrupo2.parcialtp3.viewmodels.StartViewModel

class StartFragment : Fragment() {

    companion object {
        fun newInstance() = StartFragment()
    }

    private lateinit var viewModel: StartViewModel
    private lateinit var binding: FragmentStartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStartBinding.inflate(inflater,container,false)
        return binding.root

    }

    

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(StartViewModel::class.java)
        // TODO: Use the ViewModel
    }

}