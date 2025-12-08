import com.sap.gateway.ip.core.customdev.util.Message
import groovy.json.JsonOutput
import java.util.Base64

Message processData(Message message) {

    byte[] pdfBytes = message.getBody(byte[].class)
    if (pdfBytes == null || pdfBytes.length == 0) {
        message.setHeader("X-EMAIL-STATUS", "NO_PDF")
        message.setBody("No PDF bytes found after decode step.")
        return message
    }

    // ✅ Fixed header access
    String fileName = (message.getHeaders().get("X-FILENAME") ?: "document.pdf").toString()
    String billing  = (message.getHeaders().get("X-BILLING-ID") ?: "").toString()

    String toList   = "praveenkumar.sakthivel@motiveminds.com"
    String subjPref = "Supplier PO PDFs"

    String contentB64 = Base64.getEncoder().encodeToString(pdfBytes)
    def attachment = [
        "@odata.type": "#microsoft.graph.fileAttachment",
        name: fileName,
        contentType: "application/pdf",
        contentBytes: contentB64
    ]

    String subject = billing ? "${subjPref} - ${billing}" : subjPref

    def payload = [
        message: [
            subject: subject,
            body: [ contentType: "HTML", content: "<p>Hi,</p><p>Please find attached the Customer PO PDF.</p><p>Thanks,<br/>CPI</p>" ],
            toRecipients: [[emailAddress: [address: toList]]],
            attachments: [attachment]
        ],
        saveToSentItems: true
    ]

    message.setHeader("Content-Type", "application/json")
    message.setBody(JsonOutput.toJson(payload))
    return message
}