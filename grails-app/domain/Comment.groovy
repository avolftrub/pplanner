import org.joda.time.LocalDate

class Comment {
    String text
    LocalDate createDate
    User author

    static belongsTo = Project

    static constraints = {
        text(blank: false, size:  1..4096)
    }
}
