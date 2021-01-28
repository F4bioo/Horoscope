package fbo.costa.horoscope.presenter

import fbo.costa.horoscope.data.model.Sign

interface ViewHome {

    interface View {

        fun showProgress()

        fun showFailure(message: String)

        fun hideProgress()

        fun showSign(sign: Sign)

    }
}
