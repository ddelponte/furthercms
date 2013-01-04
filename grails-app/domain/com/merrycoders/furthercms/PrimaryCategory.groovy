package com.merrycoders.furthercms

class PrimaryCategory {
    Category category
    Integer displayOrder
    Date dateCreated
    Date lastUpdated

    static constraints = {
    }

    static mapping = {
        cache true
        category cache: true, index: 'Category_Idx', fetch: 'join'
    }

    List<Category> getSiblings() {
        PrimaryCategory.list() - this
    }

    String toString() {
        category
    }
}
