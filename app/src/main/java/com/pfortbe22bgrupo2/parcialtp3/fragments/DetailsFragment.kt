package com.pfortbe22bgrupo2.parcialtp3.fragments

import android.app.ActivityOptions
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.pfortbe22bgrupo2.parcialtp3.R
import com.pfortbe22bgrupo2.parcialtp3.activities.ImageViewActivity
import com.pfortbe22bgrupo2.parcialtp3.adapters.ImageAdapter
import com.pfortbe22bgrupo2.parcialtp3.databinding.FragmentDetailsBinding
import com.pfortbe22bgrupo2.parcialtp3.databinding.ItemBottomSheetBinding
import com.pfortbe22bgrupo2.parcialtp3.models.Dog

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private lateinit var dog: Dog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //val dog = DetailsFragmentArgs.fromBundle(requireArguments()).dog
        this.dog = createSampleDog()

        setupRecyclerView()
        setShowBottomSheetAction()
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.recycler

        //val array_imgs = dog.image_urls

        val array_imgs = arrayOf(
            "https://images.unsplash.com/photo-1692528131755-d4e366b2adf0?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwzNXx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=500&q=60",
            "https://images.unsplash.com/photo-1692862582645-3b6fd47b7513?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw0MXx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=500&q=60",
            "https://images.unsplash.com/photo-1692584927805-d4096552a5ba?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw0Nnx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=500&q=60",
            "https://images.unsplash.com/photo-1692854236272-cc49076a2629?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw1MXx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=500&q=60",
            "https://images.unsplash.com/photo-1681207751526-a091f2c6a538?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwyODF8fHxlbnwwfHx8fHw%3D&auto=format&fit=crop&w=500&q=60",
            "https://images.unsplash.com/photo-1692610365998-c628604f5d9f?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwyODZ8fHxlbnwwfHx8fHw%3D&auto=format&fit=crop&w=500&q=60"
        )


        val adapter = ImageAdapter(requireContext(), array_imgs)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
    }

    private fun startImageViewActivity(imagePath: String?, imageView: ImageView?) {
        val intent = Intent(requireContext(), ImageViewActivity::class.java).apply {
            putExtra("image", imagePath)
        }

        val options = ActivityOptions.makeSceneTransitionAnimation(
            requireActivity(),
            imageView,
            "image"
        ).toBundle()

        startActivity(intent, options)
    }

    private fun setShowBottomSheetAction() {
        val btnShowBottomSheet = binding.btnShowBottomSheet
        btnShowBottomSheet.setOnClickListener {
            showBottomSheet(dog)
        }
    }

    private fun showBottomSheet(dog: Dog) {
        val dialog = createBottomSheetDialog(dog)
        dialog.show()
    }

    private fun createSampleDog(): Dog {
        return Dog(
            name = "Rex",
            age = 3,
            location = "Recoleta, Buenos Aires",
            sex = "Male",
            weight = 15.5f,
            owner_username = "jm_sarmiento",
            owner = "Juan Martin Sarmiento",
            phone = "1123478540",
            text = "Rex is a friendly and energetic dog looking for a loving home. He enjoys long walks in the park and playing fetch. He's great with kids and other pets. If you're looking for a loyal companion, Rex might be the perfect addition to your family.",
            image_urls = arrayOf("https://static.fundacion-affinity.org/cdn/farfuture/PVbbIC-0M9y4fPbbCsdvAD8bcjjtbFc0NSP3lRwlWcE/mtime:1643275542/sites/default/files/los-10-sonidos-principales-del-perro.jpg", "https://www.eltiempo.com/files/image_640_428/uploads/2023/01/05/63b73981e5a99.jpeg", "https://www.65ymas.com/uploads/s1/10/30/25/5/bigstock-421234682_1_621x621.jpeg")
        )
    }

    private fun createBottomSheetDialog(dog: Dog): BottomSheetDialog {
        val view = layoutInflater.inflate(R.layout.item_bottom_sheet, null)
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(view)

        val bottomSheetBinding = ItemBottomSheetBinding.bind(view)
        configureBottomSheetContent(bottomSheetBinding, dog)

        setOwnersPhoneCallAction(bottomSheetBinding)

        val bottomSheetBehavior = BottomSheetBehavior.from(view.parent as View)
        bottomSheetBehavior.peekHeight = resources.getDimensionPixelSize(R.dimen.peek_height)
        bottomSheetBehavior.isHideable = true

        return dialog
    }

    private fun setOwnersPhoneCallAction(binding: ItemBottomSheetBinding){
        val phoneNumber = binding.phonePicImageView
        phoneNumber.setOnClickListener {
            val phoneNumber = dog.phone
            if (!phoneNumber.isNullOrEmpty()) {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:$phoneNumber")
                startActivity(intent)
            } else {
                val errorMessage = getString(R.string.error_message_phone_number_not_avaliable)
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun configureBottomSheetContent(binding: ItemBottomSheetBinding, dog: Dog) {
        binding.dogNameTextView.text = dog.name
        binding.dogLocationTextView.text = dog.location
        binding.dogAgeTextView.text = getString(R.string.años, dog.age.toString())
        binding.dogOwnerNameTextView.text = dog.owner
        binding.dogLocationTextView.text = dog.location
        binding.adoptionDescriptionTextView.text = dog.text
        binding.dogWeightTextView.text = "${dog.weight}kg"
        binding.dogSexTextView.text = dog.sex
    }

}