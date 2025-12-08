import com.sap.gateway.ip.core.customdev.util.Message

Message processData(Message message) {
    def supplierInvoice = message.getProperty("SupplierInvoice")
    def fiscalYear = message.getProperty("FiscalYear")

    // Combine both (simple concatenation)
    def linkedKey = "${supplierInvoice}${fiscalYear}"

    message.setProperty("LinkedSAPObjectKey", linkedKey)
    return message
}
