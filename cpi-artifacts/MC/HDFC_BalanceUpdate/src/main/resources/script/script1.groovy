import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
def Message processData(Message message) {
   
    //Properties
    def properties = message.getProperties();
    def cookies = properties.get("cookie");
    String str ="";
    message.setHeader("cookie", str.join(";", cookies));
   
    return message;
}
