import org.springframework.web.context.request.RequestAttributes
import org.springframework.web.context.request.RequestContextHolder
import org.apache.shiro.SecurityUtils

/**
 * Manage users.
 */
public class UserService {

    static transactional = false

    /** Returns user which is currently logged-in */
    User getCurrentUser() {
        def userId = RequestContextHolder.requestAttributes.getAttribute('userId', RequestAttributes.SCOPE_SESSION)
        userId != null ? User.get(userId as Long) : null
    }

    /** Returns user with common uperations upon login */
    User getUserWithCommonLogin(session) {
        if (SecurityUtils.subject.isAuthenticated()) {
            def username = SecurityUtils.subject.principals.asList().get(0)
            def user = (username != null) ? User.findByUsername(username) : null
            if (user != null) {
                session.userId = user.id
            }
            return user
        }
        return null
    }
}