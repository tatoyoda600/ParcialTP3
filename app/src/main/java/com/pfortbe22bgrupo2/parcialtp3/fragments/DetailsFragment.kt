package com.pfortbe22bgrupo2.parcialtp3.fragments

import android.app.ActivityOptions
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.pfortbe22bgrupo2.parcialtp3.R
import com.pfortbe22bgrupo2.parcialtp3.activities.ImageViewActivity
import com.pfortbe22bgrupo2.parcialtp3.adapters.ImageAdapter
import com.pfortbe22bgrupo2.parcialtp3.databinding.FragmentDetailsBinding
import com.pfortbe22bgrupo2.parcialtp3.databinding.ItemBottomSheetBinding
import com.pfortbe22bgrupo2.parcialtp3.models.Dog
import com.pfortbe22bgrupo2.parcialtp3.viewmodels.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {
        private lateinit var viewModel: DetailsViewModel
        private lateinit var binding: FragmentDetailsBinding
        private lateinit var dog: Dog
        private var bottomSheetDialog: BottomSheetDialog? = null

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            binding = FragmentDetailsBinding.inflate(inflater, container, false)
            viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
            dog = DetailsFragmentArgs.fromBundle(requireArguments()).dog
            showBottomSheet(dog)
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            setupRecyclerView()
        }

        private fun setupRecyclerView() {
            val recyclerView = binding.recycler

            val adapter = ImageAdapter(requireContext(), dog.image_urls)
            adapter.setOnItemClickListener { imageView: ImageView?, imagePath: String? ->
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
            recyclerView.adapter = adapter
            recyclerView.setHasFixedSize(true)
        }

        private fun hideBottomSheet() {
            bottomSheetDialog?.dismiss()
        }

        private fun showBottomSheet(dog: Dog) {
            bottomSheetDialog  = createBottomSheetDialog(dog)
            bottomSheetDialog?.show()
        }

        private fun createBottomSheetDialog(dog: Dog): BottomSheetDialog {
            val view = layoutInflater.inflate(R.layout.item_bottom_sheet, null)
            val dialog = BottomSheetDialog(binding.root.context)

            dialog.setContentView(view)

            val bottomSheetBinding = ItemBottomSheetBinding.bind(view)
            configureBottomSheetContent(bottomSheetBinding, dog)
            setAdoptButtonAction(bottomSheetBinding, dog)
            setOwnersPhoneCallAction(bottomSheetBinding)

            val bottomSheetBehavior = BottomSheetBehavior.from(view.parent as View)
            bottomSheetBehavior.peekHeight = resources.getDimensionPixelSize(R.dimen.peek_height)
            bottomSheetBehavior.isHideable = false
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

            return dialog
        }

        private fun setAdoptButtonAction(binding: ItemBottomSheetBinding, dog: Dog){
            binding.adoptButton.setOnClickListener {
                val builder = AlertDialog.Builder(binding.root.context)
                builder.setTitle(binding.root.context.getString(R.string.adoption_confirmation_modal_title))
                builder.setMessage(binding.root.context.getString(R.string.adoption_confirmation_modal_text))
                builder.setPositiveButton(R.string.yes) { dialog, which ->
                    Log.i("DetailsActivity", "Starting adoption process for dog: ${dog.name}")
                    //remuevo el elemento
                    //envio el dog a la lista de adoptados calculo
                   // viewModel.adoptFromFavorites(dog.id)
                    // favoritesViewModel.deleteFavorite(username, dog.id) //esto es así? se debería actualizar la lista de favoritos desde acá? chequear xd
                    hideBottomSheet()
                    goToAdoptedFragment()
                }
                builder.setNegativeButton(R.string.no) { dialog, which ->
                    Log.i("DetailsActivity", R.string.no.toString())
                }
                val dialog = builder.create()
                dialog.show()
            }
        }

    private fun setOwnersPhoneCallAction(binding: ItemBottomSheetBinding){
            val phoneBtn = binding.phonePicImageView
            phoneBtn.setOnClickListener {
                val phoneNumber = dog.phone
                if (!phoneNumber.isNullOrEmpty()) {
                    val intent = Intent(Intent.ACTION_DIAL)
                    intent.data = Uri.parse("tel:$phoneNumber")
                    startActivity(intent)
                } else {
                    val errorMessage = getString(R.string.error_message_phone_number_not_available)
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }

        private fun configureBottomSheetContent(binding: ItemBottomSheetBinding, dog: Dog) {
            binding.dogNameTextView.text = dog.name
            binding.dogLocationTextView.text = dog.location
            binding.dogAgeTextView.text = getString(R.string.years, dog.age.toString())
            binding.dogOwnerNameTextView.text = dog.owner
            binding.dogLocationTextView.text = dog.location
            binding.adoptionDescriptionTextView.text = dog.text
            binding.dogWeightTextView.text = "${dog.weight}kg"
            binding.dogSexTextView.text = dog.sex
        }

    private fun goToAdoptedFragment() {
        val fragment = AdoptedFragment()
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host, fragment)
        transaction.commit()
    }
}