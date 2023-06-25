package com.rom.garagely.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.rom.garagely.R
import com.rom.garagely.constant.IntentKey
import com.rom.garagely.databinding.ActivityCreateClientBinding
import com.rom.garagely.model.User
import com.rom.garagely.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateClientActivity : BaseActivity<ActivityCreateClientBinding>() {

    override val layoutResource: Int
        get() = R.layout.activity_create_client

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.imageViewClose.setOnClickListener {
            finish()
        }
    }

    class Contract : ActivityResultContract<String?, User?>() {
        override fun createIntent(context: Context, input: String?): Intent {
            return Intent(context, CreateClientActivity::class.java)
                .putExtra(IntentKey.CLIENT_ID, input)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): User? {
            if (resultCode == Activity.RESULT_OK) {
                return intent?.getSerializableExtra(IntentKey.CLIENT) as User
            }
            return null
        }
    }
}