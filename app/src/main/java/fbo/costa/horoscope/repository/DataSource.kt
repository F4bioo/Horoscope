package fbo.costa.horoscope.repository

import android.content.Context
import fbo.costa.horoscope.R
import fbo.costa.horoscope.data.api.RetrofitInstance
import fbo.costa.horoscope.presenter.details.DetailsHome
import fbo.costa.horoscope.util.NetworkUtil
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DataSource(private val context: Context) {

    private lateinit var callback: DetailsHome.Presenter

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onException("Exception: ${throwable.localizedMessage}")
    }

    fun getSign(callback: DetailsHome.Presenter, sign: String) {
        this.callback = callback

        if (!NetworkUtil().isOnline(context)) {
            callback.onError(context.getString(R.string.text_no_internet_connection))
            callback.onComplete()
            return
        }

        CoroutineScope(Dispatchers.Main + exceptionHandler).launch {
            val response = RetrofitInstance.api.getSign(sign)
            if (response.isSuccessful) {
                response.body()?.let { _signApiEntity ->
                    callback.onSuccess(_signApiEntity)
                }
                callback.onComplete()

            } else {
                callback.onError(response.message())
                callback.onComplete()
            }
        }
    }

    private fun onException(message: String) {
        callback.onError(message)
        callback.onComplete()
    }
}
