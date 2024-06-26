package stealthpixel.processing

object BinaryBuilder {

    fun stringToBinary(input: String): String {
        return input.toByteArray().joinToString(separator = "") { byte ->
            String.format("%8s", byte.toString(2)).replace(' ', '0')
        }
    }

    fun binaryToString(binary: String): String {
        val regex = Regex("[01]{8}")
        return regex.findAll(binary).map {
            Integer.parseInt(it.value, 2).toChar().toString()
        }.joinToString(separator = "")
    }

}