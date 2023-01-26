package com.hstar.base.domain.mapper

import com.hstar.base.data.model.DataModel
import com.hstar.base.domain.model.Entity

interface EntityMapper<in D : DataModel, out E : Entity> {
    fun toEntity(dataModel: D): E
}
