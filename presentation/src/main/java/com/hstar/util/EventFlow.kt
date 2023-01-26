package com.hstar.util

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import java.util.concurrent.atomic.AtomicBoolean

/**
 * event를 observe하고 있는 곳이 아무데도 없다면,
해당 event는 유실되어서 event가 발생했던것을 나중에라도 알 수 없다는 것입니다.
그래서 Event가 발생했을때 이를 캐시하고 있다가
event의 consume 여부에 따라서 새로 구독하는 observer가 있을때 event를 전파할지 여부를 결정해주는 별도의 EventFlow
를 만들었습니다.
@See
 */
interface EventFlow<out T> : Flow<T> {

    companion object {

        const val DEFAULT_REPLAY: Int = 3
    }
}

interface MutableEventFlow<T> : EventFlow<T>, FlowCollector<T>

@Suppress("FunctionName")
fun <T> MutableEventFlow(
    replay: Int = EventFlow.DEFAULT_REPLAY
): MutableEventFlow<T> = EventFlowImpl(replay)

fun <T> MutableEventFlow<T>.asEventFlow(): EventFlow<T> = ReadOnlyEventFlow(this)

private class ReadOnlyEventFlow<T>(flow: EventFlow<T>) : EventFlow<T> by flow

private class EventFlowImpl<T>(
    replay: Int
) : MutableEventFlow<T> {

    private val flow: MutableSharedFlow<EventFlowSlot<T>> = MutableSharedFlow(replay = replay)

    @InternalCoroutinesApi
    override suspend fun collect(collector: FlowCollector<T>) = flow
        .collect { slot ->
            if (!slot.markConsumed()) {
                collector.emit(slot.value)
            }
        }

    override suspend fun emit(value: T) {
        flow.emit(EventFlowSlot(value))
    }
}

private class EventFlowSlot<T>(val value: T) {

    private val consumed: AtomicBoolean = AtomicBoolean(false)

    fun markConsumed(): Boolean = consumed.getAndSet(true)
}


interface EventDelegator<T> {
    suspend fun result(): T

    fun tryEmit(value: T): Boolean

    fun cancel(): Boolean
}


/**
 * 동기처리를 위해 이벤트 딜레이
 */
class DelegatedEvent<T : Any> : EventDelegator<T> {
    private val flow = MutableSharedFlow<T?>(extraBufferCapacity = 1)

    override suspend fun result(): T = flow.first() ?: throw CancellationException()

    override fun tryEmit(value: T): Boolean = flow.tryEmit(value)

    override fun cancel(): Boolean = flow.tryEmit(null)
}