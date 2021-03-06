import ru.appbio.ProjectStatus
import org.joda.time.LocalDate
import java.text.SimpleDateFormat
import ru.appbio.SearchParameters
import ru.appbio.ProjectSearchParameters
import grails.converters.JSON
import ru.appbio.utils.HighLighter
import ru.appbio.LTProjectStatus
import org.springframework.web.multipart.MultipartHttpServletRequest

class ProjectController {

    def userService

    def projectService

    def documentService

    def show = {
        def projectId = params.long("id")
        if (!projectId) {
            redirect(controller: 'project', action: 'list')
            return
        }

        def filter =  prepareFilter(params)
        if (params.boolean("showArchived")) {
            filter.archived = true
        }

        def project = projectService.findProjects(filter, true)[0]
        if (!project) {
            redirect(controller: 'project', action: 'list')
            return
        }

        [project: project, currentUser: userService.getCurrentUser()]
    }

    def edit = {
        def projectId = params.long("id")
        if (!projectId) {
            redirect(controller: 'project', action: 'list')
            return
        }

        def currentUser = userService.getCurrentUser()
        def project = projectService.findProjects(prepareFilter(params))[0]
        if (!project || (project.dealer != currentUser.dealer)) {
            redirect(controller: 'project', action: 'list')
            return
        }

        [project: project, isNew: false]
    }

    def list = {
        def user = userService.currentUser
        user.projectFilter?.delete()
        user.projectFilter = null
        user.save()

        def filter = prepareFilter(params)
        def results = projectService.findProjects(filter, true)

        def qsStr = new StringBuffer()
        filter.quickSearch.each {
            qsStr.append(it).append(" ")
        }

        [projects: results.list, total: results.totalCount, filter: filter, quickSearchStr: qsStr.toString().trim(), currentUser: user]
    }

    def backToList = {
        def user = userService.getCurrentUser()
        def prjFilter = user.projectFilter
        def model = [:]
        if (prjFilter) {
            model.max = prjFilter.max?.toString() ?: ""
            model.offset = prjFilter.offset?.toString() ?: ""
            model.sort = prjFilter.sort
            model.order = prjFilter.sortOrder
            model.archived = prjFilter.archived
            def q = new StringBuffer()
            prjFilter.quickSearch.each {
                q.append(it).append(" ")
            }
            model.q = q.toString().trim()
        } else {
            model.archived = params.boolean("archived")
        }

        redirect(controller: "project", action: "lookup", params: model)
    }


    def lookup = {
        def user = userService.currentUser
        def filter = prepareFilter(params)
        if (filter.quickSearch?.size() > 0) {
            user.projectFilter?.delete()
            def prjFilter = new ProjectFilter()
            prjFilter.max  = filter.max ?: null
            prjFilter.offset = filter.offset ?: null
            prjFilter.sort = filter.sort
            prjFilter.sortOrder = filter.order
            prjFilter.quickSearch = filter.quickSearch
            prjFilter.archived = filter.archived
            prjFilter.save()

            user.projectFilter = prjFilter
        } else {
            user.projectFilter?.delete()
            user.projectFilter = null

        }
        user.save()

        def results = projectService.findProjects(filter, true)

        def qsStr = new StringBuffer()
        filter.quickSearch.each {
            qsStr.append(it).append(" ")
        }
        render(view: params.boolean("archived") ? "listArchived" : 'list', model: [projects: results.list, total: results.totalCount, filter: filter, quickSearchStr: qsStr.toString().trim(), currentUser: user, params: params])
    }

