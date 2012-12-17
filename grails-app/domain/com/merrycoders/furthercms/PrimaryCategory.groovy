package com.merrycoders.furthercms

class PrimaryCategory {
    Category category
    Integer displayOrder

    static constraints = {
    }

    static mapping = {
        cache true
        category cache: true, index: 'Category_Idx', fetch: 'join'
    }

    String toString() {
        category
    }
}
