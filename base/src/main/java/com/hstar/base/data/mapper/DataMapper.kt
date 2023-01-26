package com.hstar.base.data.mapper

import com.hstar.base.data.model.DataModel
import com.hstar.base.data.model.remote.ResponseModel

interface DataMapper<in R : ResponseModel, out D : DataModel> {
    fun toDataModel(response: R): D
}
