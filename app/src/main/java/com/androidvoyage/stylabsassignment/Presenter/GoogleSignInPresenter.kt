package com.androidvoyage.stylabsassignment.Presenter

import android.content.Intent
import com.androidvoyage.stylabsassignment.SignInActivity


interface GoogleSignInPresenter {
    fun signIn(signInView: SignInActivity)

    fun onActivityResult(signInView: SignInActivity, requestCode: Int, resultCode: Int, data: Intent)
}
