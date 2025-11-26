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

    // 1️⃣ Generate first PDF
    ByteArrayOutputStream baos1 = new ByteArrayOutputStream()
    Document doc1 = new Document()
    PdfWriter.getInstance(doc1, baos1)
    doc1.open()
    doc1.add(new Paragraph("First PDF:\n\n" + emailBody))
    doc1.close()

    // 2️⃣ Generate second PDF
    ByteArrayOutputStream baos2 = new ByteArrayOutputStream()
    Document doc2 = new Document()
    PdfWriter.getInstance(doc2, baos2)
    doc2.open()
    doc2.add(new Paragraph("Second PDF:\n\n" + emailBody))
    doc2.close()

    // 3️⃣ Create MIME multipart manually
    String boundary = "----=_Part_0_123456789"
    StringBuilder mime = new StringBuilder()

    // Text part
    mime.append("Content-Type: multipart/mixed; boundary=\"" + boundary + "\"\r\n\r\n")
    mime.append("--" + boundary + "\r\n")
    mime.append("Content-Type: text/plain; charset=UTF-8\r\n\r\n")
    mime.append("Please find attached PDFs.\r\n\r\n")

    // First PDF
    mime.append("--" + boundary + "\r\n")
    mime.append("Content-Type: application/pdf; name=\"EmailReport_1.pdf\"\r\n")
    mime.append("Content-Transfer-Encoding: base64\r\n")
    mime.append("Content-Disposition: attachment; filename=\"EmailReport_1.pdf\"\r\n\r\n")
    mime.append(baos1.toByteArray().encodeBase64().toString() + "\r\n\r\n")

    // Second PDF
    mime.append("--" + boundary + "\r\n")
    mime.append("Content-Type: application/pdf; name=\"EmailReport_2.pdf\"\r\n")
    mime.append("Content-Transfer-Encoding: base64\r\n")
    mime.append("Content-Disposition: attachment; filename=\"EmailReport_2.pdf\"\r\n\r\n")
    mime.append(baos2.toByteArray().encodeBase64().toString() + "\r\n\r\n")

    // End boundary
    mime.append("--" + boundary + "--\r\n")

    // 4️⃣ Set MIME as body
    message.setBody(mime.toString())
    message.setHeader("Content-Type", "multipart/mixed; boundary=\"" + boundary + "\"")

    return message
}
