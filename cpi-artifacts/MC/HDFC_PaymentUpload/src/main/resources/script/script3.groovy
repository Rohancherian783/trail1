/* Refer the link below to learn more about the use cases of script.
https://help.sap.com/viewer/368c481cd6954bdfa5d0435479fd4eaf/Cloud/en-GB/148851bf8192412cba1f9d2c17f4bd25.html

If you want to know more about the SCRIPT APIs, refer the link below
https://help.sap.com/doc/a56f52e1a58e4e2bac7f7adbf45b2e26/Cloud/en-GB/index.html */
import com.sap.gateway.ip.core.customdev.util.Message;
//import java.util.HashMap;
//import java.util.Base64;
import java.util.*;
import groovy.json.JsonOutput;
def Message processData(Message message) {
    //Body
    def body = message.getBody(java.lang.String)as String;
/*To set the body, you can use the following method. Refer SCRIPT APIs document for more detail*/
    //message.setBody(body + " Body is modified");
    //Headers
    def headers = message.getHeaders();
    def sign_value = headers.get("SAPSimpleSignatureValue");
    String signature_val= sign_value;
   String Encoded_sign =signature_val.bytes.encodeBase64().toString()
   //Base64.getUrlEncoder().withoutPadding().encodeToString(signature_val.getBytes());
   //Properties
    //def properties = message.getProperties();
    //header_value = properties.get("JWT_header");
    def payload = [
    alg: "RS256",
    typ: "JWT"
]
 
def payloadJson = JsonOutput.toJson(payload)
    String header_val =payloadJson;
    String Encoded_header =header_val.bytes.encodeBase64().toString()
    //Base64.getUrlEncoder().withoutPadding().encodeToString(header_val.getBytes());
    String Encoded_body =body.bytes.encodeBase64().toString()
    //Base64.getUrlEncoder().withoutPadding().encodeToString(body.getBytes());
    String JWS =Encoded_header+"."+Encoded_body+"."+Encoded_sign;
    
    message.setBody(JWS);
    return message;
}