package com.hstar.domain.sample.entity

import com.hstar.base.domain.model.Entity

data class SignInEntity(
    val access_token: String? = null,
    val token_type: String? = null,
    val refresh_token: String? = null,
    val expires_in: String? = null,
    val scope: String? = null,
    val jti: String? = null,
    val firebaseToken: String? = null,
    val storeCd: Int? = null,
    val storeId: String? = null,
    val custCd: Int? = null,
    val custId: String? = null,
    val authRoles: String? =  null,
    val storeMng: Boolean? = null,
    val admin: Boolean? = null
) : Entity