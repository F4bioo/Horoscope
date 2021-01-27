package fbo.costa.horoscope.presenter.details

import fbo.costa.horoscope.data.model.SignApiEntity

interface DetailsHome {

    interface Presenter {

        fun onRequestSign(sign: String)

        fun onSuccess(dataResponse: SignApiEntity)

        fun onError(message: String)

        fun onBack(hasError: Boolean)

        fun onComplete()

        fun onStartActivity(url: String)

    }
}
