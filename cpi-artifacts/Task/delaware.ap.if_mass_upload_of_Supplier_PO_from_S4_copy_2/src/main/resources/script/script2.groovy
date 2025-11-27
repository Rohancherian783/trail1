import com.sap.gateway.ip.core.customdev.util.Message

Message processData(Message message) {

    // Get PO and Item from previous Content Modifier
    String po   = (message.getProperty("PurchaseOrder") ?: "").toString().trim()
    String item = (message.getProperty("PurchaseOrderItem") ?: "").toString().trim()

    // Build LinkedSAPObjectKey = PurchaseOrder + '000' + PurchaseOrderItem
    String linkedKey = po + "000" + item

    // Store it as an Exchange Property (for OData adapter)
    message.setProperty("LinkedSAPObjectKey", linkedKey)

    // Optional: also add as header for easy debugging/logging
    message.setHeader("LinkedSAPObjectKey_Debug", linkedKey)

    return message
}
