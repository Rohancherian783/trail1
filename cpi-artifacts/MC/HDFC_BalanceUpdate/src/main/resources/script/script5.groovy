/* Refer the link below to learn more about the use cases of script.
https://help.sap.com/viewer/368c481cd6954bdfa5d0435479fd4eaf/Cloud/en-GB/148851bf8192412cba1f9d2c17f4bd25.html

If you want to know more about the SCRIPT APIs, refer the link below
https://help.sap.com/doc/a56f52e1a58e4e2bac7f7adbf45b2e26/Cloud/en-GB/index.html */import com.sap.it.api.ITApi;
import com.sap.it.api.ITApi;
import com.sap.it.api.ITApiFactory
import com.sap.it.api.securestore.*;
import com.sap.it.api.keystore.*;
import javax.net.ssl.KeyManager;
import java.util.Base64;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Key;
import java.security.spec.*;
import java.security.spec.RSAPublicKeySpec;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import javax.xml.bind.DatatypeConverter;
import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import javax.net.ssl.*;
import java.security.cert.*;
import java.util.ArrayList
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


def Message processData(Message message) {
    //Body
   
   def body = message.getBody(java.lang.String) as String;
   KeystoreService service = ITApiFactory.getService(KeystoreService.class, null);
   Key pKey = service.getKey("sap_cloudintegrationcertificate");
   String pKeyB64Enc = new String(Base64.getEncoder().encode(pKey.getEncoded()));
     
  KeyFactory kf = KeyFactory.getInstance("RSA");
  PrivateKey privateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(pKeyB64Enc.getBytes()));

     
     //SecretKey secretKey = new SecretKeySpec(pubkey, "RSA");
    Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding"); 
    cipher.init(Cipher.DECRYPT_MODE,privateKey); //Decrypting the EncryptedKey with our private key
    byte[] clearMessage = cipher.doFinal(Base64.getDecoder().decode(body.getBytes()));
    message.setBody(clearMessage);
    return message;
}