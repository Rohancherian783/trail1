import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import java.io.ByteArrayOutputStream
import com.sap.gateway.ip.core.customdev.util.Message

Message processData(Message message) {

    def emailBody = message.getBody(String)
    if(emailBody == null || emailBody.trim().isEmpty()) {
        emailBody = "No content found."
    }

    // Generate PDF
    ByteArrayOutputStream baos = new ByteArrayOutputStream()
    Document document = new Document()
    PdfWriter.getInstance(document, baos)
    document.open()
    document.add(new Paragraph("Email Content:\n\n" + emailBody))
    document.close()

    // Set PDF as body
    message.setBody(baos.toByteArray())
    message.setHeader("Content-Type", "application/pdf")
    message.setHeader("Content-Disposition", "attachment; filename=EmailReport.pdf")

    return message
}
