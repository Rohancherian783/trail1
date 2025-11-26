import com.sap.gateway.ip.core.customdev.util.Message

Message processData(Message message) {
    message.setHeader("Content-Disposition", "attachment; filename=EmailReport_1.pdf")
    return message
}
