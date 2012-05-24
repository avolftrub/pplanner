import org.joda.time.LocalDate
import ru.appbio.ProjectStatus
import City
import ru.appbio.LTProjectStatus

class Project {

    LocalDate createDate = new LocalDate()
    String customer
    String customerName
    String inn
    String department
    City city
    String contactPerson
    String contactPhone
    String contactEmail
    String name
    String productName
    LocalDate releaseDate
    BigDecimal sum
    ProjectStatus status = ProjectStatus.INTEREST_CONFIRMED

    LTProjectStatus approvalStatus = LTProjectStatus.NEW
    String comments
    LocalDate closeDate

    List userComments

    List documents

    static belongsTo = [dealer: Dealer]

    static hasMany = [userComments: Comment, documents: Document]

    static constraints = {
        customer(blank: false, size:  1..1024)
        customerName(blank: false, size:  1..1024)
        inn(blank: false, matches: '([0-9]{10}|[0-9]{12})')
        department(blank: false, size:  1..1024)
        contactPerson(blank: false, size:  1..512)
        contactPhone(blank: false, size:  1..128)
        contactEmail(blank: false, size: 1..128, email: true)
        name(blank: false, size:  1..1024)
        productName(blank: false, size:  1..1024)
        sum(min: new BigDecimal(0))
        comments(nullable: true, size: 1..2048)
        city(nullable: true)
        releaseDate(nullable: true, validator: { val, obj ->
            if (val) {
                if (new LocalDate().toDateTimeAtStartOfDay().compareTo(val.toDateTimeAtStartOfDay()) >= 0) {
                    ['releaseDate.before.currentdate']
                }
            }
        })
        closeDate(nullable: true)
    }
}
