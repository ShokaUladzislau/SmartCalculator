fun main() {
    val report = readLine()!!
    //write your code here.

    val pass = Regex("[0-9] wrong answers?")

    println(report.matches(pass))


}