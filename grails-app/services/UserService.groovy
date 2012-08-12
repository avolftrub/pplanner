import org.springframework.web.context.request.RequestAttributes
import org.springframework.web.context.request.RequestContextHolder
import org.apache.shiro.SecurityUtils
import ru.appbio.SearchParameters
import org.hibernate.criterion.Restrictions
import org.hibernate.criterion.LikeExpression

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
        def username = SecurityUtils.subject.principals.asList().get(0)
        def user = (username != null) ? User.findByUsername(username) : null

        if (user != null) {
            session.userId = user.id
        }
        return user
    }

    /** Finds users with the speicified parameters             */
    def findUsers(SearchParameters filter) {
        User.createCriteria().list(filter.getLimits()) {

            //quick search
            if (filter.quickSearch) {
                for (token in filter.quickSearch) {
                    instance.add(
                            Restrictions.disjunction().add(
                                    new LikeExpression('firstName', token)).
                                    add(new LikeExpression('middleName', token)).
                                    add(new LikeExpression('lastName', token)).
                                    add(new LikeExpression('email', token))
                    )
                }
            }
        }
    }
}
