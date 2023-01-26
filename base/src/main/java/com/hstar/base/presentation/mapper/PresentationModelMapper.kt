package com.hstar.base.presentation.mapper

import com.hstar.base.domain.model.Entity
import com.hstar.base.presentation.model.PresentationModel

interface PresentationModelMapper<in E : Entity, out P : PresentationModel> {
    fun toPresentationModel(entity: E): P
}
