package com.spotify.demo.ui.login

import android.app.ListActivity
import android.os.Bundle
import com.spotify.demo.R
import com.spotify.demo.extensions.click
import com.spotify.demo.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.spotify.sdk.android.authentication.AuthenticationResponse
import android.content.Intent
import com.spotify.demo.constants.ApplicationConstants
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginActivity : BaseActivity() {

    private val REQUEST_CODE = 1337
    private val REDIRECT_URI = "https://mydigipay.com/"

    val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin.click {
            loginToSpotify()
        }
    }

    private fun loginToSpotify() {

        val builder = AuthenticationRequest.Builder(
            ApplicationConstants.ClientId,
            AuthenticationResponse.Type.TOKEN,
            REDIRECT_URI
        )

        builder.setScopes(arrayOf("streaming"))
        val request = builder.build()

        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            val response = AuthenticationClient.getResponse(resultCode, intent)

            when (response.type) {
                // Response was successful and contains auth token
                AuthenticationResponse.Type.TOKEN -> {
                    viewModel.updateToken(response.accessToken)
                    startActivity(Intent(this, ListActivity::class.java))
                    finish()
                }

                // Auth flow returned an error
                AuthenticationResponse.Type.ERROR -> {
                    //Handle error
                }
                // Most likely auth flow was cancelled
                else -> {
                    // Handle other cases
                }
            }


        }
    }

}
