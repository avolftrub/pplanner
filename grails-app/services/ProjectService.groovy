import ru.appbio.ProjectStatus
import ru.appbio.SearchParameters
import org.joda.time.format.DateTimeFormat
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.streaming.SXSSFWorkbook
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Cell
import ru.appbio.ProjectSearchParameters
import java.text.StringCharacterIterator
import java.text.CharacterIterator
import org.hibernate.criterion.Restrictions
import org.hibernate.criterion.LikeExpression
import java.text.SimpleDateFormat
import org.springframework.web.servlet.support.RequestContextUtils
import org.codehaus.groovy.grails.web.util.WebUtils
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.Font
import ru.appbio.LTProjectStatus

class ProjectService {

    static EXCEL_HEADER = ["Уникальный идентификатор", "Название", "Дата создания", "Дата последнего измененеия", "Продукт", "Исполнитель", "Заказчик", "ИНН Заказчика", "Город",
            "Сумма (\$)", "Статус проекта", /*"Статус LT",*/ "Планируемая дата завершения", "Фактическая дата завершения", "Подразделение", "Контактное лицо",
            "Контактный email", "Контактная информация", "Примечания"]

    def messageSource

/** Finds projects with the speicified parameters             */
    def findProjects(ProjectSearchParameters filter, includeCompetitorsProjects = false) {
        Project.createCriteria().list(filter.getLimits()) {
            or {
                and {
                    if (filter.dealerId) {
                        eq ("dealer.id", filter.dealerId)
                    }
                    if (filter.projectId) {
                        eq ("id", filter.projectId)
                    }
                    if (filter.archived) {
                        eq ("archived", true)
                    } else {
                        eq ("archived", false)
                    }
                    if (filter.quickSearch) {
                        and {
                            for (token in filter.quickSearch) {
                                or {
                                    sqlRestriction("CAST(this_.id AS char) like '%" + escape(token, '!' as char) + "%'") // id
                                    'ilike'("name", '%' + escape(token, '!' as char) + '%')                              // название проекта
                                    'ilike'("productName", '%' + escape(token, '!' as char) + '%')                       // название продукта
                                    'ilike'("customer", '%' + escape(token, '!' as char) + '%')                          // название заказчика
                                    'ilike'("customerName", '%' + escape(token, '!' as char) + '%')                      // конечный пользователь
                                    'ilike'("inn", '%' + escape(token, '!' as char) + '%')                               // инн заказчика
                                    'ilike'("department", '%' + escape(token, '!' as char) + '%')                        // подразделение
                                    'ilike'("contactPerson", '%' + escape(token, '!' as char) + '%')                     // контактное лицо
                                    'ilike'("contactEmail", '%' + escape(token, '!' as char) + '%')                     // контактный email
                                    'ilike'("contactPhone", '%' + escape(token, '!' as char) + '%')                     // контактная информация
                                    join('dealer')
                                    dealer {
                                        'ilike'("name", '%' + escape(token, '!' as char) + '%')                          // исполнитель (имя дилера)
                                    }
                                    join('city')
                                    city {
                                        'ilike'("name", '%' + escape(token, '!' as char) + '%')                          // город расположения
                                    }
                                }
                            }
                        }

                    }
                }

                if (includeCompetitorsProjects) {
                    and {
                        ne ("dealer.id", filter.dealerId)
//                        eq ("approvalStatus", LTProjectStatus.APPROVED)
                        if (filter.projectId) {
                            eq ("id", filter.projectId)
                        }
                        if (filter.archived) {
                            eq ("archived", true)
                        } else {
                            eq ("archived", false)
                        }
                        if (filter.quickSearch) {
                            and {
                                for (token in filter.quickSearch) {
                                    or {
                                        sqlRestriction("CAST(this_.id AS char) like '%" + escape(token, '!' as char) + "%'") // id
                                        'ilike'("name", '%' + escape(token, '!' as char) + '%')                              // название проекта
                                        'ilike'("productName", '%' + escape(token, '!' as char) + '%')                       // название продукта
                                        'ilike'("customer", '%' + escape(token, '!' as char) + '%')                          // название заказчика
                                        'ilike'("customerName", '%' + escape(token, '!' as char) + '%')                      // конечный пользователь
                                        'ilike'("inn", '%' + escape(token, '!' as char) + '%')                               // инн заказчика
                                        join('city')
                                        city {
                                            'ilike'("name", '%' + escape(token, '!' as char) + '%')                          // город расположения
                                        }
                                    }
                                }
                            }

                        }
                    }

                }

            }
        }
    }

