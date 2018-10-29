package com.androidvoyage.stylabsassignment.views

import com.google.android.gms.auth.api.signin.GoogleSignInAccount

interface SignInView {
    fun gotoMain(account: GoogleSignInAccount)
    fun showProgressBar(visible : Int)
}