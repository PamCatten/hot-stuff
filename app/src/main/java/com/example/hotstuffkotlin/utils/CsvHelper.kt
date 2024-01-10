package com.example.hotstuffkotlin.utils

import java.io.File

class CsvHelper(file: File) {
    fun separateRow(nextLine: ArrayList<String>?, separator: Char = SEPARATOR, lineEnd: String = LINE_END) {
        if (nextLine == null) return
        val stringBuffer = StringBuffer()
        for (i in 0..nextLine.size) if (i != 0) stringBuffer.append(separator)
        stringBuffer.append(lineEnd)

        // do something with file
    }

    // make file function

    companion object {
        private const val SEPARATOR: Char = ','
        private const val LINE_END: String = "\n"
    }
}