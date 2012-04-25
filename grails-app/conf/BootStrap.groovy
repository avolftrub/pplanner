import grails.util.Environment
import org.apache.shiro.crypto.hash.Sha512Hash
import org.joda.time.LocalDate
import ru.appbio.ProjectStatus

class BootStrap {

    def init = { servletContext ->
        Environment.executeForCurrentEnvironment {

            bootstrapShiroRoles()

            development {

                def dealer1 = new Dealer(name: "ООО Агентство Химэксперт", code: "XX9812R").save(failOnError: true)
                def dealer2 = new Dealer(name: "\"TOO \"ZALMA Ltd.\" (ЦАЛМА Лтд)", code: "BB02S18").save(failOnError: true)

                new User(username: "artem.volftrub@gmail.com", password: new Sha512Hash('password').toHex(),
                        role: ShiroRole.findByName(ShiroRole.ROLE_ADMIN), firstName: "Артем", lastName: "Вольфтруб").save(flush: true, failOnError: true)
                new User(username: "avolftrub-test-kkk@gramant.ru", password: new Sha512Hash('password').toHex(),
                        role: ShiroRole.findByName(ShiroRole.ROLE_DEALER), firstName: "Ксения",
                        lastName: "Продажная", dealer: dealer2).save(flush: true, failOnError: true)

                for(int i=1; i<40; i++) {
                    log.info "FFFF: it=$i"
                    new Project(
                            createDate: new LocalDate(),
                            customer: "Клиент № $i",
                            department: "Административно-хозяйственный отдел № $i",
                            contactPerson: "Закулачный Степан Юрьевич $it",
                            contactPhone: "+8 800 2 000 $i",
                            name: "Поставка масс спектрометра для детского сада № $i",
                            releaseDate: new LocalDate().plusMonths(i),
                            sum: new BigDecimal(Math.round(Math.random() * 300000)),
                            status: ProjectStatus.SPECIFICATION_AGREED,
                            comments: "Какой чудестный день",
                            dealer: dealer1
                    ).save(failOnError: true)
                }
            }
        }
    }

    def bootstrapShiroRoles() {
        def roles = [
                'admin' :['user','project','dealer'],
                'dealer' :['project']
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
