package com.androidvoyage.stylabsassignment.interactors


import android.content.Intent
import android.widget.Toast
import com.androidvoyage.stylabsassignment.Presenter.GoogleSignInPresenter
import com.androidvoyage.stylabsassignment.SignInActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.config.GservicesValue.init

/**
 * Created by Operator on 26.08.2016.
 */
class GoogleSignIn(sigInView: SignInActivity) : GoogleSignInPresenter {

    init {
        val account = com.google.android.gms.auth.api.signin.GoogleSignIn.getLastSignedInAccount(sigInView)
        if (account != null) {
            sigInView.gotoMain(account!!)
        }
    }

     override fun signIn(sigInView: SignInActivity) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

        val mGoogleSignInClient = com.google.android.gms.auth.api.signin.GoogleSignIn.getClient(sigInView, gso)

        val signInIntent = mGoogleSignInClient.signInIntent
        sigInView.startActivityForResult(signInIntent, RC_SIGN_IN)
    }

     override fun onActivityResult(sigInView: SignInActivity, requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (!result.isSuccess) {
                Toast.makeText(sigInView, "Something went wrong! Try again.", Toast.LENGTH_SHORT).show()
                return
            }
            handleSignInResult(result.signInAccount, sigInView)
        }
    }

    private fun handleSignInResult(result: GoogleSignInAccount?, signInView: SignInActivity) {
        signInView.gotoMain(result!!)
    }

    companion object {

        private val RC_SIGN_IN = 1111
    }
}
