package id.co.iconpln.controlflowapp.myProfileLogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import id.co.iconpln.controlflowapp.R
import id.co.iconpln.controlflowapp.myProfileRegister.MyProfileRegisterActivity
import kotlinx.android.synthetic.main.activity_my_profile_login.*

class MyProfileLoginActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile_login)

        clickListener()
    }

    private fun clickListener() {
        btnProfileLogin.setOnClickListener(this)
        tvProfileLoginRegister.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.btnProfileLogin -> {
                Toast.makeText(this, "Login", Toast.LENGTH_SHORT).show()
            }
            R.id.tvProfileLoginRegister -> {
                startActivity(Intent(this, MyProfileRegisterActivity::class.java) )
            }
        }
    }
}