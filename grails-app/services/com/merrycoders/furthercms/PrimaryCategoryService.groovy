package com.merrycoders.furthercms

class PrimaryCategoryService {

    PrimaryCategory save(PrimaryCategory primaryCategory, Boolean flush = false) {
        if (!primaryCategory) return null

        if (!primaryCategory?.displayOrder) {
            def maxDisplayOrder = primaryCategory.siblings?.displayOrder ? primaryCategory.siblings?.displayOrder?.max() + 1 : 0
            primaryCategory?.displayOrder = maxDisplayOrder
        }

        primaryCategory.save(flush: flush)

    }

    void delete(List<PrimaryCategory> primaryCategories) {
        primaryCategories.each { primaryCategory ->
            primaryCategory.category = null
            primaryCategory.delete(flush: true)
        }
    }
}
