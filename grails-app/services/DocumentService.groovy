import org.codehaus.groovy.grails.commons.ConfigurationHolder

class DocumentService {

    /** Saves document file to disk  */
    def saveDocument(Document doc, file) {
        try {
            file.inputStream.withStream { InputStream stream ->
                saveDocumentFromStream(doc, stream, file.originalFilename)
            }
            return true
        } catch (Exception ex) {
            return false
        }
    }

    /** Saves document to disk  */
    def saveDocumentFromStream(Document doc, docStream, origFileName) {
        doc.sourceFileName = origFileName
        doc.save()
        def resultFile = getDocumentFile(doc)
        resultFile.withOutputStream { os ->
            os << docStream
        }
    }

    /** Returns real path to document  */
    def getDocumentFile(Document doc) {
        def path = ConfigurationHolder.config.document.upload.dir

        def dir = new File(path)
        dir.mkdirs()

        return new File(dir, getDocumentFileName (doc))
    }

    /** Returns document file name on the disk */
    def getDocumentFileName (Document doc) {
        return String.valueOf (doc.id)
    }
}
