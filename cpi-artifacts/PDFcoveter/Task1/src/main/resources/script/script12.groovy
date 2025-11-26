import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import java.io.ByteArrayOutputStream
import com.sap.gateway.ip.core.customdev.util.Message

Message processData(Message message) {
    // 1. Get the email body
    def emailBody = message.getBody(String)

    // 2. Generate PDF
    ByteArrayOutputStream baos = new ByteArrayOutputStream()
    Document document = new Document()
    PdfWriter.getInstance(document, baos)
    document.open()
    document.add(new Paragraph(emailBody))
    document.close()
    
    // 3. Set PDF as attachment for Mail Receiver
    message.setBody(baos.toByteArray())
    message.setHeader("Content-Type", "application/pdf")
    message.setHeader("Content-Disposition", "attachment; filename=EmailReport.pdf")

    return message
}

