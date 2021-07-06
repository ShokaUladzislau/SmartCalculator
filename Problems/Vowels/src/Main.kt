fun main() {
    val letter = readLine()!!.first()
    val vovels = mutableMapOf(1 to 'a', 5 to 'e', 9 to 'i', 15 to 'o', 21 to 'u')
    var result = 0

    for (entry in vovels) {
        if (entry.value == letter.toLowerCase()){
            result = entry.key
        }
    }

    println(result)
}