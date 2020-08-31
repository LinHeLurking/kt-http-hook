package online.ruin_of_future.pyhttpkt

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withLock

class BoundedBuffer<T>(private val bufferSize: Int = 20) {
    private val semEmpty = Semaphore(bufferSize)
    private val semFull = Semaphore(bufferSize, bufferSize)
    private val mutex = Mutex()

    private val buffer = mutableListOf<T>()

    suspend fun pushIntoBuffer(entry: T) {
        semEmpty.acquire()
        mutex.withLock {
            buffer.add(entry)
        }
        semFull.release()
    }

    suspend fun popFromBuffer(): T {
        semFull.acquire()
        var ret: T
        mutex.withLock {
            ret = buffer.removeAt(0)
        }
        semEmpty.release()
        return ret
    }
}