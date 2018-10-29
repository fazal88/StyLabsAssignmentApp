package com.androidvoyage.stylabsassignment.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View

import com.androidvoyage.stylabsassignment.Presenter.GoogleSignInPresenter
import com.androidvoyage.stylabsassignment.R
import com.androidvoyage.stylabsassignment.views.SignInView
import com.androidvoyage.stylabsassignment.interactors.GoogleSignInInteractor
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.SignInButton
import kotlinx.android.synthetic.main.content_sign_in.*

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
        signInGooglePresenter = GoogleSignInInteractor(signInView = this@SignInActivity)

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
            R.id.sign_in_button -> signInGooglePresenter.signIn(this@SignInActivity)
        }// ...
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        signInGooglePresenter.onActivityResult(this@SignInActivity, requestCode, resultCode, data!!)
    }

    override fun gotoMain(account: GoogleSignInAccount) {
        updateUI(account)
    }

    override fun showProgressBar(visible: Int) {
        pb_sign_in.visibility = visible
    }
}
