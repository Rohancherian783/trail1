import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfCopy
import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.PdfWriter
import java.io.ByteArrayOutputStream
import com.sap.gateway.ip.core.customdev.util.Message

Message processData(Message message) {

    def emailBody = message.getBody(String)
    if(emailBody == null || emailBody.trim().isEmpty()) {
        emailBody = "No content found."
    }

    // PDF 1
    ByteArrayOutputStream baos1 = new ByteArrayOutputStream()
    Document doc1 = new Document()
    PdfWriter.getInstance(doc1, baos1)
    doc1.open()
    doc1.add(new Paragraph("First PDF:\n\n" + emailBody))
    doc1.close()

    // PDF 2
    ByteArrayOutputStream baos2 = new ByteArrayOutputStream()
    Document doc2 = new Document()
    PdfWriter.getInstance(doc2, baos2)
    doc2.open()
    doc2.add(new Paragraph("Second PDF:\n\n" + emailBody))
    doc2.close()

    // Merge PDFs
    ByteArrayOutputStream mergedBaos = new ByteArrayOutputStream()
    Document mergedDoc = new Document()
    PdfCopy copy = new PdfCopy(mergedDoc, mergedBaos)
    mergedDoc.open()

    PdfReader reader1 = new PdfReader(baos1.toByteArray())
    for(int i=1;i<=reader1.getNumberOfPages();i++){
        copy.addPage(copy.getImportedPage(reader1,i))
    }
    reader1.close()

    PdfReader reader2 = new PdfReader(baos2.toByteArray())
    for(int i=1;i<=reader2.getNumberOfPages();i++){
        copy.addPage(copy.getImportedPage(reader2,i))
    }
    reader2.close()

    mergedDoc.close()

    // Set as attachment
    message.setBody(mergedBaos.toByteArray())
    message.setHeader("Content-Type","application/pdf")
    message.setHeader("Content-Disposition","attachment; filename=EmailReports.pdf")

    return message
}

