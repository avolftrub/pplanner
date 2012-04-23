import org.joda.time.LocalDate
import org.springframework.web.servlet.support.RequestContextUtils
import com.ibm.icu.text.SimpleDateFormat as SDF
import com.ibm.icu.util.ULocale as UL

public class MainTagLib {

    /** User operations */
    def userService

    def messageSource

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

    def deleteDialog = { attrs, body ->
        def clazz = attrs.remove('class') as String
        if (!clazz) {
            clazz = 'Warning Alert'
        } else {
            if (!clazz.contains('Warning')) {
                clazz += ' Warning'
            }
            if (!clazz.contains('Alert')) {
                clazz += ' Alert'
            }
        }
        attrs.put('class', clazz)
        out << dialog(attrs, body)
    }

    def dialog = { attrs, body ->
        def id = attrs.id
        def title = attrs.title
        def clazz = attrs.class ? attrs.class : 'Alert'
        def showEffect = attrs.show
        def hideEffect = attrs.hide
        def width = attrs.width ? attrs.width : '420'
        def height = attrs.height
        def onOpen = attrs.onOpen
        def onClose = attrs.onClose
        clazz = clazz[0..<1].toUpperCase() + clazz[1..<clazz.length()]
        if (!title) {
            def titleKey = attrs.titleKey
            if (titleKey) {
                title = messageSource.getMessage(titleKey, null, RequestContextUtils.getLocale(request))
            }
        }
        out << '<div id="' + id + '" class="DialogContainer" style="display: none;"'
        if (title) {
            out << ' title="' + title.encodeAsHTML() + '"'
        }
        out << '>'
        out << body()
        out << '</div>'
        out << '<script type="text/javascript">'
        out << '$(document).ready(function() {'
        out << '$(\'#' + id + '\').dialog({'
        out << 'dialogClass: \'' + clazz + 'Window\', autoOpen: false, resizable: false, width: ' + width + ', minHeight: 0, '
        out << "open: function(event,ui) {$onOpen},"
        out << "close: function(event,ui) {$onClose},"
        if (height) {
            out << 'height: ' + height + ', '
        }
        out << 'modal: true'
        if (showEffect) {
            out << ',show:\'' + showEffect + '\''
        }
        if (hideEffect) {
            out << ',hide:\'' + hideEffect + '\''
        }
        out << '});'
        out << '});'
        out << '</script>'
    }

    def renderFieldErrors = { attrs, body ->
        def codec = attrs.codec ?: 'HTML'
        if (codec == 'none') codec = ''

        def bean = attrs.remove('bean')
        def field = attrs.remove('field')
        def nowrap = attrs.remove('nowrap')
        def mc = GroovySystem.metaClassRegistry.getMetaClass(bean.getClass())
        def errors
        if (mc.hasProperty(bean, 'errors')) {
            errors = bean.errors
        }

        if (errors?.hasFieldErrors(field)) {
            if (!nowrap) {
                out << "<span class=\"InputErrors\">"
            }
            out << "<ul>"
            errors.getFieldErrors(field)?.each {
                out << "<li>${message(error: it, encodeAs: codec)}</li>"
            }
            out << "</ul>"
            if (!nowrap) {
                out << "</span>"
            }
        }

    }
}