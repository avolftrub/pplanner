package ru.appbio;

public enum LTProjectStatus {
    NEW(1),
    APPROVED(2),
    REJECTED(3)

    /**
     * Type id
     */
    int id

    /**
     * Constructor
     *
     * @param id type id
     */
    LTProjectStatus(id) {
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
    static LTProjectStatus getById(int theId) {
        for (value in LTProjectStatus.values()) {
            if (value.id == theId) {
                return value;
            }
        }
        return null
    }
}
