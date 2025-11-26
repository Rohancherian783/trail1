import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import java.io.ByteArrayOutputStream
import com.sap.gateway.ip.core.customdev.util.Message

Message processData(Message message) {
    try {
        // Get email body as String
        def body = message.getBody(java.lang.String)
        if(body == null) {
            body = ""
        }

        // Create PDF
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        Document document = new Document()
        PdfWriter.getInstance(document, baos)
        document.open()
        document.add(new Paragraph(body))
        document.close()

        // Set PDF as body
        message.setBody(baos.toByteArray())
        message.setHeader("Content-Type", "application/pdf")

    } catch(Exception e) {
        // Log exception in CPI
        message.setBody("Error generating PDF: " + e.getMessage())
        message.setHeader("Content-Type", "text/plain")
    }

    return message
}
