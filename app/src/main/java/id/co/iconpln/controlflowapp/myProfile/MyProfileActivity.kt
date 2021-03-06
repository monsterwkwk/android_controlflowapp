package id.co.iconpln.controlflowapp.myProfile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import id.co.iconpln.controlflowapp.R
import id.co.iconpln.controlflowapp.models.myProfile.ProfileLoginResponse
import id.co.iconpln.controlflowapp.models.myProfile.ProfileResponse
import id.co.iconpln.controlflowapp.myContact.ProfileUser
import id.co.iconpln.controlflowapp.myProfileLogin.MyProfileLoginActivity
import kotlinx.android.synthetic.main.activity_my_profile.*

class MyProfileActivity : AppCompatActivity(), View.OnClickListener {

    private var profileLoginResponse : ProfileLoginResponse? = null
    private lateinit var viewModel: MyProfileViewModel
    private lateinit var profileUserPreference: ProfileUserPreference
    private lateinit var profileUser: ProfileUser

    companion object{
        const val REQUEST_CODE = 200
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)

        initViewModel()
        setupActionBar()
        setOnClickListener()
        showExistingPreference()
    }

    private fun setupActionBar() {
        supportActionBar?.title = "My Profile"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE){
            if (resultCode == MyProfileLoginActivity.RESULT_CODE){
                profileLoginResponse = data?.getParcelableExtra(MyProfileLoginActivity.EXTRA_PROFILE_RESULT) as ProfileLoginResponse
                saveProfileUserPreference()
            }
        }
    }

    private fun saveProfileUserPreference() {
        if (profileLoginResponse != null){
            profileUser.userToken = profileLoginResponse?.token
            profileUserPreference.setProfileUser(profileUser)
            Toast.makeText(this, "Token Saved???", Toast.LENGTH_SHORT).show()
            showExistingPreference()
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(MyProfileViewModel::class.java)
    }

    private fun setOnClickListener() {
        btnProfileToLogin.setOnClickListener(this)
        btnProfileLogout.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnProfileToLogin -> {
                val loginIntent = Intent(this, MyProfileLoginActivity::class.java)
                startActivityForResult(loginIntent, REQUEST_CODE)
            }
            R.id.btnProfileLogout -> {
                profileUserPreference.removeProfileUser(profileUser)
                showLogoutProfile()
            }
        }
    }

    private fun showExistingPreference() {
        profileUserPreference = ProfileUserPreference(this)
        profileUser = profileUserPreference.getProfileUser()

        val token = profileUserPreference.getProfileUser().userToken

        if (!token.isNullOrEmpty()){
            initiateGetProfile(token)
        }else{
            showLogoutProfile()
        }
    }

    private fun initiateGetProfile(token: String) {
        pbProfileLoading.visibility = View.GONE
        llProfileContent.visibility = View.GONE
        fetchUserProfile(token)
    }

    private fun showLogoutProfile() {
        pbProfileLoading.visibility = View.GONE
        llProfileContent.visibility = View.VISIBLE
        tvProfileWarning.visibility = View.VISIBLE
        btnProfileToLogin.visibility = View.VISIBLE
        btnProfileLogout.visibility = View.GONE
        tvProfileId.text = resources.getString(R.string.profile_empty)
        tvProfileName.text = resources.getString(R.string.profile_empty)
        tvProfileEmail.text = resources.getString(R.string.profile_empty)
        tvProfileHandphone.text = resources.getString(R.string.profile_empty)
    }

    private fun fetchUserProfile(token: String){
        viewModel.getProfile(token).observe(this, Observer { profileResponse ->
            if (profileResponse != null ){
                showProfile(profileResponse)
            } else {
                Toast.makeText(this, "Failed to get Profile", Toast.LENGTH_SHORT).show()
                profileUserPreference.removeProfileUser(profileUser)
                showLogoutProfile()
            }
        })
    }

    private fun showProfile(profileResponse: ProfileResponse) {
        pbProfileLoading.visibility = View.GONE
        llProfileContent.visibility = View.VISIBLE
        tvProfileWarning.visibility = View.GONE
        btnProfileToLogin.visibility = View.GONE
        btnProfileLogout.visibility = View.VISIBLE

        tvProfileId.text = profileResponse.id.toString()
        tvProfileName.text = profileResponse.name
        tvProfileEmail.text = profileResponse.email
        tvProfileHandphone.text = profileResponse.phone
    }
}
