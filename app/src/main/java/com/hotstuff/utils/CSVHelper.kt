package com.hotstuff.utils
import java.io.Writer

/**
 * A helper object to convert and format List values to a text-output stream.
 * @param printWriter instance of [Writer] created in [DatabaseHelper.exportCSV]
 * @see [DatabaseHelper]
 * @author Cam Patten
 */
class CSVHelper(printWriter: Writer) {
    private val writer = printWriter
    /**
     * Separate and escape quotes escape, and write a given row of values to a text-output stream.
     * @author Cam Patten
     */
    fun separateRow(nextLine: MutableList<String>?) {
        if (nextLine == null) return
        val stringBuffer = StringBuffer()
        for (i in 0 until nextLine.size) {
            if (i != 0) stringBuffer.append(SEPARATOR)
            val nextElement: String = nextLine[i]
            stringBuffer.append(QUOTE_CHAR)
            for (j in nextElement.indices) {
                when (val nextChar = nextElement[j]) {
                    SEPARATOR -> stringBuffer.append(nextChar)
                    QUOTE_CHAR -> stringBuffer.append(ESCAPE_CHAR).append(nextChar)
                    else -> stringBuffer.append(nextChar)
                }
            }
            stringBuffer.append(QUOTE_CHAR)
        }
        stringBuffer.append(LINE_END)
        writeToFile(stringBuffer)
    }

    /**
     * Write a string buffer to the file associated with the invoked CSVHelper [Writer].
     * @author Cam Patten
     */
    private fun writeToFile(stringBuffer: StringBuffer) {
        writer.write(stringBuffer.toString())
    }

    /**
     * Flush and close Java I/O stream.
     * @author Cam Patten
     */
    fun close() {
        writer.flush()
        writer.close()
    }

    companion object {
        private const val SEPARATOR: Char = ','
        private const val QUOTE_CHAR: Char = '"'
        private const val ESCAPE_CHAR: Char = '"'
        private const val LINE_END: String = "\n"
    }
}