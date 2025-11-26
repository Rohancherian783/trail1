Message processData(Message message) {
    def map = message.getProperty("attachmentsMap")
    messageLogFactory.getMessageLog(message)?.addAttachmentAsString("AttachmentsDebug", map.keySet().toString(), "text/plain")
    return message
}
