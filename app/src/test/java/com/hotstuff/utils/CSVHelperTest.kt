package com.hotstuff.utils

import org.mockito.Mockito.mock
import java.io.PrintWriter

class CSVHelperTest {

    private val writer = mock<PrintWriter>()
    private val csvHelper = CSVHelper(writer)
//    @Rule
//    val testFolder = TemporaryFolder()
//    @Test
//    fun `GIVEN csv helper WHEN separateRow is called THEN verify returns if nextLine null`() {
//        val items: MutableList<String>? = null
//
//        csvHelper.separateRow(items)
//
//    }
//    @Test
//    fun `GIVEN csv helper WHEN separateRow is called THEN verify return expected`() {
//        val items: MutableList<String> = mutableListOf("name", "category", "room")
//        val expected = csvHelper.separateRow(items)
//
//        runBlocking {
//            CSVHelper(writer).separateRow(items)
//        }
//
//        verify(writer).write(csvHelper.separateRow(items))
//    }
//
//    @Test
//    fun `close`() {
//        val itemData = arrayListOf("name", "category", "room").toMutableList()
//
//        verify.(writer).close()
//    }
}