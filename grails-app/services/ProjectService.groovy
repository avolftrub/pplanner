import ru.appbio.SearchParameters
import org.joda.time.format.DateTimeFormat
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.streaming.SXSSFWorkbook
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Cell

class ProjectService {

    static EXCEL_HEADER = ["Название", "Исполнитель", "Дата создания", "Конечный пользователь", "Подразделение", "Город", "Контактное лицо",
            "Контактная информация", "Продукт", "Планируемая дата реализации", "Сумма", "Статус", "Примечания", "Дата закрытия"]

/** Finds projects with the speicified parameters             */
    def findProjects(SearchParameters filter) {
        Project.createCriteria().list(filter.getLimits()) {

            //quick search
            if (filter.quickSearch) {
                for (token in filter.quickSearch) {
                    like('name', token)
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
        EXCEL_HEADER.each {
            Cell cell = headerRow.createCell(cellNum++)
            cell.setCellValue(it)
        }
        //create other rows
        filter.offset = 0
        filter.max = 500
        filter.sort = "id"
        filter.order = "asc"
        def dtf = DateTimeFormat.forPattern("dd.MM.yyy")
        while (true) {
            def objects = findProjects(filter)
            filter.offset += filter.max
            objects.each { Project project ->
                def row = sheet.createRow(rowNum++)
                cellNum = 0
                row.createCell (cellNum++).setCellValue(project.name ?: "")
                row.createCell (cellNum++).setCellValue(project.dealer?.name ?: "")
                row.createCell (cellNum++).setCellValue(dtf.print(project.createDate))
                row.createCell (cellNum++).setCellValue(project.customer ?: "")
                row.createCell (cellNum++).setCellValue(project.department ?: "")
                row.createCell (cellNum++).setCellValue(project.city ?: "")
                row.createCell (cellNum++).setCellValue(project.contactPerson ?: "")
                row.createCell (cellNum++).setCellValue(project.contactPhone)
                row.createCell (cellNum++).setCellValue(project.releaseDate ? dtf.print(project.releaseDate): "")
                row.createCell (cellNum++).setCellValue(project.sum ?: "")
//                row.createCell (cellNum++).setCellValue( message(code: 'project.status.'+project.status))
                row.createCell (cellNum++).setCellValue(project.comments ?: "")
                row.createCell (cellNum++).setCellValue(project.closeDate ? dtf.print(project.closeDate): "")
            }
            if (!objects) {
                break;
            }
            Project.withSession { session ->
                session.clear()
            }
//            sessionFactory.getCurrentSession().clear()
        }
        wb.write(outStream)

        log.info "ProjectService: Export finished."
    }
}
