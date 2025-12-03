import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import java.io.ByteArrayOutputStream
import com.sap.gateway.ip.core.customdev.util.Message

Message processData(Message message) {
    // Get email body
    def bodyBytes = message.getBody(byte[])
    def emailBody = new String(bodyBytes, "UTF-8")
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

    // Attach PDF properly
    def attachments = [:]   // Map of filename → byte array
    attachments.put("EmailReport.pdf", baos.toByteArray())

    message.setHeader("CamelAtta
