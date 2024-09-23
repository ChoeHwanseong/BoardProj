package model;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

public class JWT {
	private static SecretKey scSecretKey;
	private static final String SECRETKEY_PATH="/resource/tokens";
	private static final String KEY = "secretKey";
	
	static {
		ResourceBundle resourceBundle = ResourceBundle.getBundle(SECRETKEY_PATH);
		String secretkey = resourceBundle.getString(KEY);
		byte[] secret = Base64.getEncoder().encodeToString(secretkey.getBytes()).getBytes(StandardCharsets.UTF_8);
		scSecretKey = Keys.hmacShaKeyFor(secret);
	}
	
	public static String createToken(String username, Map<String, Object> payloads, long expTime) {
		long curTime = System.currentTimeMillis();
		expTime += curTime;
		
		Map<String, Object> headers = new HashMap();
		headers.put("typ","JWT");
		headers.put("alg","HS256");
		
		JwtBuilder builder = Jwts.builder().header().add(headers).and()
							.claims(payloads).subject(username)
							.issuedAt(new Date()).expiration(new Date(expTime))
							.signWith(scSecretKey, Jwts.SIG.HS256);
		
		return builder.compact();
	}
	
	public static boolean verifyToken(String token) {
		try {
			Jws<Claims> claims = Jwts.parser().verifyWith(scSecretKey).build().parseSignedClaims(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {e.printStackTrace();}
		return false;
	}
	
	public static String getToken(HttpServletRequest req, String tokenName) {
		String token="";
		Cookie[] cookies = req.getCookies();
		
		if(cookies!=null) {
			for(Cookie cookie: cookies) {
				if(cookie.getName().equals(tokenName)) {
					token = cookie.getValue();
				}
			}
		}
		return token;
	}
	
	public static Map<String, Object> getPayloads(String token){
		Map<String, Object> claims = new HashMap();
		try {
			claims = Jwts.parser().verifyWith(scSecretKey).build()
					.parseSignedClaims(token).getPayload();
		} catch (Exception e) {e.printStackTrace();}
		return claims;
	}
}
