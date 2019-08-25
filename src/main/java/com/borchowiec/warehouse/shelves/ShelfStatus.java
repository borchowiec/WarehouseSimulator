package com.borchowiec.warehouse.shelves;

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
