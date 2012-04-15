package ru.appbio;

public enum ProjectStatus {
    INTEREST_CONFIRMED(1),
    SPECIFICATION_AGREED(2),
    BUDGET_CONFIRMED(3),
    TENDER_SPECIFICATION(4),
    TENDER(4),
    ORDER_IN(5),
    SHIPPED(6)

    /**
     * Type id
     */
    int id

    /**
     * Constructor
     *
     * @param id type id
     */
    ProjectStatus(id) {
        this.id = id
    }

    /**
     * Returns id
     */
    public int getId() {
        return id;
    }

    /**
     * Get enum value by its id
     */
    static ProjectStatus getById(int theId) {
        for (value in NoteType.values()) {
            if (value.id == theId) {
                return value;
            }
        }
        return null
    }
}
