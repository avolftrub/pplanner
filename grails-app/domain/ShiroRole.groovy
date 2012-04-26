class ShiroRole {

    transient public static final String ROLE_ADMIN = 'admin'
    transient public static final String ROLE_DEALER = 'dealer'

    String name

    static hasMany = [ users: User, permissions: String ]

    static constraints = {
        name(nullable: false, blank: false, unique: true)
    }
}
