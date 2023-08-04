package nyc.nycschool.network.model

import com.google.gson.annotations.SerializedName


data class SatModel(
    @SerializedName("school_name")
    val schoolName: String,
    @field:SerializedName("dbn")
    val id: String = "",
    @field:SerializedName("sat_critical_reading_avg_score")
    val readingScore: String,
    @field:SerializedName("sat_math_avg_score")
    val mathScore: String,
    @field:SerializedName("sat_writing_avg_score")
    val writingScore: String,
)
