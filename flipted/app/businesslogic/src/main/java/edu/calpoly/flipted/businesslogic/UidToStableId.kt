package edu.calpoly.flipted.businesslogic

class UidToStableId<T> {
    private val uidMap : MutableMap<T, Long> = mutableMapOf()
    private var nextStableId: Long = 0
        get() = field.also {
            field += 1
        }

    fun getStableId(uid: T): Long {
        if(!uidMap.containsKey(uid)) {
            uidMap[uid] = nextStableId
        }
        return uidMap[uid]!!
    }
}