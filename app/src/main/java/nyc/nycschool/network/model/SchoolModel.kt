package nyc.nycschool.network.model

import com.google.gson.annotations.SerializedName

data class SchoolModel(
    @SerializedName("school_name")
    val title: String,
    @field:SerializedName("dbn")
    val id: String = "",
)
