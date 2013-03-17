package com.merrycoders.furthercms

class PrimaryCategoryService {

    public PrimaryCategory save(PrimaryCategory primaryCategory, Boolean flush = false) {
        if (!primaryCategory) return null

        if (!primaryCategory?.displayOrder) {
            def maxDisplayOrder = primaryCategory.siblings?.displayOrder ? primaryCategory.siblings?.displayOrder?.max() + 1 : 0
            primaryCategory?.displayOrder = maxDisplayOrder
        }

        primaryCategory.save(flush: flush)

    }
}
