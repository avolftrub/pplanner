public class MainTagLib {

    /** User operations */
    def userService

    /** Renders header for pages */
    def renderHeader = {
        def user = userService.getCurrentUser()
        out << render(template: '/templates/header', model: [user: user])
    }
}