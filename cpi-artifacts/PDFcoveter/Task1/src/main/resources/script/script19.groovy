import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import java.io.ByteArrayOutputStream
import com.sap.gateway.ip.core.customdev.util.Message

Message processData(Message message) {

    // 1. Use fixed text or body from Content Modifier
    def emailBody = message.getBody(String)
    if(emailBody == null || emailBody.trim().isEmpty()) {
        emailBody = "No content found."
    }

    // 2. Generate PDF
    ByteArrayOutputStream baos = new ByteArrayOutputStream()
    Document document = new Document()
    PdfWriter.getInstance(document, baos)
    document.open()
    document.add(new Paragraph(emailBody))
    document.close()

    // 3. Set PDF as message body (for Mail Receiver attachment)
    message.setBody(baos.toByteArray())
    message.setHeader("Content-Type", "application/pdf")
    message.setHeader("Content-Disposition", "attachment; filename=EmailReport.pdf")

    return message
}
