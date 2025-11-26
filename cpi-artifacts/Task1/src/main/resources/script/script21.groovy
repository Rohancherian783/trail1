import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import java.io.ByteArrayOutputStream
import com.sap.gateway.ip.core.customdev.util.Message

Message processData(Message message) {

    // 1️⃣ Get email body
    def emailBody = message.getBody(String)
    if(emailBody == null || emailBody.trim().isEmpty()) {
        emailBody = "No content found."
    }

    // 2️⃣ Generate first PDF
    ByteArrayOutputStream baos1 = new ByteArrayOutputStream()
    Document doc1 = new Document()
    PdfWriter.getInstance(doc1, baos1)
    doc1.open()
    doc1.add(new Paragraph("First PDF Content:\n\n" + emailBody))
    doc1.close()

    // 3️⃣ Generate second PDF
    ByteArrayOutputStream baos2 = new ByteArrayOutputStream()
    Document doc2 = new Document()
    PdfWriter.getInstance(doc2, baos2)
    doc2.open()
    doc2.add(new Paragraph("Second PDF Content:\n\n" + emailBody))
    doc2.close()

    // 4️⃣ Attach both PDFs using CamelAttachments (simpler and CPI-friendly)
    def attachments = [:]
    attachments.put("EmailReport_1.pdf", baos1.toByteArray())
    attachments.put("EmailReport_2.pdf", baos2.toByteArray())

    message.setHeader("CamelAttachments", attachments)
    message.setHeader("Content-Type", "multipart/mixed")

    // 5️⃣ Email body text
    message.setBody("Please find the attached PDFs.")

    return message
}
