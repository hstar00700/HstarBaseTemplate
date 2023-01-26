package com.hstar.data.sample.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResKdsData(
    @SerializedName("data")
    val data : List<KdsData>,
    @SerializedName("page")
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("total")
    val total: Int,
    @SerializedName("total_pages")
    val total_pages: Int
) : Parcelable

@Parcelize
data class KdsData(
    val status: String,
    val menuName: String,
    val orderNumber: Int,
    val orderCode: String,
    val tableName: String,
) : Parcelable