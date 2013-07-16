/* Created by Ben-J*/
package com.balinea.classes;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.balinea.CreateAccountActivity;
import com.balinea.MyBalineaActivity;
import com.balinea.R;
import com.balinea.R.bool;
import com.balinea.R.string;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.view.LayoutInflater;
import android.widget.Toast;

public class Tools {

	Context context;
	public static final String META = "<head><meta name=viewport content=target-densitydpi=medium-dpi, width=device-width/></head>";

	public Tools(Context context) {
		this.context = context;
	}

	public boolean isOnline(Boolean withAlert) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		toast(context.getString(string.no_network));
		return false;
	}

	public static boolean isOnline(Boolean withAlert, Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		Toast.makeText(context, context.getString(string.no_network),
				Toast.LENGTH_SHORT).show();
		return false;
	}

	public void toast(String tstString) {
		Toast toast = Toast.makeText(context, tstString, Toast.LENGTH_SHORT);
		toast.show();
	}

	// SHA 256
	public static String encodeSHA256(String key, String data) {
		try {
			Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
			SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(),
					"HmacSHA256");
			sha256_HMAC.init(secret_key);
			return Base64.encodeToString(sha256_HMAC.doFinal(data.getBytes()),
					0);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "error";

	}

	public static String encodeBase64(String data) {
		return Base64.encodeToString(data.getBytes(), 0);
	}

	public static String encodeURL(String url) {
		url = url.replace("\n", "");
		url = url.replace("+", "%2B");
		url = url.replace(" ", "%20");
		return url;
	}

	public static String toMD5(String in) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
			digest.reset();
			digest.update(in.getBytes());
			byte[] a = digest.digest();
			int len = a.length;
			StringBuilder sb = new StringBuilder(len << 1);
			for (int i = 0; i < len; i++) {
				sb.append(Character.forDigit((a[i] & 0xf0) >> 4, 16));
				sb.append(Character.forDigit(a[i] & 0x0f, 16));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String format(Object object) {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(0);
		return nf.format(object);
	}

	public static boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
    
    public final static boolean isValidEmail(CharSequence target) {
	    if (target == null) {
	        return false;
	    } else {
	        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
	    }
	}
}
