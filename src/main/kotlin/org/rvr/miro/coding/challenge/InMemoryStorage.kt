package org.rvr.miro.coding.challenge

class InMemoryStorage {
    private var root: Node? = null

    fun insert(widget: Widget) {
        root = insert(root, widget)
    }

    fun delete(key: Int): Boolean {
        return delete(root, key) != null
    }

    fun getRoot(): Node? {
        return root
    }

    fun cleanUp():Boolean {
        root = null
        return true
    }

    fun findAll(): Array<Node>? {
        return root?.let { findAll(it) }
    }

    private fun findAll(node: Node): Array<Node> {
        val a = node.left?.let { findAll(it) } ?: emptyArray()
        val b = node.right?.let { findAll(it) } ?: emptyArray()
        return a + arrayOf(node) + b
    }

    fun find(key: Int): Node? {
        var current: Node? = root
        while (current != null) {
            if (current.key == key) {
                break
            }
            current = if (current.key < key) current.right else current.left
        }
        return current
    }

    private fun updateHeight(n: Node) {
        n.height = 1 + Math.max(height(n.left), height(n.right))
    }

    private fun height(n: Node?): Int {
        return n?.height ?: -1
    }

    private fun getBalance(n: Node?): Int {
        return if (n == null) 0 else height(n.right) - height(n.left)
    }

    private fun insert(node: Node?, widget: Widget): Node? {
        if (node == null) {
            return Node(widget.zIndex, widget)
        } else if (node.key > widget.zIndex) {
            node.left = insert(node.left, widget)
        } else if (node.key < widget.zIndex) {
            node.right = insert(node.right, widget)
        } else {
            widget.zIndex = widget.zIndex + 1
            node.right = insert(node.right, widget)
        }
        return rebalance(node)
    }

    private fun delete(node: Node?, key: Int): Node? {
        var node = node
        if (node == null) {
            return node
        } else if (node.key > key) {
            node.left = delete(node.left, key)
        } else if (node.key < key) {
            node.right = delete(node.right, key)
        } else {
            if (node.left == null || node.right == null) {
                node = if (node.left == null) node.right else node.left
            } else {
                val mostLeftChild: Node = mostLeftChild(node.right!!)
                node.key = mostLeftChild.key
                node.right = delete(node.right, node.key)
            }
        }
        if (node != null) {
            node = rebalance(node)
        }
        return node
    }

    private fun mostLeftChild(node: Node): Node {
        var current: Node? = node
        /* loop down to find the leftmost leaf */while (current!!.left != null) {
            current = current.left
        }
        return current
    }

    private fun rebalance(z: Node): Node? {
        var z = z
        updateHeight(z)
        val balance: Int = getBalance(z)
        if (balance > 1) {
            if (height(z.right!!.right) > height(z.right!!.left)) {
                z = rotateLeft(z)
            } else {
                z.right = rotateRight(z.right!!)
                z = rotateLeft(z)
            }
        } else if (balance < -1) {
            if (height(z.left!!.left) > height(z.left!!.right)) z = rotateRight(z) else {
                z.left = rotateLeft(z.left!!)
                z = rotateRight(z)
            }
        }
        return z
    }

    private fun rotateLeft(y: Node): Node {
        val x = y.right
        val z = x!!.left
        x.left = y
        y.right = z
        updateHeight(y)
        updateHeight(x)
        return x
    }

    private fun rotateRight(y: Node): Node {
        val x = y.left
        val z = x!!.right
        x.right = y
        y.left = z
        updateHeight(y)
        updateHeight(x)
        return x
    }
}

data class Node(
    var key: Int,
    var value: Widget? = null,
    var height: Int? = null,
    var left: Node? = null,
    var right: Node? = null
)
