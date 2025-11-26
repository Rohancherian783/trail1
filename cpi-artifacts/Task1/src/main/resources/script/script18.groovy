import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import java.io.ByteArrayOutputStream
import com.sap.gateway.ip.core.customdev.util.Message
import javax.mail.internet.MimeMultipart
import javax.mail.BodyPart

Message processData(Message message) {

    // Extract email content
    def emailBody = ""
    def bodyObject = message.getBody(Object)

    if(bodyObject instanceof MimeMultipart) {
        MimeMultipart multipart = (MimeMultipart) bodyObject
        for (int i = 0; i < multipart.getCount(); i++) {
            BodyPart part = multipart.getBodyPart(i)
            if (part.getContentType().toLowerCase().contains("text/plain")) {
                emailBody += part.getContent().toString()
            }
        }
    } else {
        emailBody = bodyObject.toString()
    }

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

    // Attach PDF using CamelAttachments
    def attachments = [:]
    attachments.put("EmailReport.pdf", baos.toByteArray())
    message.setHeader("CamelAttachments", attachments)
    message.setHeader("Content-Type", "multipart/mixed")

    // Set empty body or short message
    message.setBody(" ")  // Do NOT put emailBody here, else it may override attachment

    return message
}
