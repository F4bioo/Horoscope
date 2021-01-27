package fbo.costa.horoscope.data.model


import com.google.gson.annotations.SerializedName

data class DataAccess(
    var date: String?,
    val timezone: String?,
    @SerializedName("timezone_type")
    val timezoneType: Int?
)
