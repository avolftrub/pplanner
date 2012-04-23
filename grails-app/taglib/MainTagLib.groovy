import org.joda.time.LocalDate
import org.springframework.web.servlet.support.RequestContextUtils
import com.ibm.icu.text.SimpleDateFormat as SDF
import com.ibm.icu.util.ULocale as UL

public class MainTagLib {

    /** User operations */
    def userService

    /** Renders header for pages */
    def renderHeader = {
        def user = userService.getCurrentUser()
        out << render(template: '/templates/header', model: [user: user])
    }

    /** Formats JODA objects as "21.08.2011"              */
    def formatPlainDate = {attrs ->
        if (!attrs.value) { return }
        def dt = attrs.value instanceof LocalDate ? attrs.value.toDateTimeAtStartOfDay().toDate() : attrs.value.toDate()
        def myLocale = UL.forLocale(RequestContextUtils.getLocale(request))
        out << new SDF("dd.MM.yyyy", myLocale).format(dt)
    }
}