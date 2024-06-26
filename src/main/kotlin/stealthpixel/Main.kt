package stealthpixel

import stealthpixel.processing.BinaryBuilder
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class Image(path: String) {

    private val buffer: BufferedImage = ImageIO.read(File(path))

    val width: Int = buffer.width
    val height: Int = buffer.height
    val pixels: Int = width * height

    fun getPixel(x: Int, y: Int): Pixel {
        require(x in 0 until width)
        require(y in 0 until height)
        val rgb = buffer.getRGB(x, y)
        val r: Int = (rgb shr 16) and 0xFF
        val g: Int = (rgb shr 8) and 0xFF
        val b: Int = rgb and 0xFF
        return Pixel(x, y, r, g, b)
    }

    fun updatePixel(pixel: Pixel) {
        val rgb = (pixel.r shl 16) or (pixel.g shl 8) or pixel.b
        buffer.setRGB(pixel.x, pixel.y, rgb)
    }

    fun save(filename: String) {
        val file = File(filename)
        ImageIO.write(buffer, "png", file)
    }

}

data class Pixel(val x: Int, val y: Int, var r: Int, var g: Int, var b: Int) {

    fun modify(colour: Char, index: Int, value: Char) {
        require(value in listOf('0', '1'))
        require(colour in listOf('r', 'g', 'b'))
        require(index in 0..7)
        when (colour) {
            'r' -> r = modify(r, index, value)
            'g' -> g = modify(g, index, value)
            'b' -> b = modify(b, index, value)
        }
    }

    private fun modify(colour: Int, index: Int, value: Char): Int {
        val mask = 1 shl (7 - index)
        return if (value == '1') {
            colour or mask
        } else {
            colour and mask.inv()
        }
    }

}

fun main() {
    val image = Image("images/test.jpg")
    val pixel = image.getPixel(10, 10)
    pixel.modify('r', 0, '0')
    pixel.modify('g', 0, '0')
    image.updatePixel(pixel)
    image.save("images/jesus.png")

    val test = "Super &*^#&^secret sauce"
    val testing = BinaryBuilder.stringToBinary(test)
    println(testing)
    val tester = BinaryBuilder.binaryToString(testing)
    println(tester)
}