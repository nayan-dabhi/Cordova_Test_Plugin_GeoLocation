package com.example.plugin;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class McomChecksumUtil {
 
	/**
	 * @param data
	 * @param key
	 * @return
	 * @throws GeneralSecurityException
	 */
	public static String generateChecksum(String input, String key)
			throws GeneralSecurityException {
        
    	byte[] hmacData = null;
    	String checkSumValue=null;
    	StringBuffer ls_sb=new StringBuffer();
    	SecretKeySpec secretKey=null;
    	Mac mac = Mac.getInstance("HmacSHA256");
    	
        try {
        	
            secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
            
            mac.init(secretKey);
            hmacData = mac.doFinal(input.getBytes("UTF-8"));
            
    		for(int i=0;i<hmacData.length;i++){
    			ls_sb.append(char2hex(hmacData[i]));
    		}
    		
    		checkSumValue=ls_sb.toString();
         
        } catch (UnsupportedEncodingException e) {
            throw new GeneralSecurityException(e);
        }
        
        return checkSumValue;
    }
    
    
    /**
     * @param x
     * @return
     */
    public static String char2hex(byte x){
    	
		char arr[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F' };

		char c[] = { arr[(x & 0xF0) >> 4], arr[x & 0x0F] };
		return (new String(c));
	}
    
}