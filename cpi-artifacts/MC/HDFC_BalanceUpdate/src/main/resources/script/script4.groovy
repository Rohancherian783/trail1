/* Refer the link below to learn more about the use cases of script.
https://help.sap.com/viewer/368c481cd6954bdfa5d0435479fd4eaf/Cloud/en-GB/148851bf8192412cba1f9d2c17f4bd25.html

If you want to know more about the SCRIPT APIs, refer the link below
https://help.sap.com/doc/a56f52e1a58e4e2bac7f7adbf45b2e26/Cloud/en-GB/index.html */
import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import com.sap.it.api.ITApi;
import com.sap.it.api.ITApiFactory
import com.sap.it.api.securestore.*;
import com.sap.it.api.keystore.*;
import javax.net.ssl.KeyManager;

import java.security.interfaces.RSAPublicKey;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.KeyStoreException;
import java.security.KeyFactory;
import java.security.PublicKey;
def Message processData(Message message) {
    //Body
    def body = message.getBody(java.lang.String) as String;
 /*def pubkey='''-----BEGIN PUBLIC KEY-----
MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAswriruPkyv8lKMMr8cAF
h+EpJBFmdzXqnWyHu1bmpYXJzIZAWgfRLFOfKw6IYEF6WihA8yIJU5psttI/QDdl
QjXfoNWg1nA+5GMkwGmceEFsZDWHAD5QlMdh8WK3gk07Ws5yRtSUMieSW+JCf0mX
s1HU/6KTFMJlxAERz4+p2I8FQoKgucicRRyfW040vyZEfgdbm4vSLdfsdzIcLFbZ
sy7CN0ZCCncln6Bc5hbFLf1kUAwiD3Ta2p1DA7MuGYHcALs8W3b0ghA5lD3FWYPX
jmNpFD6gdNuO9Hq9OVyFZ/TmCOw6jWn7OHGdNUTVlcQVlx7+V52S4QKWWDLhM+es
KQIDAQAB
-----END PUBLIC KEY-----''';

KeyFactory kf = KeyFactory.getInstance("RSA");
PublicKey publicKey = kf.generatePublic(new X509EncodedKeySpec(pubkey.getBytes()));*/
 KeystoreService service = ITApiFactory.getService(KeystoreService.class, null);
 X509Certificate cert = service.getCertificate("hdfc_public_certificate");  //get the certificate from the keystore
 RSAPublicKey publicKey = (RSAPublicKey) cert.getPublicKey(); //get the RSA publickey from the certificate.

//message.setProperty("publickey",publicKey);

    Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding"); 
    cipher.init(Cipher.ENCRYPT_MODE, publicKey);
    byte[] encryptedMessage = Base64.getEncoder().encodeToString(cipher.doFinal(body.getBytes()));
    message.setBody(encryptedMessage);
    return message;
}