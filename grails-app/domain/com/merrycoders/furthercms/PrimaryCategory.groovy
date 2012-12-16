package com.merrycoders.furthercms

class PrimaryCategory {
    Category category
    Integer displayOrder

    static constraints = {
    }

    static mapping = {
        cache true
        category index: 'Category_Idx'
    }

    String toString() {
        category
    }
}
