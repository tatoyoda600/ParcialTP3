package com.pfortbe22bgrupo2.parcialtp3.fragments

import android.app.ActivityOptions
import android.content.Context
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
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.pfortbe22bgrupo2.parcialtp3.R
import com.pfortbe22bgrupo2.parcialtp3.activities.ImageViewActivity
import com.pfortbe22bgrupo2.parcialtp3.adapters.ImageAdapter
import com.pfortbe22bgrupo2.parcialtp3.databinding.FragmentDetailsBinding
import com.pfortbe22bgrupo2.parcialtp3.databinding.ItemBottomSheetBinding
import com.pfortbe22bgrupo2.parcialtp3.models.Dog
import com.pfortbe22bgrupo2.parcialtp3.utilities.DatabaseHandler
import com.pfortbe22bgrupo2.parcialtp3.viewmodels.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private lateinit var viewModel: DetailsViewModel
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var dog: Dog
    private var bottomSheetDialog: BottomSheetDialog? = null
    var phoneNumber: String? = null
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
        setupRecyclerView(binding.root)
    }

    private fun setupRecyclerView(view: View) {
        val recyclerView = binding.recycler

        val adapter = ImageAdapter(view.context, dog.image_urls)
        adapter.setOnItemClickListener { imageView: ImageView?, imagePath: String? ->
            val intent = Intent(view.context, ImageViewActivity::class.java).apply {
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
        recyclerView.setHasFixedSize(false)
    }

    private fun hideBottomSheet() {
        bottomSheetDialog?.dismiss()
    }

    private fun showBottomSheet(dog: Dog) {
        bottomSheetDialog = createBottomSheetDialog(dog)
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

    private fun setAdoptButtonAction(binding: ItemBottomSheetBinding, dog: Dog) {
        binding.adoptButton.setOnClickListener {
            val builder = AlertDialog.Builder(binding.root.context)
            builder.setTitle(binding.root.context.getString(R.string.adoption_confirmation_modal_title))
            builder.setMessage(binding.root.context.getString(R.string.adoption_confirmation_modal_text))
            builder.setPositiveButton(R.string.yes) { dialog, which ->
                Log.i("DetailsFragment", "Starting adoption process for dog: ${dog.name}")
                hideBottomSheet()
                val pref = requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE)
                val userName = pref.getString("userName","").toString()
                viewModel.adoptFromFavorites(dog, userName)
                goToAdoptedFragment()
            }
            builder.setNegativeButton(R.string.no) { dialog, which ->
                Log.i("DetailsActivity", R.string.no.toString())
            }
            val dialog = builder.create()
            dialog.show()
        }
    }

    private fun setOwnersPhoneCallAction(binding: ItemBottomSheetBinding) {
        val phoneBtn = binding.phonePicImageView
        phoneBtn.setOnClickListener {
            if (!phoneNumber.isNullOrEmpty()) {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:$phoneNumber")
                startActivity(intent)
            } else {
                val errorMessage = getString(R.string.error_message_phone_number_not_available)
                Toast.makeText(binding.root.context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun configureBottomSheetContent(binding: ItemBottomSheetBinding, dog: Dog) {
        binding.dogNameTextView.text = dog.name
        binding.dogAgeTextView.text = getString(R.string.years, dog.age.toString())
        binding.dogLocationTextView.text = dog.location
        binding.adoptionDescriptionTextView.text = dog.text
        binding.dogWeightTextView.text = "${dog.weight}kg"
        binding.dogSexTextView.text = dog.sex

        val databaseHandler = DatabaseHandler(binding.root.context)
        CoroutineScope(Dispatchers.IO).launch {
            val user = databaseHandler.getUserByUsername(dog.owner_username)
            if (user != null) {
                // Glide se tiene que llamar desde el main thread si o si
                phoneNumber = user.phone
                binding.dogOwnerNameTextView.text = user.name
                CoroutineScope(Dispatchers.Main).launch {
                    Glide.with(binding.root.context)
                        .load(user.image_url)
                        .into(binding.profileUserPicImageView)
                }
            }
        }
    }

    private fun goToAdoptedFragment() {
        val fragment = AdoptedFragment()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host, fragment)
            .commit()
    }

}