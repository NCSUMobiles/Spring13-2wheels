package com.ncsu.edu.spinningwellness.encrypt;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class PasswordEncoder {
	  /**
     * Logger for this class
     */

    private static PasswordEncoder instance;

    /**
     * Count for the number of time to hash,
     * more you hash more difficult it would be for the attacker
     */
    private final static int ITERATION_COUNT = 5;

    /**
     * Empty Constructor
     */
    public PasswordEncoder() {
    }

    /**
     * @return
     * @author Freeloaders
     */
    public static synchronized PasswordEncoder getInstance()
    {
    	if (instance == null)
    	{
    		PasswordEncoder returnPasswordEncoder = new PasswordEncoder();
    		return returnPasswordEncoder;
    	} else {
    		return instance;
    	}
     }

    /**
     *
     * @param password
     * @param saltKey
     * @return
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @author freeloaders
     */
    public synchronized String encode(String password, String saltKey)
	    throws NoSuchAlgorithmException, IOException {

	String encodedPassword = null;
	byte[] salt = base64ToByte(saltKey);

	MessageDigest digest = MessageDigest.getInstance("SHA-256");
	digest.reset();
	digest.update(salt);

	byte[] btPass = digest.digest(password.getBytes("UTF-8"));
	for (int i = 0; i < ITERATION_COUNT; i++) {
	    digest.reset();
	    btPass = digest.digest(btPass);
	}

	encodedPassword = byteToBase64(btPass);

	return encodedPassword;
    }

    /**
     * @param str
     * @return byte[]
     * @throws IOException
     */
    private byte[] base64ToByte(String str) throws IOException {

	BASE64Decoder decoder = new BASE64Decoder();
	byte[] returnbyteArray = decoder.decodeBuffer(str);
	return returnbyteArray;
    }

    /**
     * @param bt
     * @return String
     * @throws IOException
     */
    private String byteToBase64(byte[] bt) {

	BASE64Encoder endecoder = new BASE64Encoder();
	String returnString = endecoder.encode(bt);
	return returnString;
    }
}
