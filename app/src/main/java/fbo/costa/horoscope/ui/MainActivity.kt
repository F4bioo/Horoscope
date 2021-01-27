package fbo.costa.horoscope.ui

import android.content.Intent
import android.view.View
import androidx.viewbinding.ViewBinding
import fbo.costa.horoscope.R
import fbo.costa.horoscope.databinding.ActivityMainBinding
import fbo.costa.horoscope.util.Constants


class MainActivity : AbstractActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun getLayout(): ViewBinding {
        binding = ActivityMainBinding.inflate(layoutInflater)
        return binding
    }

    override fun onInject() {
        binding.apply {
            aries.setOnClickListener(this@MainActivity)
            taurus.setOnClickListener(this@MainActivity)
            gemini.setOnClickListener(this@MainActivity)
            cancer.setOnClickListener(this@MainActivity)
            leo.setOnClickListener(this@MainActivity)
            virgo.setOnClickListener(this@MainActivity)
            libra.setOnClickListener(this@MainActivity)
            scorpio.setOnClickListener(this@MainActivity)
            sagittarius.setOnClickListener(this@MainActivity)
            capricorn.setOnClickListener(this@MainActivity)
            aquarius.setOnClickListener(this@MainActivity)
            pisces.setOnClickListener(this@MainActivity)
        }
    }

    override fun onClick(view: View?) {
        binding.apply {
            when (view?.id) {
                aries.id -> startActivity(0)
                taurus.id -> startActivity(1)
                gemini.id -> startActivity(2)
                cancer.id -> startActivity(3)
                leo.id -> startActivity(4)
                virgo.id -> startActivity(5)
                libra.id -> startActivity(6)
                scorpio.id -> startActivity(7)
                sagittarius.id -> startActivity(8)
                capricorn.id -> startActivity(9)
                aquarius.id -> startActivity(10)
                pisces.id -> startActivity(11)
            }
        }
    }

    private fun startActivity(position: Int) {
        val sign = resources.getStringArray(R.array.get_sign_api)[position]
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(Constants.EXTRA_SIGN, sign)
        intent.putExtra(Constants.EXTRA_POSITION, position)
        startActivity(intent)
    }
}
