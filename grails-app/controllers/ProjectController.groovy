import ru.appbio.ProjectStatus
import org.joda.time.LocalDate
import java.text.SimpleDateFormat
import ru.appbio.SearchParameters

class ProjectController {

    def userService

    def projectService

    def show = {
        def project = Project.get(params.id)
        if (!project) {
            redirect(controller: 'project', action: 'list')
        }

        [project: project]
    }

    def edit = {
        def project = Project.get(params.id)
        if (!project) {
            redirect(controller: 'project', action: 'list')
        }

        [project: project, isNew: false]
    }

    def list = {
        def filter = prepareFilter()
        def results = projectService.findProjects(filter)
        [projects: results.list, total: results.totalCount, filter: filter]
    }

    def update = {
        def project = Project.get(params.id)
        if (!project) {
            redirect(controller: 'project', action: 'list')
        }

        if (params.releaseDate) {
            try {
                params.releaseDate = new LocalDate (new SimpleDateFormat("dd.MM.yyyy").parse(params.releaseDate).getTime())
            } catch (Exception ex) {
                params.releaseDate = null
            }
        }

        project.status = ProjectStatus.getById(params.status as Integer)
        project.dealer = Dealer.get(params.dealer)

//        bindData(disease, params, [exclude: ['mimNumbers', 'russianSynonyms', 'englisghSynonyms', 'mkbCodes']])
        bindData(project, params, [exclude: ['status', 'dealer']])


//        project.properties = params

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

//        bindData(disease, params, [exclude: ['mimNumbers', 'russianSynonyms', 'englisghSynonyms', 'mkbCodes']])
        bindData(project, params, [exclude: ['status', 'dealer']])

        project.validate()

        if (!project.hasErrors() && project.save()) {
            redirect(controller: 'project', action: 'show')
        } else {
            render(view: '/project/create', model: [project: project, isNew: true, currentUser: userService.getCurrentUser()])
        }
    }

    def delete = {
        def project = Project.get(params.id)
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
        def filter = prepareFilter()
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
        response.setHeader("Content-Disposition", "attachment; filename=projects.xlsx")

//        response.setContentLength((int) tmpExcelFile.length())
        projectService.exportToExcel(response.outputStream, filter)
        response.outputStream.flush()
    }


    private def prepareFilter() {
        if (!params."sort") {
            params.sort = "name"
            params.order = "asc"

        }

        def filter = new SearchParameters()

        bindData(filter, params)
        params.max = Math.min(params.int('max') ?: 20, 100)
        filter.bindLimits(params)
        return filter
    }
}
