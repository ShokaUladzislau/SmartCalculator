fun main() {
    val library = mutableMapOf<String, Int>()
    library["The Art of Computer Programming"] = 2
    library["Design Patterns"] = 4
    library["Cracking the Coding Interview"] = 3
    library["Design Patterns"] = 5
    library["Clean code"] = 1
    library.remove("Clean code")
    var total = 0
    for (entry in library) {
        total += entry.value
    }
    print(total)
}