/* Refer the link below to learn more about the use cases of script.
https://help.sap.com/viewer/368c481cd6954bdfa5d0435479fd4eaf/Cloud/en-GB/148851bf8192412cba1f9d2c17f4bd25.html

If you want to know more about the SCRIPT APIs, refer the link below
https://help.sap.com/doc/a56f52e1a58e4e2bac7f7adbf45b2e26/Cloud/en-GB/index.html */
import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;

def Message payloadLogger(Message message) {
    def body = message.getBody(java.lang.String)
    def properties = message.getProperties();
    String PAYMENT_REF = properties.get("PAYMENT_REF_NO");
    
    def messageLog = messageLogFactory.getMessageLog(message)
    if (messageLog != null) {
        messageLog.addAttachmentAsString("Request"+PAYMENT_REF, body, 'text/plain');
    }
    return message
}
