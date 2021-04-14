package edu.calpoly.flipted.businesslogic.tasks.data

import edu.calpoly.flipted.businesslogic.tasks.data.blocks.TaskBlock

data class Page(
        val blocks: List<TaskBlock>
) {
        companion object {
                /**
                 * It is useful in the UI logic for every page to have a unique identifier.
                 * However, this UID only has to identify any particular block for the lifetime
                 * of the application.
                 * It doesn't need to be stored on the server. It's just a client-side logic
                 * semantic, not an actual part of the page data.
                 */
                private var newuid : Long = 20
                        get() {
                                field += 1
                                return field
                        }
        }
        val uid = newuid
}