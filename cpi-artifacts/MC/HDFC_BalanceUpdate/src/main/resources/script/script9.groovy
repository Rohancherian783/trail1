/* Refer the link below to learn more about the use cases of script.
https://help.sap.com/viewer/368c481cd6954bdfa5d0435479fd4eaf/Cloud/en-GB/148851bf8192412cba1f9d2c17f4bd25.html

If you want to know more about the SCRIPT APIs, refer the link below
https://help.sap.com/doc/a56f52e1a58e4e2bac7f7adbf45b2e26/Cloud/en-GB/index.html */
import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
def Message processData(Message message) {
    //Body
    //def body = message.getBody();
/*To set the body, you can use the following method. Refer SCRIPT APIs document for more detail*/
    //message.setBody(body + " Body is modified");
   String CustomerId;
    //Properties
    def properties = message.getProperties();
    String value = properties.get("BankAccount");
    if(value =="50200031861762")
     CustomerId="115152740";
    else if(value =="50200035703531")
     CustomerId="127443550";
    else if(value =="50200008346326")
     CustomerId="58380546"; 
    else if(value =="50200049881720")
     CustomerId="184518385"; 
     else
     CustomerId="115152740";
    
    message.setProperty("CustomerId",CustomerId);
    
    return message;
}