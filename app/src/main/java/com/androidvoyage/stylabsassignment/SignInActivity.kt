package com.androidvoyage.stylabsassignment

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View

import com.androidvoyage.stylabsassignment.Presenter.GoogleSignInPresenter
import com.androidvoyage.stylabsassignment.interactors.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.SignInButton

class SignInActivity : TransitionActivity(), View.OnClickListener, SignInView {
    private val TAG = "SignInActivity"
    private lateinit var signInGooglePresenter: GoogleSignInPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        setSupportActionBar(findViewById<View>(R.id.toolbar) as Toolbar)

        //Google+
        val signInButton = findViewById<View>(R.id.sign_in_button) as SignInButton
        signInButton.setSize(SignInButton.SIZE_STANDARD)
        signInButton.setOnClickListener(this)
        signInGooglePresenter = GoogleSignIn(sigInView = this@SignInActivity)

    }

    private fun updateUI(account: GoogleSignInAccount?) {
        if (account != null) {
            startActivity(Intent(this, RecycleActivity::class.java)
                    .putExtra(this.getString(R.string.first_name), account.givenName)
                    .putExtra(this.getString(R.string.last_name), account.familyName))
            finish()
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.sign_in_button -> signInGooglePresenter!!.signIn(this@SignInActivity)
        }// ...
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        signInGooglePresenter!!.onActivityResult(this@SignInActivity, requestCode, resultCode, data!!)
    }

    override fun gotoMain(account: GoogleSignInAccount) {
        updateUI(account)
    }

    companion object {

        private val RC_SIGN_IN = 1111
    }
}
