package com.pfortbe22bgrupo2.parcialtp3.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pfortbe22bgrupo2.parcialtp3.R
import com.pfortbe22bgrupo2.parcialtp3.viewmodels.AdoptedViewModel

class AdoptedFragment : Fragment() {

    companion object {
        fun newInstance() = AdoptedFragment()
    }

    private lateinit var viewModel: AdoptedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_adopted, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AdoptedViewModel::class.java)
        // TODO: Use the ViewModel
    }

}