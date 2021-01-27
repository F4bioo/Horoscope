package fbo.costa.horoscope.presenter.details

import android.content.Context
import android.content.Intent
import android.net.Uri
import fbo.costa.horoscope.data.model.Sign
import fbo.costa.horoscope.data.model.SignApiEntity
import fbo.costa.horoscope.presenter.ViewHome
import fbo.costa.horoscope.repository.DataSource
import fbo.costa.horoscope.util.Constants
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

class DetailsPresenter(
    private val context: Context,
    private val view: ViewHome.View,
    private val dataSource: DataSource
) : DetailsHome.Presenter {

    override fun onRequestSign(sign: String) {
        this.view.showProgress()
        this.dataSource.getSign(this, sign)
    }

    override fun onSuccess(dataResponse: SignApiEntity) {
        val sign = dataResponse.toSign()
        this.view.showSign(sign)
    }

    override fun onError(message: String) {
        this.view.showFailure(message)
    }

    override fun onBack(hasError: Boolean) {
        if (hasError) this.view.returnAfterError()
    }

    override fun onComplete() {
        this.view.hideProgress()
    }

    override fun onStartActivity(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }

    // Convert ArticleApiEntity to Article
    private fun SignApiEntity.toSign(): Sign {
        var text = this.text
        this.text?.let { _text ->
            text = getText(_text.trim())
        }

        val dataAccess = this.dataAccess
        this.dataAccess?.date?.let { _date ->
            dataAccess?.date = datetimeFormat(_date)
        }

        return Sign(
            author = this.author,
            dataAccess = dataAccess,
            sign = this.sign,
            text = text,
            urlOrigin = this.urlOrigin
        )
    }

    // Divide and get only the first part of the daily horoscope
    private fun getText(text: String): String {
        val space = Pattern.compile(" {7}")

        return space.split(text)[0]
    }

    // ApiNews provides this type: 2021-01-26 17:34:19.158269
    // Fun returns this type: Tue, 26 Jan 17:34
    private fun datetimeFormat(publishedAt: String): String {
        val apiDatetime = SimpleDateFormat(Constants.API_DATETIME_FORMAT, Locale.getDefault())
        val pattern = SimpleDateFormat(Constants.PATTERN, Locale.getDefault())

        apiDatetime.parse(publishedAt)?.let { _date ->
            return pattern.format(_date)
        }
        return publishedAt
    }
}
