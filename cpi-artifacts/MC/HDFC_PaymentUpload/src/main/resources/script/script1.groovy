/* Refer the link below to learn more about the use cases of script.
https://help.sap.com/viewer/368c481cd6954bdfa5d0435479fd4eaf/Cloud/en-GB/148851bf8192412cba1f9d2c17f4bd25.html

If you want to know more about the SCRIPT APIs, refer the link below
https://help.sap.com/doc/a56f52e1a58e4e2bac7f7adbf45b2e26/Cloud/en-GB/index.html */
import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import java.security.SecureRandom;
import java.util.ArrayList
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
def Message processData(Message message){

String password;
 def body = message.getBody(java.lang.String)as String;
def randomStr;
def key;

String alphabet = (('A'..'N')+('P'..'Z')+('a'..'k')+('m'..'z')+('2'..'9')).join()
def length = 32
randomStrs = new Random().with {
(1..length).collect { alphabet[ nextInt( alphabet.length() ) ] }.join()
}
message.setProperty("randomString32byte", randomStrs);

 String ivstr ="1234567890123456";
//iv =randomStr.getBytes();
message.setProperty("ivparameter",ivstr);
String new_body = ivstr + body;
 IV_param= new IvParameterSpec(ivstr.getBytes());

 Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            SecretKey secretKey = new SecretKeySpec(randomStrs.getBytes(), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey,IV_param);
            //String pCerB64Enc = new String(Base64.getEncoder().encodeToString(new_body.getBytes()));
           byte[] encryptedMessage = Base64.getEncoder().encodeToString(cipher.doFinal(new_body.getBytes()));//cipher.doFinal(body);
          //byte[] encryptedMessage = cipher.doFinal(pCerB64Enc.getBytes());

 //String new_body = iv.toString() + encryptedMessage.toString();
message.setBody(encryptedMessage)

    return message;
}