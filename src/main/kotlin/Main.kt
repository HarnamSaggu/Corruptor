import java.io.File

fun main() {
	println("Filepath")
	var bytes: ByteArray
	try {
		val filepath = readLine() ?: ""
		val file = File(filepath)
		if (file.isFile) bytes = file.readBytes()
		else return
	} catch (e: Exception) {
		return
	}
	println("File size: ${bytes.size} bytes")
	println("Corrupted filepath")
	try {
		val newFile = File(readLine() ?: "")
		println("Enter locations for shuffling and scrambling")
		do {
			println("Command e.g. shuffle 0, 100 or scramble 300, 420 and to exit type 'done'")
			val input = readLine() ?: return
			val coords = input.replace("\\D+".toRegex(), " ").trim().split("\\s+".toRegex())
			if (input.matches("shuffle\\s+\\d+\\s+\\d+".toRegex())) {
				bytes = bytes.shuffleSection(coords[0].toInt(), coords[1].toInt())
			} else if (input.matches("scramble\\s+\\d+\\s+\\d+".toRegex())) {
				bytes = bytes.scrambleBytes(
					coords[0].toInt(),
					coords[1].toInt(),
					intArrayOf(-10, -15, -1, -2, 0, 0, 0, 0, 0, 1, 2, 20, 15, 3)
				)
			}

		} while (input != "done")


		newFile.writeBytes(bytes)
		println("\nEverything went well")
	} catch (e: Exception) {
		return
	}
}

fun ByteArray.scrambleBytes(start: Int, end: Int, scrambleArray: IntArray): ByteArray {
	if (start < 0 || start >= this.size) return this
	if (end < 0 || end >= this.size) return this

	val scrambledBytes = this
	for (i in start until end) {
		scrambledBytes[i] = (scrambledBytes[i] + scrambleArray.random().toByte()).toByte()
	}

	return scrambledBytes
}

fun ByteArray.shuffleSection(start: Int, end: Int): ByteArray {
	if (start < 0 || start >= this.size) return this
	if (end < 0 || end >= this.size) return this

	val byteList = this.toMutableList()
	val shuffledSection = byteList.subList(start, end)
	shuffledSection.shuffle()

	return (byteList.subList(0, start) + shuffledSection + byteList.subList(end, byteList.size)).toByteArray()
}