    def update = {
        def projectId = params.long("id")
        if (!projectId) {
            redirect(controller: 'project', action: 'list')
            return
        }

        def currentUser = userService.getCurrentUser()
        def project = projectService.findProjects(prepareFilter(params))[0]
        if (!project || (project.dealer != currentUser.dealer)) {
            redirect(controller: 'project', action: 'list')
            return
        }

        project.status = ProjectStatus.getById(params.status as Integer)

        if (currentUser.isAdmin()) {
            project.dealer = Dealer.get(params.dealer)
        } else {
            project.dealer = currentUser.dealer
        }

        project.dealer = Dealer.get(params.dealer)

        if (params.city?.trim()?.length() != 0){
            project.city = City.findByName(params.city?.trim())
        }

        project.releaseDate = (params.releaseDate?.trim()?.length() > 0) ? LocalDate.parse(params.releaseDate?.trim()) : null
        project.closeDate = (params.closeDate?.trim()?.length() > 0) ? LocalDate.parse(params.closeDate?.trim()) : null

        bindData(project, params, [exclude: ['status', 'dealer', 'city', 'releaseDate', 'closeDate']])

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

        project.releaseDate = (params.releaseDate?.trim()?.length() > 0) ? LocalDate.parse(params.releaseDate?.trim()) : null
        project.closeDate = (params.closeDate?.trim()?.length() > 0) ? LocalDate.parse(params.closeDate?.trim()) : null

        bindData(project, params, [exclude: ['status', 'dealer', 'city', 'releaseDate', 'closeDate']])

        project.validate()

        if (project.releaseDate && new LocalDate().toDateTimeAtStartOfDay().compareTo(project.releaseDate.toDateTimeAtStartOfDay()) >= 0) {
            project.errors.rejectValue('releaseDate', 'releaseDate.before.currentdate')
        }

        if (!project.hasErrors() && project.save()) {
            redirect(controller: 'project', action: 'show', id: project.id)
        } else {
            render(view: '/project/create', model: [project: project, isNew: true, currentUser: userService.getCurrentUser()])
        }
    }

    def delete = {
        def projectId = params.long("id")
        if (!projectId) {
            redirect(controller: 'project', action: 'list')
            return
        }

        def project = projectService.findProjects(prepareFilter(params))[0]
        def currentUser = userService.getCurrentUser()
        if (!project || (project.dealer != currentUser.dealer)) {
            redirect(controller: 'project', action: 'list')
            return
        }

        project.delete()

        flash.message = message(code: 'project.deleted', args: [project.name])
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
            return
        }

        def project = projectService.findProjects(prepareFilter(params))[0]

        project.approvalStatus = LTProjectStatus.APPROVED

        if (!project.save()) {
            flash.message = message(code: "project.action.approve.failed")
        }

