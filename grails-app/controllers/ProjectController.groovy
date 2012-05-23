import ru.appbio.ProjectStatus
import org.joda.time.LocalDate
import java.text.SimpleDateFormat
import ru.appbio.SearchParameters
import ru.appbio.ProjectSearchParameters
import grails.converters.JSON
import ru.appbio.utils.Highlighter
import ru.appbio.LTProjectStatus

class ProjectController {

    def userService

    def projectService

    def show = {
        def projectId = params.long("id")
        if (!projectId) {
            redirect(controller: 'project', action: 'list')
        }

        def project = projectService.findProjects(prepareFilter(params))[0]
        if (!project) {
            redirect(controller: 'project', action: 'list')
        }

        [project: project, currentUser: userService.getCurrentUser()]
    }

    def edit = {
        def projectId = params.long("id")
        if (!projectId) {
            redirect(controller: 'project', action: 'list')
        }

        def project = projectService.findProjects(prepareFilter(params))[0]
        if (!project) {
            redirect(controller: 'project', action: 'list')
        }

        [project: project, isNew: false]
    }

    def list = {
        def filter = prepareFilter(params)
        def results = projectService.findProjects(filter)

        def qsStr = new StringBuffer()
        filter.quickSearch.each {
            qsStr.append(it).append(" ")
        }

        [projects: results.list, total: results.totalCount, filter: filter, quickSearchStr: qsStr.toString().trim()]
    }

    def lookup = {
        def filter = prepareFilter(params)
        def results = projectService.findProjects(filter)

        def qsStr = new StringBuffer()
        filter.quickSearch.each {
            qsStr.append(it).append(" ")
        }

        render(view: 'list', model: [projects: results.list, total: results.totalCount, filter: filter, quickSearchStr: qsStr.toString().trim()])
    }

    def update = {
        def projectId = params.long("id")
        if (!projectId) {
            redirect(controller: 'project', action: 'list')
        }

        def project = projectService.findProjects(prepareFilter(params))[0]
        if (!project) {
            redirect(controller: 'project', action: 'list')
        }

        project.status = ProjectStatus.getById(params.status as Integer)

        def currentUser = userService.getCurrentUser()
        if (currentUser.isAdmin()) {
            project.dealer = Dealer.get(params.dealer)
        } else {
            project.dealer = currentUser.dealer
        }

        project.dealer = Dealer.get(params.dealer)

        if (params.city?.trim()?.length() != 0){
            project.city = City.findByName(params.city?.trim())
        }

        bindData(project, params, [exclude: ['status', 'dealer', 'city']])

        project.validate()

        if (!project.hasErrors() && project.save()) {
            redirect(controller: 'project', action: 'show', params: [id: project.id])
        } else {
            render(view: '/project/edit', model: [project: project])
        }
    }

    def save = {
        def project = new Project()

        project.status = ProjectStatus.getById(params.status as Integer)

        def currentUser = userService.getCurrentUser()
        if (currentUser.isAdmin()) {
            project.dealer = Dealer.get(params.dealer)
        } else {
            project.dealer = currentUser.dealer
        }

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
        def projectId = params.long("id")
        if (!projectId) {
            redirect(controller: 'project', action: 'list')
        }

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

    def approve = {
        def projectId = params.long("id")
        if (!projectId) {
            redirect(controller: 'project', action: 'list')
        }

        def project = projectService.findProjects(prepareFilter(params))[0]
        if (!project) {
            redirect(controller: 'project', action: 'list')
        }

        project.approvalStatus = LTProjectStatus.APPROVED

        project.save()

        redirect(controller: 'project', action: 'show', id:  project.id)
    }

    def reject = {
        def projectId = params.long("id")
        if (!projectId) {
            redirect(controller: 'project', action: 'list')
        }

        def project = projectService.findProjects(prepareFilter(params))[0]
        if (!project) {
            redirect(controller: 'project', action: 'list')
        }

        project.approvalStatus = LTProjectStatus.REJECTED

        project.save()

        redirect(controller: 'project', action: 'show', id:  project.id)
    }

    def addComment = {
        def projectId = params.long("projectId")
        if (!projectId) {
            redirect(controller: 'project', action: 'list')
        }

        def project = projectService.findProjects(prepareFilter(params))[0]
        if (!project) {
            redirect(controller: 'project', action: 'list')
        }

        def commentInstance = new Comment(params)
        commentInstance.author = userService.getCurrentUser()
        commentInstance.createDate = new LocalDate()
        project.addToUserComments(commentInstance)

        project.save()

        redirect(controller: 'project', action: 'show', id:  project.id)
    }

    def deleteComment = {
        def projectId = params.long("projectId")
        if (!projectId) {
            redirect(controller: 'project', action: 'list')
        }

        def project = projectService.findProjects(prepareFilter(params))[0]
        if (!project) {
            redirect(controller: 'project', action: 'list')
        }

        def commentId = params.long("id")

        if (!commentId) {
            redirect(controller: 'project', action: 'list')
        }

        def commentInstance = Comment.get(commentId)
        if (!commentInstance || commentInstance.author != userService.currentUser) {
            redirect(controller: 'project', action: 'list')
        } else {
            project.removeFromUserComments(commentInstance)
            project.save()
        }

        redirect(controller: 'project', action: 'show', id:  project.id)
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

        def result = City.executeQuery(query, params)

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
