package com.example.hotstuffkotlin.utils
import java.io.Writer

class CSVHelper(printWriter: Writer) {
    private val writer = printWriter
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
        writer.write(stringBuffer.toString())
    }

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