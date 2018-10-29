package com.androidvoyage.stylabsassignment.interactors


import android.content.Intent
import android.view.View
import android.widget.Toast
import com.androidvoyage.stylabsassignment.Presenter.GoogleSignInPresenter
import com.androidvoyage.stylabsassignment.activities.SignInActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

/**
 * Created by Operator on 26.08.2016.
 */
class GoogleSignInInteractor(signInView: SignInActivity) : GoogleSignInPresenter {

    init {
        signInView.showProgressBar(View.GONE)
        val account = GoogleSignIn.getLastSignedInAccount(signInView)
        if (account != null) {
            signInView.gotoMain(account)
        }
    }

     override fun signIn(signInView: SignInActivity) {
         signInView.showProgressBar(View.VISIBLE)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(signInView, gso)

        val signInIntent = mGoogleSignInClient.signInIntent
        signInView.startActivityForResult(signInIntent, RC_SIGN_IN)
    }

     override fun onActivityResult(signInView: SignInActivity, requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (!result.isSuccess) {
                signInView.showProgressBar(View.GONE)
                Toast.makeText(signInView, "Something went wrong! Try again.", Toast.LENGTH_SHORT).show()
                return
            }
            handleSignInResult(result.signInAccount, signInView)
        }
    }

    private fun handleSignInResult(result: GoogleSignInAccount?, signInView: SignInActivity) {
        signInView.showProgressBar(View.GONE)
        signInView.gotoMain(result!!)
    }

    companion object {

        private val RC_SIGN_IN = 1111
    }
}
