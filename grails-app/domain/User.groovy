class User {
    String username
    String password
    String firstName
    String lastName
    ShiroRole role
    Dealer dealer
    
    static constraints = {
        username(nullable: false, blank: false)
        dealer(nullable: true)
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
