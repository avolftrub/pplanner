class Document {

    /** Unique identifier    */
    Long id

    /** Original file name */
    String sourceFileName

    /** Content type */
    String contentType

    static belongsTo = Project

    static constraints = {
        sourceFileName (nullable: true, size: 1..1024)
        contentType (nullable: true)
    }
}