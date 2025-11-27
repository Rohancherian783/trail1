import com.sap.gateway.ip.core.customdev.util.Message
import com.itextpdf.text.*
import com.itextpdf.text.pdf.*
import java.io.ByteArrayOutputStream

Message processData(Message message) {

    // PDF content (example)
    def pdfContent = "This is the email content converted to PDF."

    // First PDF
    def pdf1 = new ByteArrayOutputStream()
    def doc1 = new Document()
    PdfWriter.getInstance(doc1, pdf1)
    doc1.open()
    doc1.add(new Paragraph(pdfContent))
    doc1.close()

    // Second PDF
    def pdf2 = new ByteArrayOutputStream()
    def doc2 = new Document()
    PdfWriter.getInstance(doc2, pdf2)
    doc2.open()
    doc2.add(new Paragraph(pdfContent))
    doc2.close()

    // Add both to attachments
    def attachmentsMap = [:]
    attachmentsMap.put("EmailReport_1.pdf", pdf1.toByteArray())
    attachmentsMap.put("EmailReport_2.pdf", pdf2.toByteArray())

    // Set attachments map to header
    message.setHeader("CamelAttachments", attachmentsMap)

    return message
}




