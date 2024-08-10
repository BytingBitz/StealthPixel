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
        val red: Int = (rgb shr 16) and 0xFF
        val green: Int = (rgb shr 8) and 0xFF
        val blue: Int = rgb and 0xFF
        return Pixel(x, y, red, green, blue)
    }

    fun updatePixel(pixel: Pixel) {
        val rgb = (pixel.red shl 16) or (pixel.green shl 8) or pixel.blue
        buffer.setRGB(pixel.x, pixel.y, rgb)
    }

    fun save(filename: String) {
        val file = File(filename)
        ImageIO.write(buffer, "png", file)
    }
}

enum class Colour {
    Red, Green, Blue
}

data class Pixel(val x: Int, val y: Int, var red: Int, var green: Int, var blue: Int) {

    fun modify(colour: Colour, index: Int, bit: Int) {
        require(index in 0..7)
        require(bit in 0..1)
        when (colour) {
            Colour.Red -> red = update(red, index, bit)
            Colour.Green -> green = update(green, index, bit)
            Colour.Blue -> blue = update(blue, index, bit)
        }
    }

    private fun update(colour: Int, index: Int, bit: Int): Int {
        val mask = 1 shl (7 - index)
        return if (bit == 1) {
            colour or mask
        } else {
            colour and mask.inv()
        }
    }
}

fun main() {
    val image = Image("images/test.jpg")
    val pixel = image.getPixel(10, 10)
    pixel.modify(Colour.Red, 0, 0)
    pixel.modify(Colour.Green, 0, 0)
    image.updatePixel(pixel)
    image.save("images/jesus.png")

    val test = "Super &*^#&^secret sauce"
    val testing = BinaryBuilder.stringToBinary(test)
    println(testing)
    val tester = BinaryBuilder.binaryToString(testing)
    println(tester)
}