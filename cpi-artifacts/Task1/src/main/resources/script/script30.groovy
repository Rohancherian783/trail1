import com.sap.gateway.ip.core.customdev.util.Message
import java.io.ByteArrayOutputStream
import javax.activation.DataHandler
import javax.mail.util.ByteArrayDataSource
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter

def Message processData(Message message) {

    // 1️⃣ Get email body and perform final text stripping
    def rawEmailBody = message.getBody(String)
    
    // --- START OF MODIFIED CLEANUP LOGIC ---
    
    def cleanEmailBody = rawEmailBody

    // a. Remove the large, multi-line disclaimer block using multiline regex (s flag).
    // We target "Disclaimer:" up to the end of the content.
    // NOTE: This assumes the disclaimer always starts with "Disclaimer:" and appears at the end.
    def disclaimerPattern = '(?si)Disclaimer:.*'
    cleanEmailBody = cleanEmailBody.replaceAll(disclaimerPattern, '')

    // b. Replace ALL HTML tags (block and inline) with a SINGLE SPACE. (Harmless for plain text, vital for HTML).
    cleanEmailBody = cleanEmailBody.replaceAll('(?si)<.*?>', ' ') 
                                     
    // c. Replace only CARRIAGE RETURNS (\r) with a space, but preserve standard NEWLINES (\n).
    cleanEmailBody = cleanEmailBody.replaceAll('\r', ' ')
    
    // d. Remove the specific unwanted header text patterns (Header lines from previous HTML template).
    // This is still harmless for plain text but protects against hybrid emails.
    def unwantedPattern = "Email|\\{\\{company_name\\}\\}|Short headline or tagline goes here"
    cleanEmailBody = cleanEmailBody.replaceAll(unwantedPattern, '')

    // e. Replace 3 or more consecutive newlines with two newlines (paragraph break).
    cleanEmailBody = cleanEmailBody.replaceAll('\n{3,}', '\n\n') 
    
    // 💡 CRUCIAL CHANGE: Replace 3 or more consecutive SPACES with TWO SPACES, NOT newlines.
    // This preserves the alignment gaps in the plain-text table, which rely on large spaces.
    cleanEmailBody = cleanEmailBody.replaceAll(' {3,}', '  ')
    
    // f. Trim leading/trailing whitespace and newlines.
                                     .trim()
    
    // --- END OF MODIFIED CLEANUP LOGIC ---
    
    def emailBody = cleanEmailBody // Use the cleaned, formatted plain text body for the PDF

    // 2️⃣ Get the incoming email subject dynamically
    def incomingSubject = message.getHeader("CamelMailSubject", String) ?:
                          message.getHeader("Subject", String) ?:
                          message.getHeader("mail_subject", String) ?:
                          "EmailReport"

    // 3️⃣ Log for debugging (optional)
    def log = messageLogFactory.getMessageLog(message)
    if (log) {
        log.addAttachmentAsString("Incoming Subject", incomingSubject, "text/plain")
        log.addAttachmentAsString("Cleaned Email Body (Formatted)", emailBody, "text/plain") 
    }

    // 4️⃣ Set outgoing mail subject (for both header and property)
    message.setHeader("Subject", incomingSubject)
    message.setProperty("Subject", incomingSubject)

    // 5️⃣ Clean and prepare PDF filename
    def pdfSubject = incomingSubject.replaceAll("[\\\\/:*?\"<>|]", "_")
    if (pdfSubject.length() > 50) {
        pdfSubject = pdfSubject.substring(0, 50)
    }

    // 6️⃣ Function to create PDF DataHandler
    def createPDFDataHandler = { content ->
        Document document = new Document()
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        PdfWriter.getInstance(document, baos)
        document.open()
        
        // iText's Paragraph handles embedded '\n' characters as line breaks
        // NOTE: iText will use a non-proportional font to display the text, meaning columns
        // might not be perfectly aligned unless the font is explicitly set to Monospace, 
        // which is advanced configuration not possible here. However, the text will be intact.
        document.add(new Paragraph(content))
        
        document.close()
        def ds = new ByteArrayDataSource(baos.toByteArray(), "application/pdf")
        return new DataHandler(ds)
    }

    // 7️⃣ Create two PDFs
    def pdf1 = createPDFDataHandler(emailBody)
    def pdf2 = createPDFDataHandler(emailBody)

    // 8️⃣ Add attachments with dynamic filenames
    def attachments = [:]
    attachments.put("${pdfSubject}_1.pdf".toString(), pdf1)
    attachments.put("${pdfSubject}_2.pdf".toString(), pdf2)
    message.setAttachments(attachments)

    // 9️⃣ Set final mail body text
    message.setBody("Please find attached PDFs generated from the email titled: '${incomingSubject}'.")

    return message
}
















