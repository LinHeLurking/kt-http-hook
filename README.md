# A Simple Kotlin HTTP Hooker

![Build Status](https://github.com/LinHeLurking/kt-http-hook/workflows/Build/badge.svg)

This repo contains some sample codes about the usage http
as message channel across different programming languages(In 
this repo its Python).

# Usage

Assume that you have a data class like this.

```kotlin
data class SampleClass(
    val ID: String,
    val courseName: String,
    val assignmentName: String,
    val lastVisited: Long
) {
    constructor(ID: String, courseName: String, assignmentName: String) :
            this(ID, courseName, assignmentName, -1)
}
```

Then you need launch a `HookServer` like this.

```kotlin
val hookServer = HookServer(clazz = SampleClass::class)
launch {
    println("Receiver launched")
    hookServer.serve()
}
```

Passing `clazz` to `HookServer` constructor is for reflection
convenience.

Then just send data to it using HTTP Get. You have to set parameters
have the same name as those declared in one of the constructors.

```python
import requests


def sendTest(port=8080):
    res = requests.get(f"http://127.0.0.1:{port}", params={
        "ID": "ABC123zxy",
        "courseName": "测试课程",
        "assignmentName": "测试作业",
        "lastVisited": "123456"
    })
    if res.text != "Success":
        print("Error")
    else:
        print("Success")
    # Wrong case
    res = requests.get(f"http://127.0.0.1:{port}", params={
        "DI": "ABC123zxy",
        "couresName": "测试课程",
        "assignmetnName": "测试作业",
        "lastVsiited": "123456"
    })
    if res.text != "Success":
        print("Error")
    else:
        print("Success")
    res = requests.get(f"http://127.0.0.1:{port}", params={
        "ID": "ABC123zxy",
        "courseName": "测试课程",
        "assignmentName": "测试作业"
    })
    if res.text != "Success":
        print("Error")
    else:
        print("Success")
```


Constructors are searched automatically with respect to their
types and names. Sample outputs:

> Sender Side:

```
❯ python.exe .\src\py\Sender.py
Success
Error
Success
```

> Receiver Side:

```
Received DataPacket: 
ID: ABC123zxy, 
courseName: 测试课程, 
assignmentName: 测试作业, 
lastVisited: 123456
Received DataPacket: 
ID: ABC123zxy, 
courseName: 测试课程, 
assignmentName: 测试作业, 
lastVisited: -1
```

Newly created instances of `SampleClass` is
stored in a buffer. You can retrieve entries from it by calling
this.

```kotlin
val entry = hookServer.popFromBuffer()
```
