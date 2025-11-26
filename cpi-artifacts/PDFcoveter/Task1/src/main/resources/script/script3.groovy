import com.sap.gateway.ip.core.customdev.util.Message
import java.util.Properties
import java.io.ByteArrayInputStream

import javax.mail.Session
import javax.mail.Multipart
import javax.mail.BodyPart
import javax.mail.internet.MimeMessage

def Message processData(Message message) {

    // Convert CPI body into InputStream
    def body = message.getBody(byte[].class)
    def inputStream = new ByteArrayInputStream(body)

    // Parse using JavaMail API
    def session = Session.getDefaultInstance(new Properties())
    def mimeMessage = new MimeMessage(session, inputStream)

    def content = mimeMessage.getContent()
    def emailBody = ""

    if (content instanceof String) {
        // Simple email (text only)
        emailBody = content
    } else if (content instanceof Multipart) {
        Multipart multipart = (Multipart) content
        for (int i = 0; i < multipart.count; i++) {
            BodyPart bp = multipart.getBodyPart(i)
            if (bp.isMimeType("text/plain")) {
                emailBody = bp.getContent()
                break
            } else if (bp.isMimeType("text/html")) {
                emailBody = bp.getContent()   // fallback if HTML only
            }
        }
    }

    // Set extracted email body as new payload
    message.setBody(emailBody)

    return message
}
