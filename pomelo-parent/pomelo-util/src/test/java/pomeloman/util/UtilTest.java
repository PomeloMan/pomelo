package pomeloman.util;

import java.io.IOException;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;

import org.junit.Test;

import pomeloman.util.commons.BinaryUtil;
import pomeloman.util.commons.FileUtil;
import pomeloman.util.commons.MathUtil;
import pomeloman.util.security.IrreversibleEncryptor;
import pomeloman.util.security.IrreversibleEncryptor.Algorithm;
import pomeloman.util.security.RSAUtil;
import pomeloman.util.security.ReversibleEncryptor;

public class UtilTest {

	/******************************** ↓文件工具↓ ********************************/
	@Test
	public void basic() {
//		FileUtil.getFile("doc", "README.txt");
		System.out.println(FileUtil.getFile(""));
	}

	@Test
	public void decimal() {
//		System.out.println(DecimalUtil.format("0.00%", 0.999956));
		System.out.println(MathUtil.subtract(1, 2));
		System.out.println(MathUtil.divide(3, 5));
		System.out.println(MathUtil.round(3.1415926,4));
	}

	@Test
	public void zip() throws IOException {
	}

	@Test
	public void unzip() throws IOException {
	}

	/******************************** ↑文件工具↑ ********************************/
	/******************************** ↓加密解密↓ ********************************/
	private String securityString = "pomeloman*@%";

	@Test
	public void MD5Test() throws Exception {
		System.out.println("Before encryption: " + securityString);
		System.out.println("After encryption: " + IrreversibleEncryptor.encrypt(securityString, Algorithm.MD5));
	}

	@Test
	public void SHATest() throws Exception {
		System.out.println("Before encryption: " + securityString);
		System.out.println("After encryption: " + IrreversibleEncryptor.encrypt(securityString, Algorithm.SHA));
	}

	@Test
	public void DESTest() throws Exception {
		String key = ReversibleEncryptor.getSecretKeyHexString(pomeloman.util.security.ReversibleEncryptor.Algorithm.DES);
		String cipher = ReversibleEncryptor.encrypt(securityString, key, pomeloman.util.security.ReversibleEncryptor.Algorithm.DES);
		System.out.println("Before encryption: " + securityString);
		System.out.println("After encryption: " + cipher);
		System.out.println("After decryption: " + ReversibleEncryptor.decrypt(cipher, key, pomeloman.util.security.ReversibleEncryptor.Algorithm.DES));
	}

	@Test
	public void AESTest() throws Exception {
		String key = ReversibleEncryptor.getSecretKeyHexString(pomeloman.util.security.ReversibleEncryptor.Algorithm.AES);
		String cipher = ReversibleEncryptor.encrypt(securityString, key, pomeloman.util.security.ReversibleEncryptor.Algorithm.AES);
		System.out.println("Before encryption: " + securityString);
		System.out.println("After encryption: " + cipher);
		System.out.println("After decryption: " + ReversibleEncryptor.decrypt(cipher, key, pomeloman.util.security.ReversibleEncryptor.Algorithm.AES));
	}

	@Test
	public void DESedeTest() throws Exception {
		String key = ReversibleEncryptor.getSecretKeyHexString(pomeloman.util.security.ReversibleEncryptor.Algorithm.DESede);
		String cipher = ReversibleEncryptor.encrypt(securityString, key, pomeloman.util.security.ReversibleEncryptor.Algorithm.DESede);
		System.out.println("Before encryption: " + securityString);
		System.out.println("After encryption: " + cipher);
		System.out.println("After decryption: " + ReversibleEncryptor.decrypt(cipher, key, pomeloman.util.security.ReversibleEncryptor.Algorithm.DESede));
	}

	@Test
	public void RSATest() throws Exception {
		KeyPair pair = RSAUtil.getKeyPair();
		String cipher = RSAUtil.encrypt((RSAPublicKey) pair.getPublic(), securityString);
		System.out.println("Before encryption: " + securityString);
		System.out.println("After encryption: " + cipher);
		System.out.println("After decryption: " + RSAUtil.decrypt((RSAPrivateKey) pair.getPrivate(), cipher));
	}

	/******************************** ↑加密解密↑ ********************************/
	/******************************** ↓二进制工具↓ ********************************/
	@Test
	public void StringUtilTest() {
		System.out.println(Arrays.toString(BinaryUtil.toIndexArray(5)));
		System.out.println(Arrays.toString(BinaryUtil.toBinaryArray(5)));
	}
	/******************************** ↑二进制工具↑ ********************************/
}
