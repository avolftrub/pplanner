class ProjectController {

    static scaffold = true

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

        [project: project]
    }
}
