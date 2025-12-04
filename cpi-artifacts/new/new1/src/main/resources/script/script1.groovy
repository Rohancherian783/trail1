import com.sap.gateway.ip.core.customdev.util.Message
import java.io.ByteArrayOutputStream
import javax.activation.DataHandler
import javax.mail.util.ByteArrayDataSource
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter

def Message processData(Message message) {

    // Read email content (body)
    def emailBody = message.getBody(String)

    // Extract the subject from available headers
    def incomingSubject = message.getHeader("CamelMailSubject", String) ?:
                          message.getHeader("Subject", String) ?:
                          message.getHeader("mail_subject", String) ?:
                          "EmailReport"

    message.setHeader("Subject", incomingSubject)

    // Clean subject to make a valid file name
    def pdfSubject = incomingSubject.replaceAll("[\\\\/:*?\"<>|]", "_")
    if (pdfSubject.length() > 50) {
        pdfSubject = pdfSubject.substring(0, 50)
    }

    // Function to create PDF data handler
    def createPDFDataHandler = { content ->
        Document document = new Document()
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        PdfWriter.getInstance(document, baos)

        document.open()
        document.add(new Paragraph(content))
        document.close()

        ByteArrayDataSource dataSource =
            new ByteArrayDataSource(baos.toByteArray(), "application/pdf")

        return new DataHandler(dataSource)
    }

    // Create PDF from email body
    def pdfHandler = createPDFDataHand
