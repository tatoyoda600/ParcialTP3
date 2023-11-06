package com.pfortbe22bgrupo2.parcialtp3.activities

import android.app.ActivityOptions
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.pfortbe22bgrupo2.parcialtp3.R
import com.pfortbe22bgrupo2.parcialtp3.adapters.ImageAdapter
import com.pfortbe22bgrupo2.parcialtp3.databinding.ActivityDetailsBinding
import com.pfortbe22bgrupo2.parcialtp3.databinding.ItemBottomSheetBinding
import com.pfortbe22bgrupo2.parcialtp3.models.Dog
import com.pfortbe22bgrupo2.parcialtp3.utilities.DatabaseHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailsBinding
    private lateinit var dog: Dog
    var phoneNumber: String? = null

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

        val bottomSheetBehavior = BottomSheetBehavior.from(view.parent as View)
        bottomSheetBehavior.peekHeight = resources.getDimensionPixelSize(R.dimen.peek_height)
        bottomSheetBehavior.isHideable = false
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

        return dialog
    }

    private fun setOwnersPhoneCallAction(binding: ItemBottomSheetBinding){
        val phoneBtn = binding.phonePicImageView
        phoneBtn.setOnClickListener {
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
