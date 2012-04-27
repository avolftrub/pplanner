import org.apache.shiro.crypto.hash.Sha512Hash

class SettingsController {

    def userService

    def showSettings = {
        [user: userService.getCurrentUser()]
    }

    def editSettings = {
        [user: userService.getCurrentUser()]
    }

    def update = {
        def user = userService.getCurrentUser()
        if (!user) {
            redirect(controller: 'project', action: 'list')
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
                redirect(controller: 'settings', action: 'showSettings')
            } else {
                //clear password fields
                user.password = ''
                user.password2 = ''
                render(view: '/settings/editSettings', model: [user: user, pwdChange: isPasswordChanged])
            }
        } else {
            //clear password fields
            user.password = ''
            user.password2 = ''
            render(view: '/settings/editSettings', model: [user: user, pwdChange: isPasswordChanged])
        }
    }
}
