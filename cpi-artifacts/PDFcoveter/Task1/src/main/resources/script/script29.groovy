import com.sap.gateway.ip.core.customdev.util.Message
import java.io.ByteArrayOutputStream
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter

def Message processData(Message message) {

    // 1️⃣ Get the email body
    def emailBody = message.getBody(String)

    // 2️⃣ Function to create PDF as byte array
    def createPDF = { content ->
        Document document = new Document()
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        PdfWriter.getInstance(document, baos)
        document.open()
        document.add(new Paragraph(content))
        document.close()
        return baos.toByteArray()
    }

    // 3️⃣ Generate two PDFs
    byte[] pdf1 = createPDF(emailBody)
    byte[] pdf2 = createPDF(emailBody)

    // 4️⃣ Set attachments map for outgoing email
    def attachments = [:]
    attachments.put("EmailReport_1.pdf", pdf1)
    attachments.put("EmailReport_2.pdf", pdf2)

    message.setAttachments(attachments)

    return message
}
