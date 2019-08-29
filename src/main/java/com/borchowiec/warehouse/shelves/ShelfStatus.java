package com.borchowiec.warehouse.shelves;

/**
 * Describes shelves e.g. if shelf has product or if it's waiting for product. It can be helpful e.g. if you want to get
 * shelves that are empty.
 * @author Patryk Borchowiec
 */
public enum ShelfStatus {
    EMPTY("Empty"),
    HAS_PRODUCT("Has product"),
    WAITING_FOR_IMPORT("Waiting for import"),
    WAITING_FOR_EXPORT("Waiting for export");

    private final String displayName;

    ShelfStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
