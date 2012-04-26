import org.joda.time.LocalDate

class User {
    LocalDate createDate = new LocalDate()
    String username
    String password
    String password2
    String firstName
    String middleName
    String lastName
    ShiroRole role

    static belongsTo = [dealer: Dealer]

    static transients = ['password2']

    static constraints = {
        firstName(blank: false, size: 1..128)
        lastName(blank: false, size: 1..128)
        username(nullable: false, blank: false, email: true, unique: true)
        middleName(nullable: true, size: 1..128)
        dealer(nullable: true)
        password(size: 6..256, validator: {val, obj ->
            if (val != obj.password2) {
                ['passwords.mismatch']
            }
        })
    }

    transient def getName() {
        return "$firstName $lastName"
    }

    transient def isAdmin() {
        return role.name == ShiroRole.ROLE_ADMIN
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role=" + role +
                ", dealer=" + dealer?.name +
                '}';
    }
}
