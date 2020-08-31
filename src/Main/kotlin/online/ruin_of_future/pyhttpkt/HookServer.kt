package online.ruin_of_future.pyhttpkt

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlin.properties.Delegates
import kotlin.reflect.KClass
import kotlin.reflect.KClassifier
import kotlin.reflect.KParameter
import kotlin.reflect.full.createType
import kotlin.reflect.full.valueParameters

/**
 * Init this class with a KClass. Type parameter can be omitted.
 *
 * ```
 * // Example:
 * val hookServer = HookServer(MyClass::class)
 * ```
 * */
class HookServer<T : Any>(private val clazz: KClass<T>, val port: Int = 8080) {
    private fun String.toType(type: KClassifier): Any {
        when (type) {
            String::class -> {
                return this
            }
            Byte::class -> {
                return this.toByte()
            }
            Short::class -> {
                return this.toShort()
            }
            Int::class -> {
                return this.toInt()
            }
            Long::class -> {
                return this.toLong()
            }
            Float::class -> {
                return this.toFloat()
            }
            Double::class -> {
                return this.toDouble()
            }
            Boolean::class -> {
                return this.toBoolean()
            }
            else -> {
                throw Exception("Not supported type")
            }
        }
    }

    private val buffer = BoundedBuffer<T>()

    suspend fun popFromBuffer(): T {
        return buffer.popFromBuffer()
    }

    fun serve() {
        embeddedServer(Netty, port) {
            routing {
                get("/") {
                    val httpParams = call.parameters
                    val mp = mutableMapOf<KParameter, Any>()
                    val constructor = clazz.constructors.first { func ->
                        val pName = func.parameters.map { param ->
                            param.name!!
                        }.sorted()
                        val rName = httpParams.entries().map {
                            it.key
                        }.sorted()
                        pName == rName
                    }
                    for (kParam in constructor.parameters) {
                        val valueStr = httpParams[kParam.name!!]!!
                        val value = valueStr.toType(kParam.type.classifier!!)
                        mp[kParam] = value
                    }
                    val dpkt = constructor.callBy(mp)
                    buffer.pushIntoBuffer(dpkt)
                    call.respondText("Success")
                }
            }
        }.start(wait = true)
    }
}