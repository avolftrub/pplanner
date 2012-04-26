import org.joda.time.LocalDate
import ru.appbio.ProjectStatus
import City

class Project {

    LocalDate createDate = new LocalDate()
    String customer
    String department
    City city
    String contactPerson
    String contactPhone
    String name
    LocalDate releaseDate
    BigDecimal sum
    ProjectStatus status = ProjectStatus.INTEREST_CONFIRMED
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
