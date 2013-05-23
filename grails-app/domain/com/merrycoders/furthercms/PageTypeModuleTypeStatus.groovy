package com.merrycoders.furthercms

/**
 * This status determines what module types are available to users when creating a new page of a certain type.
 * Active ModuleTypes will be displayed in the content area of a new Page
 * Available ModuleTypes will may be added to the new page, but aren't in the content area by default
 * Unavailable ModuleTypes are hidden from the user
 */
public enum PageTypeModuleTypeStatus {
    ACTIVE("Active"),
    AVAILABLE("Available"),
    UNAVAILABLE("Unavailable")

    String name
    String description
    Date lastUpdated
    Date dateCreated

    PageTypeModuleTypeStatus(String name) {
        this.name = name
    }

    static constraints = {
        description nullable: true
    }

    String toString() {
        name
    }

}