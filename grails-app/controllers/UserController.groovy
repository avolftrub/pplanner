import ru.appbio.SearchParameters
import org.apache.shiro.crypto.hash.Sha512Hash

class UserController {
    def userService

    def show = {
        def user = User.get(params.long('id'))
        if (!user) {
            redirect(controller: 'user', action: 'list')
        }

        [user: user]
    }

    def edit = {
        def user = User.get(params.long('id'))
        if (!user) {
            redirect(controller: 'user', action: 'list')
        }

        [user: user, isNew: false]
    }

    def list = {
        def filter = prepareFilter()
        def results = userService.findUsers(filter)
        [users: results.list, total: results.totalCount, filter: filter]
    }


    def update = {
        def user = User.get(params.long('id'))
        if (!user) {
            redirect(controller: 'user', action: 'list')
        }

        def isPasswordChanged = params.boolean('pwdChange')

        user.properties = params

        user.validate()

        if (isPasswordChanged) {
            if (params.password.trim().length() < 6) {
                user.errors.rejectValue('password', 'password.too.short')
            } else if (params.password != params.password2) {
                user.errors.rejectValue('password', 'passwords.mismatch')
                user.errors.rejectValue('password2', 'passwords.mismatch')
            }
        }

        if (!user.hasErrors()) {
            if (isPasswordChanged) {
                user.password = new Sha512Hash(params.password).toHex()
            }
            if (user.save()) {
                redirect(controller: 'user', action: 'show')
            } else {
                //clear password fields
                user.password = ''
                user.password2 = ''
                render(view: '/user/edit', model: [user: user, pwdChange: isPasswordChanged])
            }
        } else {
            //clear password fields
            user.password = ''
            user.password2 = ''
            render(view: '/user/edit', model: [user: user, pwdChange: isPasswordChanged])
        }
    }

    def save = {
        def user = new User()

        user.properties = params

        if (params.boolean("admin")) {
            user.role = ShiroRole.findByName(ShiroRole.ROLE_ADMIN)
        } else {
            user.role = ShiroRole.findByName(ShiroRole.ROLE_DEALER)
        }

        def isPasswordChanged = params.boolean('pwdChange')

        if (isPasswordChanged) {
            if (params.password.trim().length() < 6) {
                user.errors.rejectValue('password', 'password.too.short')
            } else if (params.password != params.password2) {
                user.errors.rejectValue('password', 'passwords.mismatch')
                user.errors.rejectValue('password2', 'passwords.mismatch')
            }
        }

        user.validate()

        if (!user.hasErrors()) {
            user.password = new Sha512Hash(params.password).toHex()
            if (user.save()) {
                redirect(controller: 'user', action: 'show')
            } else {
                //clear password fields
                user.password = ''
                user.password2 = ''
                render(view: '/user/create', model: [user: user, isNew: true, currentUser: userService.getCurrentUser(),admin: params.boolean("admin")])
            }
        } else {
            //clear password fields
            user.password = ''
            user.password2 = ''
            render(view: '/user/create', model: [user: user, isNew: true, currentUser: userService.getCurrentUser(),admin: params.boolean("admin")])
        }
    }

    def delete = {
        def user = User.get(params.long('id'))
        if (!user) {
            redirect(controller: 'user', action: 'list')
        }

        def currentUser = userService.currentUser

        if (user == currentUser) {
            flash.message = message(code: 'user.self.deleting')
            redirect(controller: 'user', action: params.actionBack)
        } else {
            user.delete()
            redirect(controller: 'user', action: 'list')
        }
    }

    def create = {
        [user: new User(), isNew: true, pwdChange: true, currentUser: userService.getCurrentUser(), admin: params.admin]
    }

    private def prepareFilter() {
        if (!params."sort") {
            params.sort = "lastName"
            params.order = "asc"

        }

        def filter = new SearchParameters()

        bindData(filter, params)
        params.max = Math.min(params.int('max') ?: 20, 100)
        filter.bindLimits(params)
        return filter
    }
}
