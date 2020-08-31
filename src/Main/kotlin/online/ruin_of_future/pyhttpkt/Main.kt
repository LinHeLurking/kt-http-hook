package online.ruin_of_future.pyhttpkt

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

suspend fun main(args: Array<String>) {
    println("Hello World")
    // You should chose available open port
    val port = 8080
    val hookServer = HookServer(clazz = SampleClass::class, port = port)
    coroutineScope {
        launch {
            println("Receiver launched")
            hookServer.serve()
        }
        launch {
            println("Checker launched")
            while (true) {
                val dpkt = hookServer.popFromBuffer()
                println(
                    "Received DataPacket: \n" +
                            "ID: ${dpkt.ID}, \n" +
                            "courseName: ${dpkt.courseName}, \n" +
                            "assignmentName: ${dpkt.assignmentName}, \n" +
                            "lastVisited: ${dpkt.lastVisited}"
                )
            }
        }
    }
}