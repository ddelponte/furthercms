package com.merrycoders.furthercms.exceptions

import com.merrycoders.furthercms.PageType

class UndeletablePageTypeException extends RuntimeException {

    String message = "Unable to delete page type at this time"
    PageType pageType

    String toString() {

        return message

    }
}
