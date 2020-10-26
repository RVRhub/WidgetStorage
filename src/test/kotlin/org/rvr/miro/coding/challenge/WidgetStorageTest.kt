package org.rvr.miro.coding.challenge

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class   WidgetStorageTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    /**
     * Insert 16 widgets with diff z-Index
     */
    @Test
    fun givenWidgets_whenFillStorage_thenAllWidgetInserted() {
        val storage: WidgetStorage = WidgetStorageInMemory()

        val zIndexs = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
        for (zIndex in zIndexs) {
            var coordinates = Coordinates(0, 0)
            storage.insert(coordinates, zIndex, 100, 100)
        }

        assertEquals(16, storage.findAll()?.size)
    }

    /**
     * Delete first widget from storage
     */
    @Test
    fun givenWidgets_whenDeleteWidget_thenOneWidgetDeleted() {
        val storage: WidgetStorage = WidgetStorageInMemory()

        val zIndexs = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
        for (zIndex in zIndexs) {
            var coordinates = Coordinates(0, 0)
            storage.insert(coordinates, zIndex, 100, 100)
        }

        assertEquals(16, storage.findAll()?.size)

        val firstWidget = storage.findAll()?.first()
        firstWidget?.let { storage.delete(it) }

        assertEquals(15, storage.findAll()?.size)
    }

    /**
     * Insert widget with same z-Index and verify shifting
     * Clean up all storage
     * Insert widget with different z-Index and verify sorted values
     */
    @Test
    fun givenWidgets_whenInsertWidget_thenCorrectSorting() {
        val storage: WidgetStorage = WidgetStorageInMemory()

        val coordinates = Coordinates(0, 0)
        var zIndexs = arrayOf(1, 2, 3)
        for (zIndex in zIndexs) {
            storage.insert(coordinates, zIndex, 100, 100)
        }

        assertEquals(listOf(1, 2, 3), storage.findAll()?.map { it?.zIndex })
        storage.insert(coordinates, 2, 100, 100)
        assertEquals(listOf(1, 2, 3, 4), storage.findAll()?.map { it?.zIndex })

        storage.cleanUp()

        zIndexs = arrayOf(1,5,6)
        for (zIndex in zIndexs) {
            storage.insert(coordinates, zIndex, 100, 100)
        }

        assertEquals(listOf(1, 5, 6), storage.findAll()?.map { it?.zIndex })
        storage.insert(coordinates, 2, 100, 100)
        assertEquals(listOf(1, 2, 5, 6), storage.findAll()?.map { it?.zIndex })
    }
}
