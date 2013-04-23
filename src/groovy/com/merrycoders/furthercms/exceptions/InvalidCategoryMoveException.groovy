package com.merrycoders.furthercms.exceptions

import com.merrycoders.furthercms.Category

/**
 * This exception is thrown when:
 * 1.  Either the category or the parent are null
 * 2.  The parent is a child of the category
 */
class InvalidCategoryMoveException extends RuntimeException {
    String message = "Invalid Move"
    Category category
    Category parent

    String toString() {

        return message

    }
}
