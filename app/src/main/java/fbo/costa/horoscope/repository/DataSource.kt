package fbo.costa.horoscope.repository

import android.content.Context
import fbo.costa.horoscope.R
import fbo.costa.horoscope.data.api.RetrofitInstance
import fbo.costa.horoscope.presenter.details.DetailsHome
import fbo.costa.horoscope.util.NetworkUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DataSource(private val context: Context) {

    fun getSign(callback: DetailsHome.Presenter, sign: String) {
        if (NetworkUtil().isOnline(context)) {
            CoroutineScope(Dispatchers.Main).launch {
                val response = RetrofitInstance.api.getSign(sign)
                if (response.isSuccessful) {
                    response.body()?.let { _signApiEntity ->
                        callback.onSuccess(_signApiEntity)
                    }
                    callback.onComplete()

                } else {
                    callback.onError(response.message())
                    callback.onComplete()
                    callback.onBack(true)
                }
            }
        } else {
            callback.onError(context.getString(R.string.text_no_internet_connection))
            callback.onComplete()
            callback.onBack(true)
        }
    }
}
