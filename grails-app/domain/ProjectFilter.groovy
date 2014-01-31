

class ProjectFilter {

    /** First record to show    */
    Integer offset

    /** How many records to show   */
    Integer max

    /** Sort property name    */
    String sort

    /** Sort order    */
    String sortOrder

    /** Quick search tokens  */
    List<String> quickSearch

    Boolean archived = false

    static belongsTo = User

    static hasMany = [quickSearch: String]

    static constraints = {
        offset(nullable: true)
        max(nullable: true)
        sort(nullable: true)
    }
}
