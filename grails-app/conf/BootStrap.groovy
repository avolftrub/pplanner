import grails.util.Environment
import org.apache.shiro.crypto.hash.Sha512Hash
import org.joda.time.LocalDate
import ru.appbio.ProjectStatus

class BootStrap {

    def init = { servletContext ->
        Environment.executeForCurrentEnvironment {

            bootstrapShiroRoles()

            production {

//                if (User.count ==0) {
//                    def dealer1 = new Dealer(name: "ООО Агентство Химэксперт").save(failOnError: true)
//                    def dealer2 = new Dealer(name: "\"TOO \"ZALMA Ltd.\" (ЦАЛМА Лтд)").save(failOnError: true)
                if (User.count ==0) {
                    new User(username: "artem.volftrub@gmail.com",
                            password: new Sha512Hash('password').toHex(), password2: new Sha512Hash('password').toHex(),
                            role: ShiroRole.findByName(ShiroRole.ROLE_ADMIN), firstName: "Артем", lastName: "Вольфтруб").save(flush: true, failOnError: true)
//                    new User(username: "avolftrub-test-ggg@gramant.ru",
//                            password: new Sha512Hash('password').toHex(), password2: new Sha512Hash('password').toHex(),
//                            role: ShiroRole.findByName(ShiroRole.ROLE_DEALER), firstName: "Галина",
//                            lastName: "Головоломкина", dealer: dealer1).save(flush: true, failOnError: true)

//                    new User(username: "avolftrub-test-kkk@gramant.ru",
//                            password: new Sha512Hash('password').toHex(), password2: new Sha512Hash('password').toHex(),
//                            role: ShiroRole.findByName(ShiroRole.ROLE_DEALER), firstName: "Ксения",
//                            lastName: "Продажная", dealer: dealer2).save(flush: true, failOnError: true)
                                        /*
                    for(int i=1; i<40; i++) {
                        log.info "FFFF: it=$i"
                        def p = new Project(
                                createDate: new LocalDate(),
                                customer: "Клиент № $i",
                                department: "Административно-хозяйственный отдел № $i",
                                contactPerson: "Закулачный Степан Юрьевич $it",
                                contactPhone: "+8 800 2 000 $i",
                                contactEmail: info@customer.ru
                                name: "Поставка масс спектрометра для детского сада № $i",
                                releaseDate: new LocalDate().plusMonths(i),
                                sum: new BigDecimal(Math.round(Math.random() * 300000)),
                                status: ProjectStatus.SPECIFICATION_AGREED,
                                comments: "Какой чудестный день")
                        if (i< 20) {
                            p.dealer = dealer1
                        } else {
                            p.dealer = dealer2
                        }
                        p.save(failOnError: true)
                        */
                    //}
                }
            }
        }
    }

    def bootstrapShiroRoles() {
        def roles = [
                'admin' :['user','project:list,backToList,show,approve,reject,addComment,deleteComment,downloadDocument,exportToExcel','dealer', 'settings'],
                'dealer' :['project:list,backToList,show,create,save,update,edit,delete,addComment,deleteComment,uploadDocument,downloadDocument,exportToExcel,lookupCity,lookup', 'settings']
        ]

        roles.each { roleName, perms ->
            def role = ShiroRole.findByName(roleName)
            perms.each { permission ->
                if (permission.indexOf(':') == -1) {
                    role.addToPermissions(permission + ':*')
                } else {
                    role.addToPermissions(permission)
                }
            }
            role.save(failOnError:true, flush: true)

//            if (!role) {
//                role = new ShiroRole(name:roleName)
//                perms.each { permission ->
//                    if (permission.indexOf(':') == -1) {
//                        role.addToPermissions(permission + ':*')
//                    } else {
//                        role.addToPermissions(permission)
//                    }
//                }
//                role.save(failOnError:true, flush: true)
//            }

        }
    }

    def destroy = {
    }
}
