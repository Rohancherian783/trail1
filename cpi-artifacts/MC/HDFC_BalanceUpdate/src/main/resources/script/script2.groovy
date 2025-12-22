/* Refer the link below to learn more about the use cases of script.
https://help.sap.com/viewer/368c481cd6954bdfa5d0435479fd4eaf/Cloud/en-GB/148851bf8192412cba1f9d2c17f4bd25.html

If you want to know more about the SCRIPT APIs, refer the link below
https://help.sap.com/doc/a56f52e1a58e4e2bac7f7adbf45b2e26/Cloud/en-GB/index.html */
import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.*;
import groovy.json.JsonOutput;
import groovy.json.JsonSlurper
def Message processData(Message message) {
    //Body
    def body = message.getBody(java.lang.String)as String;
    def jsonSlurper = new JsonSlurper()
    persedbody= jsonSlurper.parseText(body);
/*To set the body, you can use the following method. Refer SCRIPT APIs document for more detail*/
    //message.setBody(body + " Body is modified");
       def payload = [
    alg: "RS256",
    typ: "JWT"
]
 
def payloadJson = JsonOutput.toJson(payload)
    String header_val =payloadJson;
    String Encoded_header =header_val.bytes.encodeBase64().toString()
    
    def Jsonbody = JsonOutput.toJson(persedbody)
   // def messageBytes = body.getBytes(UTF_8)
    String Encoded_body =Jsonbody.bytes.encodeBase64().toString();
    String header_body =Encoded_header+"."+Encoded_body;
    message.setBody(header_body);
    
    return message;
}