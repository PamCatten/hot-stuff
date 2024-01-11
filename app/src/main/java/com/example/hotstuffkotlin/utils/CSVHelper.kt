package com.example.hotstuffkotlin.utils

import java.io.Writer

class CSVHelper(printWriter: Writer) {
    private val writer = printWriter
    fun separateRow(nextLine: ArrayList<String>?, separator: Char = SEPARATOR, lineEnd: String = LINE_END) {
        if (nextLine == null) return
        val stringBuffer = StringBuffer()
        for (i in 0 until nextLine.size) {
            if (i != 0) stringBuffer.append(separator)
            val nextElement: String = nextLine[i]

            for (j in nextElement.indices) {
                val nextChar = nextElement[j]
                stringBuffer.append(nextChar)
            }
        }
        stringBuffer.append(lineEnd)
        writer.write(stringBuffer.toString())
        // do something with file
    }

    // make file function
    fun exportFile() {
        val foo : Any
    }

    companion object {
        private const val SEPARATOR: Char = ','
        private const val LINE_END: String = "\n"
    }
}