import ru.appbio.ProjectStatus
import org.joda.time.LocalDate
import java.text.SimpleDateFormat

class ProjectController {

    def userService

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
        def list = Project.list()
        [projects: list, total: list.size()]
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

        project.properties = params

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
        bindData(project, params, [exclude: ['status']])

        project.validate()

        if (!project.hasErrors() && project.save()) {
            redirect(controller: 'project', action: 'show')
        } else {
            render(view: '/project/create', model: [project: project, isNew: true, user: userService.getCurrentUser()])
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
        [project: new Project(), isNew: true, user: userService.getCurrentUser()]
    }
}
