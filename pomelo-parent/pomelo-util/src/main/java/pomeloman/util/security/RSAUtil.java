package pomeloman.util.security;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.Cipher;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * <p>
 * 非对称加密<br>
 * 公钥用于对数据进行加密，私钥对数据进行解密，两者不可逆。公钥和私钥是同时生成的，一一对应。<br>
 * 比如：A拥有公钥，B拥有公钥和私钥。A将数据通过公钥进行加密后，发送密文给B，B可以通过私钥进行解密。
 * <br>
 * Asymmetric encryption<br>
 * The public key is used to encrypt the data and the private key to decrypt the data, both of which are irreversible. The public key and private key are generated at the same time, one by one. <br>
 * For example: A has a public key, B has a public key and private key. A encrypts the data with the public key, sends the ciphertext to B, and B decrypts it with the private key.
 * </p>
 * @ClassName RSAUtil.java
 * @author PomeloMan
 */
public class RSAUtil {

	public final static String RSA_ALGORITHM = "RSA";
	public final static int DEFAULT_KEYSIZE = 1024;

	static {
		/**
		 * <p>引入密钥库<br>Introduce the keystore</p>
		 */
		Security.addProvider(new BouncyCastleProvider()); // BC库
	}

	/**
	 * <p>获取公钥和私钥的密钥对<br>Obtain the key pair of the public key and the private key</p>
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static KeyPair getKeyPair() throws NoSuchAlgorithmException {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM);
		keyPairGenerator.initialize(DEFAULT_KEYSIZE);
		return keyPairGenerator.generateKeyPair();
	}

	/**
	 * <p>加密<br>Encrypt</p>
	 * @param publicKey
	 * @param src
	 * @return String
	 * @throws UnsupportedEncodingException
	 * @throws GeneralSecurityException
	 */
	public static String encrypt(RSAPublicKey publicKey, String src)
			throws UnsupportedEncodingException, GeneralSecurityException {
		return EncryptHelper.encode(encrypt(publicKey, src.getBytes(EncryptHelper.DEFAULT_CHARSET)));
	}

	/**
	 * <p>加密<br>Encrypt</p>
	 * @param publicKey
	 * @param srcBytes
	 * @return byte[]
	 * @throws GeneralSecurityException
	 */
	public static byte[] encrypt(RSAPublicKey publicKey, byte[] srcBytes)
			throws GeneralSecurityException {
		Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return cipher.doFinal(srcBytes);
	}

	/**
	 * <p>解密<br>Decrypt</p>
	 * @param privateKey
	 * @param cipher
	 * @return
	 * @throws GeneralSecurityException
	 * @throws UnsupportedEncodingException
	 */
	public static String decrypt(RSAPrivateKey privateKey, String cipher)
			throws GeneralSecurityException, UnsupportedEncodingException {
		return new String(decrypt(privateKey, EncryptHelper.decode(cipher)), EncryptHelper.DEFAULT_CHARSET);
	}

	/**
	 * <p>解密<br>Decrypt</p>
	 * @param privateKey 私钥
	 * @param srcBytes
	 * @return byte[]
	 * @throws GeneralSecurityException
	 */
	public static byte[] decrypt(RSAPrivateKey privateKey, byte[] srcBytes)
			throws GeneralSecurityException {
		Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return cipher.doFinal(srcBytes);
	}

}
