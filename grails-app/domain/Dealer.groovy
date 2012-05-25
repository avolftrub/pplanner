import org.joda.time.LocalDate

class Dealer {

    Date dateCreated
    String name

    static hasMany = [user: User]

    static constraints = {
        name(blank: false, size: 1..512)
    }

    @Override
    public String toString() {
        return name
    }
}
