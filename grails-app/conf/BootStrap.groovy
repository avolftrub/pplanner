import grails.util.Environment
import org.apache.shiro.crypto.hash.Sha512Hash

class BootStrap {

    def init = { servletContext ->
        Environment.executeForCurrentEnvironment {

            bootstrapShiroRoles()

            development {
                new User(username: "artem.volftrub@gmail.com", password: new Sha512Hash('password').toHex(),
                        role: ShiroRole.findByRole('admin'), firstName: "Артем", lastName: "Вольфтруб").save(flush: true, failOnError: true)
                new User(username: "avolftrub-test-kkk@gramant.ru", password: new Sha512Hash('password').toHex(),
                        role: ShiroRole.findByName('dealer'), firstName: "Ксения",
                        lastName: "Продажная").save(flush: true, failOnError: true)

                def dealer1 = new Dealer(name: "ООО Агентство Химэксперт", code: "XX9812R").save(failOnError: true)
                def dealer2 = new Dealer(name: "\"TOO \"ZALMA Ltd.\" (ЦАЛМА Лтд)", code: "BB02S18").save(failOnError: true)

            }
        }
    }

    def bootstrapShiroRoles() {
        def roles = [
                admin:['users','projects','dealers'],
                dealer:['projects']
        ]

        roles.each { roleName, perms ->
            def role = ShiroRole.findByName(roleName)
            if (!role) {
                role = new ShiroRole(name:roleName)
                perms.each { permission ->
                    if (permission.indexOf(':') == -1) {
                        role.addToPermissions(permission + ':*')
                    } else {
                        role.addToPermissions(permission)
                    }
                }
                role.save(failOnError:true, flush: true)
            }
        }
    }

    def destroy = {
    }
}
