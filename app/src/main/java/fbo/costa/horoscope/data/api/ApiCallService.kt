package fbo.costa.horoscope.data.api

import android.content.Context
import fbo.costa.horoscope.BuildConfig
import fbo.costa.horoscope.util.Constants
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiCallService {

    private var api: ApiService? = null

    private fun getApi(context: Context): ApiService {
        if (api == null) {
            val client = OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)

            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            if (BuildConfig.DEBUG) {
                client.addInterceptor(logging)
            }

            val cacheSize = 5 * 1024 * 1024L
            val cache = Cache(context.cacheDir, cacheSize)
            client.cache(cache)

            api = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build()
                .create(ApiService::class.java)
        }
        return api!!
    }

    /**
     * * **Context**
     * Activity context can be destroyed and recreated
     * whereas the application context has a lifetime
     * of the whole activity
     *
     * @param context Consider using ApplicationContext
     */
    suspend fun call(context: Context, sign: String) =
        getApi(context).getSign(sign)
}
