package fbo.costa.horoscope.presenter.details

import android.content.Context
import fbo.costa.horoscope.data.model.Sign
import fbo.costa.horoscope.data.model.SignApiEntity
import fbo.costa.horoscope.presenter.ViewHome
import fbo.costa.horoscope.repository.DataSource
import fbo.costa.horoscope.util.Constants
import fbo.costa.horoscope.util.EntityMapper
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

class DetailsPresenter(
    private val context: Context,
    private val view: ViewHome.View,
    private val dataSource: DataSource
) : DetailsHome.Presenter, EntityMapper<SignApiEntity, Sign> {

    override fun onRequestSign(sign: String) {
        this.view.showProgress()
        this.dataSource.getSign(this, sign)
    }

    override fun onSuccess(signApiEntity: SignApiEntity) {
        val sign = mapFromEntityModel(signApiEntity)
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

    // Convert SignApiEntity to Sign
    override fun mapFromEntityModel(entityModel: SignApiEntity): Sign {
        var text = entityModel.text
        entityModel.text?.let { _text ->
            text = textConvert(_text)
        }

        var date = entityModel.dataAccess?.date
        entityModel.dataAccess?.date?.let { _date ->
            date = dateConvert(_date)
        }

        return Sign(
            author = entityModel.author,
            date = date,
            sign = entityModel.sign,
            text = text,
            urlOrigin = entityModel.urlOrigin
        )
    }

    // Divide and get only the first part of the daily horoscope
    private fun textConvert(text: String): String {
        val space = Pattern.compile(" {7}")

        return space.split(text.trim())[0]
    }

    // ApiNews provides this type: 2021-01-26 17:34:19.158269
    // Fun returns this type: Tue, 26 Jan 17:34
    private fun dateConvert(publishedAt: String): String {
        val apiDatetime = SimpleDateFormat(Constants.API_DATETIME_FORMAT, Locale.getDefault())
        val pattern = SimpleDateFormat(Constants.PATTERN, Locale.getDefault())

        apiDatetime.parse(publishedAt)?.let { _date ->
            return pattern.format(_date)
        }
        return publishedAt
    }
}
