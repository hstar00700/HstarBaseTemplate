package com.hstar.data.model

import android.os.Parcelable
import com.hstar.base.data.model.DataModel
import com.hstar.base.domain.mapper.EntityMapper
import com.hstar.domain.sample.entity.UserEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDataModel(
    val avatar: String,
    val email: String,
    val firstName: String,
    val id: Int,
    val lastName: String
) : Parcelable, DataModel {
    companion object : EntityMapper<UserDataModel, UserEntity> {
        override fun toEntity(dataModel: UserDataModel) = with(dataModel) {
            UserEntity(
                id = id,
                avatar = avatar,
                email = email,
                firstName = firstName,
                lastName = lastName
            )
        }
    }
}
