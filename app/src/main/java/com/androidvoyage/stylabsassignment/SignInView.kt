package com.androidvoyage.stylabsassignment

import com.google.android.gms.auth.api.signin.GoogleSignInAccount

interface SignInView {
    fun gotoMain(account: GoogleSignInAccount)
}