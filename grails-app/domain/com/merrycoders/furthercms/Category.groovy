package com.merrycoders.furthercms

class Category {
    String name = ""
    Category parent
    String urlKey
    Page page
    Boolean isPublished = false // If true, it's viewable by the public
    Boolean isInMenu = true // If true, it's displayed in the main menu

    static constraints = {
        parent(nullable: true)
        page(nullable: true)
    }

    static mapping = {
        cache true
    }

    /**
     *
     * @return List of immediate children
     */
    List<Category> getChildren() {
        if (this?.id) Category.findAllByParent(this, [sort: "name"])
        else return []
    }

    /**
     *
     * @return List of all children at all levels
     */
    List<Category> getDeepChildren() {
        def list = []

        def children = getChildren()
        list.addAll(children)

        children.each { child ->
            list.addAll(child.getDeepChildren())
        }
        return list
    }
}
