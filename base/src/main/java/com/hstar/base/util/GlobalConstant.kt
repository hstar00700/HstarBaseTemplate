package com.hstar.base.util

import io.reactivex.rxjava3.subjects.PublishSubject

val batteryStatePublishSubject: PublishSubject<Pair<Float, Float>> = PublishSubject.create()

