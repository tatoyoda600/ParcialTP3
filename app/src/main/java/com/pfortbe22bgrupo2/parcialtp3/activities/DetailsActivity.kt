package com.pfortbe22bgrupo2.parcialtp3.activities

import android.app.ActivityOptions
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.pfortbe22bgrupo2.parcialtp3.R
import com.pfortbe22bgrupo2.parcialtp3.adapters.ImageAdapter
import com.pfortbe22bgrupo2.parcialtp3.databinding.ActivityDetailsBinding
import com.pfortbe22bgrupo2.parcialtp3.databinding.ItemBottomSheetBinding
import com.pfortbe22bgrupo2.parcialtp3.models.Dog

class DetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailsBinding
    private lateinit var dog: Dog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        dog = DetailsActivityArgs.fromBundle(intent.extras!!).dog
        setupRecyclerView(binding.root)
        showBottomSheet(dog)
    }

    private fun showBottomSheet(dog: Dog) {
        val dialog = createBottomSheetDialog(dog)
        dialog.show()
    }

    private fun createBottomSheetDialog(dog: Dog): BottomSheetDialog {
        val view = layoutInflater.inflate(R.layout.item_bottom_sheet, null)
        val dialog = BottomSheetDialog(this)

        dialog.setContentView(view)

        val bottomSheetBinding = ItemBottomSheetBinding.bind(view)
        configureBottomSheetContent(bottomSheetBinding, dog)

        setOwnersPhoneCallAction(bottomSheetBinding)

        // Configurar BottomSheetBehavior
        val bottomSheetBehavior = BottomSheetBehavior.from(view.parent as View)
        bottomSheetBehavior.peekHeight = resources.getDimensionPixelSize(R.dimen.peek_height) // Altura parcial al inicio
        bottomSheetBehavior.isHideable = false // No ocultar el bottom sheet
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED // Iniciar colapsado

        return dialog
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
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun configureBottomSheetContent(binding: ItemBottomSheetBinding, dog: Dog) {
        binding.dogNameTextView.text = dog.name
        binding.dogAgeTextView.text = getString(R.string.years, dog.age.toString())
        binding.dogOwnerNameTextView.text = dog.owner
        binding.dogLocationTextView.text = dog.location
        binding.adoptionDescriptionTextView.text = dog.text
        binding.dogWeightTextView.text = "${dog.weight}kg"
        binding.dogSexTextView.text = dog.sex
    }

    private fun setupRecyclerView(view: View) {
        val recyclerView = binding.recycler

        val adapter = ImageAdapter(view.context, dog.image_urls)
        adapter.setOnItemClickListener { imageView: ImageView?, imagePath: String? ->
            val intent = Intent(view.context, ImageViewActivity::class.java).apply {
                putExtra("image", imagePath)
            }

            val options = ActivityOptions.makeSceneTransitionAnimation(
               this,
                imageView,
                "image"
            ).toBundle()

            startActivity(intent, options)
        }
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(false)
    }
}