        redirect(controller: 'project', action: 'show', id:  project.id)
    }

    def reject = {
        def projectId = params.long("id")
        if (!projectId) {
            redirect(controller: 'project', action: 'list')
            return
        }

        def project = projectService.findProjects(prepareFilter(params))[0]

        project.approvalStatus = LTProjectStatus.REJECTED

        if (!project.save()) {
            flash.message = message(code: "project.action.approve.failed")
        }

        redirect(controller: 'project', action: 'show', id:  project.id)
    }

    def addComment = {
        def projectId = params.long("projectId")
        if (!projectId) {
            redirect(controller: 'project', action: 'list')
            return
        }

        def project = projectService.findProjects(prepareFilter(params))[0]
        if (!project) {
            redirect(controller: 'project', action: 'list')
            return
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
            return
        }

        def project = projectService.findProjects(prepareFilter(params))[0]
        if (!project) {
            redirect(controller: 'project', action: 'list')
            return
        }

        def commentId = params.long("id")

        if (!commentId) {
            redirect(controller: 'project', action: 'list')
            return
        }

        def commentInstance = Comment.get(commentId)
        if (!commentInstance || commentInstance.author != userService.currentUser) {
            redirect(controller: 'project', action: 'list')
            return
        } else {
            project.removeFromUserComments(commentInstance)
            project.save()
        }

        flash.message = message(code: 'comment.deleted')
        redirect(controller: 'project', action: 'show', id:  project.id)
    }

    /** Uploads document */
    def uploadDocument = {
        def projectId = params.long("projectId")
        if (!projectId) {
            redirect(controller: 'project', action: 'list')
            return
        }

        def currentUser = userService.getCurrentUser()

        def project = projectService.findProjects(prepareFilter(params))[0]
        if (!currentUser.isAdmin()) {
            if (!project || (currentUser.dealer && project.dealer != currentUser.dealer)) {
                redirect(controller: 'project', action: 'list')
                return
            }
        }

        def file = request instanceof MultipartHttpServletRequest ? request.getFile('file') : null
        if (file.size > 0) {
            Document.withTransaction { status ->
                def doc = new Document(params)

                if (file) {
                    doc.contentType = file.contentType
                }

                doc.validate()
                project.addToDocuments(doc)

                if (!doc.hasErrors() && project.save(flush: true)) {
                    if (!documentService.saveDocument(doc, file)) {

                        doc.errors.rejectValue('sourceFileName', 'Document.file.upload.error')
                        status.setRollbackOnly()
                        flash.message = message (code: 'Document.file.upload.error')
                        redirect(controller: 'project', action: 'show', id:  project.id)
                    } else {
                        redirect(controller: 'project', action: 'show', id:  project.id)
                    }
                } else {
                    status.setRollbackOnly()
                    flash.message = message (code: 'Document.file.upload.error')
                    redirect(controller: 'project', action: 'show', id:  project.id)
                }
            }
        } else {
            flash.message = message(code: 'upload.file.empty')
            redirect(controller: 'project', action: 'show', id:  project.id)
        }
    }

    /** Download specified document       */
    def downloadDocument = {
        def document = Document.get(params.id)
        if (document) {
            def docFile = documentService.getDocumentFile(document)
            if (document.contentType) {
                response.setContentType(document.contentType)
            }
            response.setHeader("Content-disposition", "attachment;filename*=UTF-8''${document.sourceFileName.encodeAsURL()}")
            response.setContentLength((int) docFile.length())
            response.outputStream << docFile.newInputStream()
            response.outputStream.flush()
            return null
        } else {
            response.sendError(404)
        }
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
    def listArchived = {
        def user = userService.currentUser
        user.projectFilter?.delete()
        user.projectFilter = null
        user.save()

        def filter = prepareFilter(params)
        filter.archived = true
        def results = projectService.findProjects(filter, true)

        def qsStr = new StringBuffer()
        filter.quickSearch.each {
            qsStr.append(it).append(" ")
        }

        [projects: results.list, total: results.totalCount, filter: filter, quickSearchStr: qsStr.toString().trim(), currentUser: user]

    }

    def archive = {
        def archivedIds = params.list("achivedProjectId").collect {it as long}
        if (!archivedIds || archivedIds.size() == 0) {
            redirect(controller: 'project', action: 'list')
            return
        }

        def projects2archive = Project.findAllByIdInList(archivedIds)
        def countPrj = 0

        projects2archive.each {
            it.archived = true
            if (it.save()) {
                countPrj++
            }
        }

        flash.message = message(code: "project.action.archived.true", args: [countPrj])

        redirect(controller: 'project', action: 'lookup')
    }

    def unarchive = {
        def archivedIds = params.list("achivedProjectId").collect {it as long}
        if (!archivedIds || archivedIds.size() == 0) {
            redirect(controller: 'project', action: 'list')
            return
        }

        def projects2archive = Project.findAllByIdInList(archivedIds)
        def countPrj = 0

        projects2archive.each {
            it.archived = false
            if (it.save()) {
                countPrj++
            }
        }

        flash.message = message(code: "project.action.unarchived.true", args: [countPrj])

        redirect(controller: 'project', action: 'lookup', params: [archived: true])
    }


    private def prepareFilter(params) {
        if (!params."sort") {
            params.sort = "dateCreated"
            params.order = "desc"

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
