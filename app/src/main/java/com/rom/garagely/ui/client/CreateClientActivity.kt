package com.rom.garagely.ui.client

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import coil.imageLoader
import com.bumptech.glide.Glide
import com.rom.garagely.R
import com.rom.garagely.constant.IntentKey
import com.rom.garagely.databinding.ActivityCreateClientBinding
import com.rom.garagely.model.Client
import com.rom.garagely.model.User
import com.rom.garagely.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateClientActivity : BaseActivity<ActivityCreateClientBinding>() {

    private val viewModel: ClientViewModel by viewModels()

    private val client by lazy {
        Client()
    }

    private val firstName: String
        get() = binding.editTextFirstName.text.toString()

    private val lastName: String
        get() = binding.editTextLastName.text.toString()

    private val email: String
        get() = binding.editTextEmail.text.toString()

    private val address: String
        get() = binding.editTextAddress.text.toString()

    private val country: String
        get() = binding.editTextCountry.text.toString()

    private val comment: String
        get() = binding.editTextCountry.text.toString()

    private val phoneNumber: String
        get() = binding.editTextPhone.text.toString()

    private var imageUir: Uri? = null

    private val registerPhoto =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
            it?.let {
                imageUir = it
                setImageLoader(it)
            }
        }
    override val layoutResource: Int
        get() = R.layout.activity_create_client

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.imageViewClose.setOnClickListener {
            finish()
        }

        binding.textViewCreate.setOnClickListener {
            createClient()
        }

        binding.textViewAddPhoto.setOnClickListener {
            registerPhoto.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        observableCreateClient()
    }

    private fun setImageLoader(uri: Uri) {
        Glide.with(binding.root)
            .load(uri)
            .into(binding.imageViewGuest)
    }

    private fun createClient() {
        with(client) {
            first_name = firstName
            last_name = lastName
            phone_number = phoneNumber
            email = this@CreateClientActivity.email
            this.comment = this@CreateClientActivity.comment
            country = this@CreateClientActivity.country
            address = this@CreateClientActivity.address

        }
        viewModel.createClient(client, imageUir)
    }

    private fun observableCreateClient() {
        viewModel.crateClient.observe(this) {
            setResult(RESULT_OK, Intent().apply {
                putExtra(IntentKey.CLIENT, it)
            })
            finish()
        }
    }

    class Contract : ActivityResultContract<String?, Client?>() {
        override fun createIntent(context: Context, input: String?): Intent {
            return Intent(context, CreateClientActivity::class.java)
                .putExtra(IntentKey.CLIENT_ID, input)
        }

        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        override fun parseResult(resultCode: Int, intent: Intent?): Client? {
            if (resultCode == Activity.RESULT_OK) {
                return intent?.getParcelableExtra(IntentKey.CLIENT, Client::class.java) as Client
            }
            return null
        }
    }
}