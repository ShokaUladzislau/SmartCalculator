fun solution(strings: MutableList<String>, str: String): MutableList<String> {
    while (strings.contains(str)){
        strings[strings.indexOf(str)] = "Banana"
    }
    return strings
}

