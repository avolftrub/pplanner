import ru.appbio.ProjectStatus
import org.joda.time.LocalDate
import java.text.SimpleDateFormat
import ru.appbio.SearchParameters
import ru.appbio.ProjectSearchParameters
import grails.converters.JSON
import ru.appbio.utils.Highlighter

class ProjectController {

    def userService

    def projectService

    def show = {
        def project = projectService.findProjects(prepareFilter(params))[0]
            if (!project) {
            redirect(controller: 'project', action: 'list')
        }

        [project: project]
    }

    def edit = {
        def project = projectService.findProjects(prepareFilter(params))[0]
        if (!project) {
            redirect(controller: 'project', action: 'list')
        }

        [project: project, isNew: false]
    }

    def list = {
        def filter = prepareFilter(params)
        def results = projectService.findProjects(filter)
        [projects: results.list, total: results.totalCount, filter: filter]
    }

    def update = {
        def project = projectService.findProjects(prepareFilter(params))[0]
        if (!project) {
            redirect(controller: 'project', action: 'list')
        }

        project.status = ProjectStatus.getById(params.status as Integer)
        project.dealer = Dealer.get(params.dealer)

        if (params.city?.trim()?.length() != 0){
            project.city = City.findByName(params.city?.trim())
        }

        bindData(project, params, [exclude: ['status', 'dealer', 'city']])

        project.validate()

        if (!project.hasErrors() && project.save()) {
            redirect(controller: 'project', action: 'show')
        } else {
            render(view: '/project/edit', model: [project: project])
        }
    }

    def save = {
        def project = new Project()

        project.status = ProjectStatus.getById(params.status as Integer)
        project.dealer = Dealer.get(params.dealer)
        if (params.city?.trim()?.length() != 0){
            project.city = City.findByName(params.city?.trim())
        }

        bindData(project, params, [exclude: ['status', 'dealer', 'city']])

        project.validate()

        if (!project.hasErrors() && project.save()) {
            redirect(controller: 'project', action: 'show')
        } else {
            render(view: '/project/create', model: [project: project, isNew: true, currentUser: userService.getCurrentUser()])
        }
    }

    def delete = {
        def project = projectService.findProjects(prepareFilter(params))[0]
        if (!project) {
            redirect(controller: 'project', action: 'list')
        }

        project.delete()

        redirect(controller: 'project', action: 'list')
    }

    def create = {
        [project: new Project(), isNew: true, currentUser: userService.getCurrentUser()]
    }

    /** Exports projects list to excel */
    def exportToExcel = {
        def filter = prepareFilter(params)
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
        response.setHeader("Content-Disposition", "attachment; filename=projects.xlsx")

//        response.setContentLength((int) tmpExcelFile.length())
        projectService.exportToExcel(response.outputStream, filter)
        response.outputStream.flush()
    }

    def lookupCity = {
        def q = params.term?.replaceAll(/[\*\\\+\-\!\(\)\:\^\{\}\~\?\"\'\]\[]/, " ")?.trim()
        def terms = []
        for (token in q?.split(" ")) {
            if (token.trim().length() > 0) {
                terms << token.trim()
            }
        }


        def query = "from City ${terms ? ' where ' : ''}"

        def params = []
        terms.eachWithIndex { nextTerm, index ->
            query += " ( lower(name) like ? escape '!' ) "
            params << '%' + projectService.escape(nextTerm.toLowerCase(), '!' as char) + '%'
            if (index < terms.size() - 1) {
                query += " and "
            }
        }

        query += " order by name "

        log.error "TTTTT:Searching for cities: $query"
        params.each {
            log.error "HHHHH: $it"
        }

        def result = City.executeQuery(query, params)

        log.error "TTTTT:Found results:"

        params.each {
            log.error "HHHHH: $it"
        }


//        def result = projectService.findCities(terms)

//        def data = []
//        result.each {
//            data << [it.name]
//            data << [label: Highlighter.highlight(it.name, terms, '<strong>', '</strong>'), value: it.name]
//        }




        render(contentType: 'text/json') {
            result.collect {it.name}
        }
    }


    private def prepareFilter(params) {
        if (!params."sort") {
            params.sort = "name"
            params.order = "asc"

        }

        def filter = new ProjectSearchParameters()

        filter.projectId = params.long("id")
        filter.dealerId = userService.currentUser?.dealer?.id

        bindData(filter, params)
        params.max = Math.min(params.int('max') ?: 20, 100)
        filter.bindLimits(params)
        return filter
    }
}
