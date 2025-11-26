import com.sap.gateway.ip.core.customdev.util.Message

Message processData(Message message) {
    // Set attachment filename for this branch
    message.setHeader("Content-Disposition", "attachment; filename=EmailReport_2.pdf")
    return message
}
