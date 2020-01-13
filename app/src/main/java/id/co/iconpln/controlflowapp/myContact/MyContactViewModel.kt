package id.co.iconpln.controlflowapp.myContact

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import id.co.iconpln.controlflowapp.models.myContact.ContactResponse
import id.co.iconpln.controlflowapp.network.MyContactNetworkRepository
import org.json.JSONObject

class MyContactViewModel : ViewModel() {
    private val listContacts = MutableLiveData<ArrayList<MyContact>>()

    internal fun setContact(){
        val client = AsyncHttpClient()
        val listItemContacts = ArrayList<MyContact>()
        val url = "https://api.androidhive.info/contacts/"

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                try {

                    // get data json
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val arrayContacts = responseObject.getJSONArray("contacts")

                    // convert JSON Object to readable data
                    // data in JSON Object is read by ITS key, exp: id, name
                    for(i in 0 until arrayContacts.length()){
                        val contact = arrayContacts.getJSONObject(i)
                        val contactItem = MyContact()

                        contactItem.id = contact.getString("id")
                        contactItem.name = contact.getString("name")
                        contactItem.email = contact.getString("email")
                        contactItem.mobile = contact.getJSONObject("phone").getString("mobile")

                        listItemContacts.add(contactItem)
                    }

                    // post realtime latest value from Backgroud Thread
                    listContacts.postValue(listItemContacts)

                } catch (e: Exception){
                    Log.d("@@@MyContactException", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                Log.d("@@@MyContactFailure", error.message.toString())
            }

        })
    }

    internal fun getContact(): LiveData<ArrayList<MyContact>> {
        return listContacts
    }

    fun getListContact(): MutableLiveData<ArrayList<ContactResponse>> {
        return MyContactNetworkRepository().fetchContact()
    }
}