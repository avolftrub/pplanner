

class City {

    String name
    Long countryId
    Long regionId

    static mapping = {
        id (column: 'city_id', generator: 'assigned')
        table(name: "city")
        version false
    }
    static constraints = {
        name(blank: false, size: 1..128)
    }
}
