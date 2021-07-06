fun main() {
    val words = mutableMapOf<String, Int>()

    do {
        val key = readLine()!!
        if (key == "stop") break
        words[key] = (words[key] ?: 0) + 1
    } while (true)

    println(words.maxByOrNull { it.value }?.key)
}