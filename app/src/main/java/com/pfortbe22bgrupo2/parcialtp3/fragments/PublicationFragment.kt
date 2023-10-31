package com.pfortbe22bgrupo2.parcialtp3.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pfortbe22bgrupo2.parcialtp3.viewmodels.PublicationViewModel
import com.pfortbe22bgrupo2.parcialtp3.R

class PublicationFragment : Fragment() {

    companion object {
        fun newInstance() = PublicationFragment()
    }

    private lateinit var viewModel: PublicationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_publication, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PublicationViewModel::class.java)
        // TODO: Use the ViewModel
    }

}