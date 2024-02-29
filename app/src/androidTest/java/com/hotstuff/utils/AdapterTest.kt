package com.hotstuff.utils

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class AdapterTest {
    private lateinit var context: Context
    private lateinit var items: ArrayList<com.hotstuff.models.Item>
    @Before
    fun initTest() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        items = ArrayList()
        items.add(
            com.hotstuff.models.Item(
                1, 1, "TV", 1, "Electronics", "Living Room",
                "Brand A", 0.00, null, null
            )
        )
        items.add(
            com.hotstuff.models.Item(
                2, 1, "Stool", 2, "Furniture", "Kitchen",
                "Brand B", 0.00, null, null
            )
        )
        items.add(
            com.hotstuff.models.Item(
                3, 1, "Couch", 2, "Furniture", "Living Room",
                "Brand C", 0.00, null, null
            )
        )
    }
    @Test
    fun `WHEN searchClear is called THEN items is cleared`() {
        val adapter = Adapter(items)
        assertEquals(3, items.size)
        // TODO: Investigate warning when time
        adapter.searchClear()
        assertEquals(0, items.size)
    }
    @Test
    fun `GIVEN item position AND an updated array WHEN searchInsert is called THEN items is updated`() {
        val adapter = Adapter(items)
        assertEquals(3, items.size)
        val updatedItems = items
        val newItem = com.hotstuff.models.Item(
            4, 1, "Couch", 2, "Furniture",
            "Living Room", "Brand C", 0.00, null, null
        )
        updatedItems.add(newItem)
        adapter.searchInsert(updatedItems.size, updatedItems)
        assertEquals(items[updatedItems.size - 1], newItem)
    }
}