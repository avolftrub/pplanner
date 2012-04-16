import org.joda.time.LocalDate
import ru.appbio.ProjectStatus

class Project {

    LocalDate createDate
    String customer
    String department
    String city
    String contactPerson
    String contactPhone
    String name
    LocalDate releaseDate
    BigDecimal sum
    ProjectStatus status
    String comments
    LocalDate closeDate

    static belongsTo = [dealer: Dealer]


    static constraints = {
        customer(blank: false, size:  1..1024)
        department(blank: false, size:  1..1024)
        contactPerson(blank: false, size:  1..512)
        contactPhone(blank: false, size:  1..128)
        name(blank: false, size:  1..1024)
        sum(min: new BigDecimal(0))
        comments(nullable: true, size: 1..1024)
        city(nullable: true)
        releaseDate(nullable: true)
        closeDate(nullable: true)
    }
}
