import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import java.io.ByteArrayOutputStream
import com.sap.gateway.ip.core.customdev.util.Message

Message processData(Message message) {
    // Extract email body
    def bodyBytes = message.getBody(byte[])
    def emailBody = new String(bodyBytes, "UTF-8")  // convert to string

    // Check if body is empty
    if(emailBody == null || emailBody.trim().isEmpty()) {
        emailBody = "No content found in email."
    }

    // Generate PDF
    ByteArrayOutputStream baos = new ByteArrayOutputStream()
    Document document = new Document()
    PdfWriter.getInstance(document, baos)
    document.open()
    document.add(new Paragraph(emailBody))
    document.close()

    // Set PDF as attachment
    message.setBody(baos.toByteArray())
    message.setHeader("Content-Type", "application/pdf")
    message.setHeader("Content-Disposition", "attachment; filename=EmailReport.pdf")

    return message
}
