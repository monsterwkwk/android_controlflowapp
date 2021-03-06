package id.co.iconpln.controlflowapp.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import id.co.iconpln.controlflowapp.models.myProfile.*
import id.co.iconpln.controlflowapp.myProfile.MyProfileViewModel
import id.co.iconpln.controlflowapp.myProfileLogin.MyProfileLoginViewModel
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyProfileNetworkRepository {

    fun doLogin(profileLoginUser: ProfileLoginUser): MutableLiveData<ProfileLoginResponse>{

        val loginData = MutableLiveData<ProfileLoginResponse>()

        NetworkConfig.profileApi().loginUser(profileLoginUser)
            .enqueue(object : Callback<BaseProfileLoginResponse<ProfileLoginResponse>> {
                override fun onFailure(
                    call: Call<BaseProfileLoginResponse<ProfileLoginResponse>>,
                    t: Throwable
                ) {

                    // on failure code
                    MyProfileLoginViewModel.errorMessage = ""
                    loginData.postValue(null)
                }

                override fun onResponse(
                    call: Call<BaseProfileLoginResponse<ProfileLoginResponse>>,
                    response: Response<BaseProfileLoginResponse<ProfileLoginResponse>>
                ) {

                    // on response
                    if(response.isSuccessful){
                        // Response 200
                        val loginResponse = response.body()?.data
                        loginData.postValue(loginResponse)
                        Log.d("@@@Profile", "Login: ${loginResponse?.customer?.email} -- LOGIN")

                    } else {
                        // Response failed
                        when(response.code()){
                            in 400..420 -> {
                                val errorResponse = JSONObject( response.errorBody()?.string() ?: "")
                                val errorMessage = errorResponse.getJSONArray("messages")[0].toString()
                                MyProfileLoginViewModel.errorMessage = errorMessage
                                Log.d("okhttp - -", errorMessage)
                            }
                            else -> {
                                MyProfileLoginViewModel.errorMessage = "Unknown Network Error"
                            }
                        }
                        loginData.postValue(null)
                    }
                }
            })

        return loginData
    }

    fun doRegister(profileRegisterUser: ProfileRegisterUser): MutableLiveData<ProfileResponse> {

        val registerData = MutableLiveData<ProfileResponse>()

        NetworkConfig.profileApi().registerUser(profileRegisterUser)
            .enqueue(object : Callback<ProfileRegisterResponse<ProfileResponse>>{
                override fun onFailure(
                    call: Call<ProfileRegisterResponse<ProfileResponse>>,
                    t: Throwable
                ) {

                    registerData.postValue(null)
                }

                override fun onResponse(
                    call: Call<ProfileRegisterResponse<ProfileResponse>>,
                    response: Response<ProfileRegisterResponse<ProfileResponse>>
                ) {

                    if (response.isSuccessful){
                        val registerResponse = response.body()?.data
                        registerData.postValue(registerResponse)
                        Log.d("@@@Profile", "Register: Success -- CREATED")
                    } else {
                        registerData.postValue(null)
                    }
                }

            })

        return registerData
    }

    fun getProfile(token: String): MutableLiveData<ProfileResponse> {
        val profileData = MutableLiveData<ProfileResponse>()

        NetworkConfig.profileApi().getProfile("Bearer $token")
            .enqueue(object : Callback<BaseProfileResponse<ProfileResponse>> {
                override fun onFailure(
                    call: Call<BaseProfileResponse<ProfileResponse>>,
                    t: Throwable
                ) {

                    profileData.postValue(null)
                }

                override fun onResponse(
                    call: Call<BaseProfileResponse<ProfileResponse>>,
                    response: Response<BaseProfileResponse<ProfileResponse>>
                ) {

                    if (response.isSuccessful){
                        
                        val profileResponse = response.body()?.data
                        profileData.postValue(profileResponse)
                    } else {

                        profileData.postValue(null)
                    }
                }

            })

        return profileData
    }
}