import org.joda.time.LocalDate

class Dealer {

    LocalDate createDate = new LocalDate()
    String name
    String code

    static hasMany = [user: User]

    static constraints = {
        name(blank: false)
        code(nullable: true)
    }

    @Override
    public String toString() {
        return name
    }
}
