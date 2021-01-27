package fbo.costa.horoscope.data.model

import com.google.gson.annotations.SerializedName

data class SignApiEntity(
    @SerializedName("autor")
    val author: String?,
    @SerializedName("dataAcesso")
    val dataAccess: DataAccess?,
    @SerializedName("signo")
    val sign: String?,
    @SerializedName("texto")
    val text: String?,
    @SerializedName("urlOrigem")
    val urlOrigin: String?
)