    def findCities(terms) {
        return City.createCriteria().list() {
            and {
                for (token in terms) {
                    'ilike'("name", '%' + escape(token, '!' as char) + '%')
                }
            }
        }
    }

    /** Exports user list to the output stream */
    def exportToExcel (outStream, filter) {

        log.info "ProjectService: Starting export... Total number of projects=${Project.count()}"
        Workbook wb = new SXSSFWorkbook(500)
        Sheet sheet = wb.createSheet("Projects")
        //create header row
        int rowNum = 0
        def headerRow = sheet.createRow(rowNum++)
        int cellNum = 0
        def utils = WebUtils.retrieveGrailsWebRequest()

        CellStyle headStyle = wb.createCellStyle();
        Font f = wb.createFont();
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);
        headStyle.setFont(f);

        EXCEL_HEADER.each {
            Cell cell = headerRow.createCell(cellNum++)
            cell.setCellStyle(headStyle)
            cell.setCellValue(it)
        }
        //create other rows
        filter.offset = 0
        filter.max = 5000
        filter.sort = "name"
        filter.order = "asc"
        def dtf = new SimpleDateFormat("dd.MM.yyyy")
        def jodaDtf = DateTimeFormat.forPattern("dd.MM.yyy")
        while (true) {
            def objects = findProjects(filter)
            filter.offset += filter.max
            objects.each { Project project ->
                def row = sheet.createRow(rowNum++)
                cellNum = 0
                def status = messageSource.getMessage('project.status.' + project.status.id, null, RequestContextUtils.getLocale(utils.getCurrentRequest()))
//                def status1 = messageSource.getMessage('project.status.lt.' + project.approvalStatus.id, null, RequestContextUtils.getLocale(utils.getCurrentRequest()))

                row.createCell (cellNum++).setCellValue(project.id ?: "")
                row.createCell (cellNum++).setCellValue(project.name ?: "")
                row.createCell (cellNum++).setCellValue(dtf.format(project.dateCreated))
                row.createCell (cellNum++).setCellValue(dtf.format(project.lastUpdated))
                row.createCell (cellNum++).setCellValue(project.productName ?: "")
                row.createCell (cellNum++).setCellValue(project.dealer?.name ?: "")
                row.createCell (cellNum++).setCellValue(project.customer ?: "")
                row.createCell (cellNum++).setCellValue(project.inn ?: "")
                row.createCell (cellNum++).setCellValue(project.city?.name ?: "")
                row.createCell (cellNum++).setCellValue(project.sum.toString() ?: "")
                row.createCell (cellNum++).setCellValue(status ?: "")
//                row.createCell (cellNum++).setCellValue(status1 ?: "")
                row.createCell (cellNum++).setCellValue(project.releaseDate ? jodaDtf.print(project.releaseDate): "")
                row.createCell (cellNum++).setCellValue(project.closeDate ? jodaDtf.print(project.closeDate): "")
                row.createCell (cellNum++).setCellValue(project.department ?: "")
                row.createCell (cellNum++).setCellValue(project.contactPerson ?: "")
                row.createCell (cellNum++).setCellValue(project.contactEmail ?: "")
                row.createCell (cellNum++).setCellValue(project.contactPhone)
                row.createCell (cellNum++).setCellValue(project.comments ?: "")
            }

            if (!objects) {
                break;
            }
            Project.withSession { session ->
                session.clear()
            }
//            sessionFactory.getCurrentSession().clear()
        }

        EXCEL_HEADER.eachWithIndex {it, colNumber ->
            sheet.autoSizeColumn(colNumber)
        }
        wb.write(outStream)

        log.info "ProjectService: Export finished."
    }

    public static String escape(String src, char escapeChar) {
        final StringBuilder result = new StringBuilder();
        final StringCharacterIterator iterator = new StringCharacterIterator(src);
        char character = iterator.current();
        while (character != CharacterIterator.DONE) {
            switch (character) {
                case '_':
                case '%':
                case '!':
                case '\\':
                    result.append(escapeChar);
                default:
                    result.append(character);
                    break;
            }
            character = iterator.next();
        }
        return result.toString();
    }

}
