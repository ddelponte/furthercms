package com.merrycoders.furthercms

class SecondaryNavAdminMenuItem {
    PrimaryNavAdminMenuItem primaryNavAdminMenuItem // This is the parent
    String titleMessageCode
    String titleDefault
    String controller = "admin"
    String action
    Integer displayOrder
    Date dateCreated
    Date lastUpdated

    static constraints = {
    }

    static mapping = {
        cache true
        controller index: "Controller_Idx"
        action index: "Action_Idx"
    }

    String toString() {
        titleDefault
    }
}
