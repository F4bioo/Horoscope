package fbo.costa.horoscope.ui

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.Toast
import androidx.viewbinding.ViewBinding
import fbo.costa.horoscope.R
import fbo.costa.horoscope.data.model.Sign
import fbo.costa.horoscope.databinding.ActivityDetailsBinding
import fbo.costa.horoscope.presenter.ViewHome
import fbo.costa.horoscope.presenter.details.DetailsPresenter
import fbo.costa.horoscope.repository.DataSource
import fbo.costa.horoscope.util.Constants

class DetailsActivity : AbstractActivity(), ViewHome.View {

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var presenter: DetailsPresenter

    private var sign: String? = null
    private var position: Int? = null

    override fun getLayout(): ViewBinding {
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        return binding
    }

    override fun onInject() {
        val dataSource = DataSource(this)
        presenter = DetailsPresenter(this, this, dataSource)
        getExtras()
    }

    override fun showProgress() {
        binding.progress.visibility = View.VISIBLE
    }

    override fun showFailure(message: String) {
        Toast.makeText(
            this,
            message, Toast.LENGTH_SHORT
        ).show()
    }

    override fun returnAfterError() {
        this.onBackPressed()
    }

    override fun hideProgress() {
        binding.progress.visibility = View.INVISIBLE
    }

    override fun showSign(sign: Sign) {
        var singName = sign.sign
        position?.let { _position ->
            singName = resources.getStringArray(
                R.array.set_sign_app
            )[_position]
        }

        binding.apply {
            textSign.text = singName
            textDaily.text = sign.text
            textAuthor.text = sign.author
            textDate.text = sign.date

            textSource.visibility = View.VISIBLE
            textSource.setOnClickListener {
                sign.urlOrigin?.let { _url ->
                    startActivity(_url)
                }
            }
        }
    }

    private fun getExtras() {
        intent.extras?.let { _sign ->
            sign = _sign.getString(Constants.EXTRA_SIGN)
        }

        intent.extras?.let { _position ->
            position = _position.getInt(Constants.EXTRA_POSITION)
        }

        sign?.let { _sign ->
            presenter.onRequestSign(_sign)
        }
    }

    private fun startActivity(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}
