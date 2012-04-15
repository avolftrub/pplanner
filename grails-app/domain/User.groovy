class User {
    String username
    String passwordHash
    String firstName
    String lastName
    ShiroRole role
    
    static constraints = {
        username(nullable: false, blank: false)
    }

    transient def getName() {
        return "$firstName $lastName"
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                "role=" + role +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
