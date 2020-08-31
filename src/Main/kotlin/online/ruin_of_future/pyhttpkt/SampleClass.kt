package online.ruin_of_future.pyhttpkt

data class SampleClass(
    val ID: String,
    val courseName: String,
    val assignmentName: String,
    val lastVisited: Long
) {
    constructor(ID: String, courseName: String, assignmentName: String) :
            this(ID, courseName, assignmentName, -1)
}