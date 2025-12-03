import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfCopy
import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.PdfWriter
import java.io.ByteArrayOutputStream
import java.io.ByteArrayInputStream
import com.sap.gateway.ip.core.customdev.util.Message
import javax.mail.internet.MimeMultipart
import javax.mail.BodyPart

Message processData(Message message) {

    // 1️⃣ Extract email body safely
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
        emailBody = "No content found."
    }

    // 2️⃣ Generate first PDF
    ByteArrayOutputStream baos1 = new ByteArrayOutputStream()
    Document doc1 = new Document()
    PdfWriter.getInstance(doc1, baos1)
    doc1.open()
    doc1.add(new Paragraph("First PDF:\n\n" + emailBody))
    doc1.close()

    // 3️⃣ Generate second PDF
    ByteArrayOutputStream baos2 = new ByteArrayOutputStream()
    Document doc2 = new Document()
    PdfWriter.getInstance(doc2, baos2)
    doc2.open()
    doc2.add(new Paragraph("Second PDF:\n\n" + emailBody))
    doc2.close()

    // 4️⃣ Merge PDFs into one
    ByteArrayOutputStream mergedBaos = new ByteArrayOutputStream()
    Document mergedDocument = new Document()
    PdfCopy copy = new PdfCopy(mergedDocument, mergedBaos)
    mergedDocument.open()

    PdfReader reader1 = new PdfReader(baos1.toByteArray())
    for (int i = 1; i <= reader1.getNumberOfPages(); i++) {
        copy.addPage(copy.getImportedPage(reader1, i))
    }
    reader1.close()

    PdfReader reader2 = new PdfReader(baos2.toByteArray())
    for (int i = 1; i <= reader2.getNumberOfPages(); i++) {
        copy.addPage(copy.getImportedPage(reader2, i))
    }
    reader2.close()

    mergedDocument.close()

    // 5️⃣ Set merged PDF as body
    message.setBody(mergedBaos.toByteArray())
    message.setHeader("Content-Type", "application/pdf")
    message.setHeader("Content-Disposition", "attachment; filename=EmailReports.pdf")

    return message
}
