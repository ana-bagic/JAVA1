package hr.fer.oprpp1.hw05.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Razred omogućava simetrično kriptiranje i dekriptiranje datoteke korištenjem AES kriptoalgoritma i 128-bit ključa,
 * te računanje i provjeru SHA-256 digitalnog potpisa.
 * 
 * @author Ana Bagić
 *
 */
public class Crypto {

	/**
	 * Ulazna metoda za izvođenje programa
	 * 
	 * @param args računanje i provjera SHA-256 (checksha datotekaZaProvjeru),
	 * kriptiranje (encrypt izDatoteke uDatoteku),
	 * dekriptiranje (decrypt izDatoteke uDatoteku)
	 */
	public static void main(String[] args) {
		
		switch (args[0]) {
		case "checksha" -> checkSha(args[1]);
		case "encrypt" -> encryptDecrypt(args[1], args[2], true);
		case "decrypt" -> encryptDecrypt(args[1], args[2], false);
		default ->
		throw new IllegalArgumentException("Unexpected value: " + args[0] + ". Please use 'checksha', 'encrypt' or 'decrypt'.");
		}
		
	}

	/**
	 * Kriptira odnosno dekriptira datoteku korištenjem AES kriptoalgoritma i 128-bit ključa.
	 * 
	 * @param fromFile datoteka koju se želi kriptirati/dekriptirati
	 * @param toFile datoteka u koju se želi pohraniti rezultat kriptiranja/dekriptiranja
	 * @param encrypt <code>true</code> ako se želi kriptirati,
	 * <code>false</code> ako se želi dekriptirati.
	 */
	private static void encryptDecrypt(String fromFile, String toFile, boolean encrypt) {
		Scanner sc = new Scanner(System.in);
		String keyText, ivText;
		
		while(true) {
			System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
			keyText = sc.nextLine();
			
			if(keyText.length() != 32) {
				System.out.println("Password has to contain 32 characters");
				continue;
			}
			
			break;
		}
		
		while(true) {
			System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
			ivText = sc.nextLine();
			
			if(ivText.length() != 32) {
				System.out.println("Initialization vector has to contain 32 characters");
				continue;
			}
			
			break;
		}
		
		sc.close();
		
		SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
		
		Cipher cipher = null;
		
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
		}
		
		try {
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
		} catch (InvalidKeyException e) {
			System.err.println(keyText + " is not a valid key");
		} catch (InvalidAlgorithmParameterException e) {
			System.err.println(ivText + " is not a valid initialization vector");
		}
		
		try (InputStream is = Files.newInputStream(Paths.get(fromFile));
				OutputStream os = Files.newOutputStream(Paths.get(toFile))) { 
			byte[] original = new byte[4096];
			byte[] processed;
			
			while(true) {
				int bytesRead = is.read(original);
				
				if(bytesRead < 1)
					break;
				
				processed = cipher.update(original, 0, bytesRead);
				
				if(processed != null)
					os.write(processed);
			}
			
			processed = cipher.doFinal();
			os.write(processed);
		} catch (IOException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
		
		System.out.println((encrypt ? "Encryption" : "Decryption") + " completed. Generated file "
				+ toFile + " based on file " + fromFile + ".");
	}

	/**
	 * Računa i provjerava SHA-256 digitalni potpis datoteke.
	 * 
	 * @param fileToCheck datoteka čiji se digitalni potpis želi izračunati
	 */
	private static void checkSha(String fileToCheck) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Please provide expected sha-256 digest for " + fileToCheck + ":");
		String expectedDigest = sc.nextLine();
		
		sc.close();
		
		MessageDigest md = null;
		byte[] actualDigestByte = null;
		
		try {
			md = MessageDigest.getInstance("SHA256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		try (InputStream is = Files.newInputStream(Paths.get(fileToCheck))) { 
			byte[] buff = new byte[4096];
			
			while(true) {
				int bytesRead = is.read(buff);
				
				if(bytesRead < 1)
					break;
				md.update(buff, 0, bytesRead);
			}
			
			actualDigestByte = md.digest();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String actualDigest = Util.bytetohex(actualDigestByte);
		
		System.out.println("Digesting completed. Digest of " + fileToCheck + (actualDigest.equals(expectedDigest)
				? " matches expected digest."
				: " does not match expected digest. Digest was: " + actualDigest));
		
	}
}
