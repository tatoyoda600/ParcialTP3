package com.pfortbe22bgrupo2.parcialtp3.fragments

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.pfortbe22bgrupo2.parcialtp3.R
import com.pfortbe22bgrupo2.parcialtp3.viewmodels.DetailsViewModel
import com.pfortbe22bgrupo2.parcialtp3.databinding.FragmentDetailsBinding
import com.pfortbe22bgrupo2.parcialtp3.databinding.ItemBottomSheetBinding
import com.pfortbe22bgrupo2.parcialtp3.models.Dog

class DetailsFragment : Fragment() {

    lateinit var binding: FragmentDetailsBinding
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
        setShowBottomSheetAction()
    }

    private fun setShowBottomSheetAction(){
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