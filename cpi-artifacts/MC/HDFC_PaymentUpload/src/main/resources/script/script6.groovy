import com.sap.it.api.ITApi
import com.sap.it.api.ITApiFactory
import com.sap.it.api.securestore.*;
import com.sap.it.api.keystore.*;
import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.ArrayList
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.util.HashMap;

def Message processData(Message message){


String password;
def body = message.getBody((byte[]).class);
 def pmap = message.getProperties(); 
 randomStr = pmap.get("symmetrickey");
 def iv = "1234567890123456";


//def iv = pmap.get("newproperty");
IV_param = new IvParameterSpec(iv.getBytes());
 Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
 SecretKey secretKey = new SecretKeySpec(randomStr.getBytes(),"AES");
 cipher.init(Cipher.DECRYPT_MODE, secretKey,IV_param);
 byte[] clearMessage = cipher.doFinal(Base64.getDecoder().decode(body));

 message.setBody(clearMessage)
return message
}

