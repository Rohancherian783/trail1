import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import java.io.ByteArrayOutputStream
import com.sap.gateway.ip.core.customdev.util.Message

Message processData(Message message) {

    // 1️⃣ Get email body (from Content Modifier or Mail Sender)
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

    // 4️⃣ Create multipart MIME message manually
    // Build MIME headers for each attachment
    String boundary = "----=_Part_0_123456789.123456789"

    StringBuilder mimeBuilder = new StringBuilder()
    mimeBuilder.append("Content-Type: multipart/mixed; boundary=\"" + boundary + "\"\r\n\r\n")
    mimeBuilder.append("--" + boundary + "\r\n")
    mimeBuilder.append("Content-Type: text/plain; charset=UTF-8\r\n")
    mimeBuilder.append("Content-Transfer-Encoding: 7bit\r\n\r\n")
    mimeBuilder.append("Please find attached PDFs.\r\n\r\n")

    // First PDF
    mimeBuilder.append("--" + boundary + "\r\n")
    mimeBuilder.append("Content-Type: application/pdf; name=\"EmailReport_1.pdf\"\r\n")
    mimeBuilder.append("Content-Transfer-Encoding: base64\r\n")
    mimeBuilder.append("Content-Disposition: attachment; filename=\"EmailReport_1.pdf\"\r\n\r\n")
    mimeBuilder.append(baos1.toByteArray().encodeBase64().toString() + "\r\n\r\n")

    // Second PDF
    mimeBuilder
