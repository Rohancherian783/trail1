/* Refer the link below to learn more about the use cases of script.
https://help.sap.com/viewer/368c481cd6954bdfa5d0435479fd4eaf/Cloud/en-US/148851bf8192412cba1f9d2c17f4bd25.html

If you want to know more about the SCRIPT APIs, refer the link below
https://help.sap.com/doc/a56f52e1a58e4e2bac7f7adbf45b2e26/Cloud/en-US/index.html */
import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import java.util.Base64;
def Message processData(Message message) {
    //Body
    def body = message.getBody(java.lang.String)as String;
    Integer length = body.length();
    def newbody = body.substring(16,length);
    String encoded_body=newbody.substring((newbody.indexOf('.')+1),newbody.indexOf('.',50))
    
  
   //String decodedbody = encoded_body.bytes.decodeBase64().toString();
  byte[] decodedBytes = Base64.getDecoder().decode(encoded_body);
String decodedString = new String(decodedBytes);  
    
    
    message.setBody(decodedString);
    
    return message;
}