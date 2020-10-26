package org.rvr.miro.coding.challenge

class WidgetStorageInMemory : WidgetStorage {

    private var storage: InMemoryStorage = InMemoryStorage()

    override fun findAll(): List<Widget?>? {
        val transform: (Node) -> Widget? = { it.value }
        return storage.findAll()?.map(transform)
    }

    override fun insert(coordinates: Coordinates, zIndex: Int, width: Int, height: Int): Widget {
        val widget = Widget(coordinates, zIndex, width, height)
        storage.insert(widget)
        return widget
    }

    override fun delete(widget: Widget): Boolean {
        return storage.delete(widget.zIndex)
    }

    override fun cleanUp(): Boolean {
        return storage.cleanUp()
    }
}
