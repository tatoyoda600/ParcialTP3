package com.pfortbe22bgrupo2.parcialtp3.activities

import android.app.ActivityOptions
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.pfortbe22bgrupo2.parcialtp3.R
import com.pfortbe22bgrupo2.parcialtp3.adapters.ImageAdapter
import com.pfortbe22bgrupo2.parcialtp3.databinding.ActivityDetailsBinding
import com.pfortbe22bgrupo2.parcialtp3.databinding.ItemBottomSheetBinding
import com.pfortbe22bgrupo2.parcialtp3.fragments.AdoptedFragment
import com.pfortbe22bgrupo2.parcialtp3.models.Dog
import com.pfortbe22bgrupo2.parcialtp3.viewmodels.DetailsViewModel
import com.pfortbe22bgrupo2.parcialtp3.viewmodels.FavoritesViewModel
import javax.inject.Inject

//class DetailsActivity @Inject constructor(private val favoritesViewModel: FavoritesViewModel, private val username: String) : AppCompatActivity() {
class DetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailsBinding
    private lateinit var dog: Dog
    //private lateinit var detailsViewModel: DetailsViewModel
    private lateinit var fragmentManager: FragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        //detailsViewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
        setContentView(binding.root)
        dog = DetailsActivityArgs.fromBundle(intent.extras!!).dog
        showBottomSheet(dog)
        fragmentManager = supportFragmentManager
    }

    override fun onStart() {
        super.onStart()
        setupRecyclerView(binding.root)
    }

    private fun setAdoptButtonAction(binding: ItemBottomSheetBinding, dog: Dog){
        binding.adoptButton.setOnClickListener {
            val builder = AlertDialog.Builder(binding.root.context)
            builder.setTitle(binding.root.context.getString(R.string.adoption_confirmation_modal_title))
            builder.setMessage(binding.root.context.getString(R.string.adoption_confirmation_modal_text))
            builder.setPositiveButton(R.string.yes) { dialog, which ->
                //remuevo el elemento
                //envio el dog a la lista de adoptados calculo
                //detailsViewModel.adoptFromFavorites(dog.id)
               // favoritesViewModel.deleteFavorite(username, dog.id) //esto es así? se debería actualizar la lista de favoritos desde acá? chequear xd
                Log.i("DetailsActivity", "Adopted dog: ${dog.name}")
                goToAdoptedFragment()
            }
            builder.setNegativeButton(R.string.no) { dialog, which ->
                Log.i("DetailsActivity", R.string.no.toString())
            }
            val dialog = builder.create()
            dialog.show()
        }
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
        setAdoptButtonAction(bottomSheetBinding, dog)
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

    private fun goToAdoptedFragment() {
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, AdoptedFragment())
        fragmentTransaction.commit()
    }
}
