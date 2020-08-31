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


if __name__ == '__main__':
    sendTest()
