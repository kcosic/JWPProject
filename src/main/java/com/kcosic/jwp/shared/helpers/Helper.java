package com.kcosic.jwp.shared.helpers;

import com.kcosic.jwp.shared.enums.AttributeEnum;
import com.kcosic.jwp.shared.enums.JspEnum;
import com.kcosic.jwp.shared.model.entities.CustomerEntity;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Helper {

    public static String hash(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(encodedHash);
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static void goToPage(HttpServletRequest request, HttpServletResponse response, JspEnum page, boolean hasError) throws ServletException, IOException {
        if(hasError){
            request.setAttribute(AttributeEnum.HAS_ERROR.toString(), true);
        }
        request.getRequestDispatcher(page.toString()).forward(request, response);
    }

    public static void setSessionData(HttpServletRequest request, AttributeEnum key, Object data){
        request.getSession().setAttribute(key.toString(), data); // add to session
    }

    public static void removeSessionData(HttpServletRequest request, AttributeEnum key){
        request.getSession().removeAttribute(key.toString());
    }

    @SuppressWarnings("unchecked")
    public static <T> T getSessionData(HttpServletRequest request, AttributeEnum key, Class<T> type){
        var data = request.getSession().getAttribute(key.toString());
        if(data == null){
            return null;
        }
        return (T)data;
    }

    public static boolean isUserAuthenticated(String jsonUserData) {
        return false;
    }

    public static CustomerEntity createGuestCustomer() {
        var guest = new CustomerEntity();
        guest.setRoleId(3);
        return DbHelper.createCustomer(guest);
    }
}
