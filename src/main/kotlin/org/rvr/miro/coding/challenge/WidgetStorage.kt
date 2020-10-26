package org.rvr.miro.coding.challenge

import java.time.LocalDateTime
import java.util.*

interface WidgetStorage {
    fun findAll(): List<Widget?>?

    fun insert(coordinates: Coordinates, zIndex: Int, width: Int, height: Int): Widget

    fun delete(widget: Widget): Boolean

    fun cleanUp(): Boolean
}

data class Widget(
    var coordinates: Coordinates,
    var zIndex: Int,
    var width: Int,
    var height: Int,
    var id: String = UUID.randomUUID().toString(),
    var lastModificationDate: LocalDateTime = LocalDateTime.now(),
)

data class Coordinates(
    var x: Int,
    var y: Int
)
