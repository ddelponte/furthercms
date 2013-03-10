package com.merrycoders.furthercms

class SecondaryAdminMenuItem {
    PrimaryAdminMenuItem primaryNavAdminMenuItem // This is the parent
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
        controller index: "Primary_Nav_Admin_Menu_Item_Controller_Idx"
        action index: "Primary_Nav_Admin_Menu_Item_Action_Idx"
    }

    String toString() {
        titleDefault
    }
}
