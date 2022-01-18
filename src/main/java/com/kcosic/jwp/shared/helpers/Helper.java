package com.kcosic.jwp.shared.helpers;

import com.kcosic.jwp.shared.enums.AttributeEnum;
import com.kcosic.jwp.shared.enums.JspEnum;
import com.kcosic.jwp.shared.model.entities.CustomerEntity;
import com.kcosic.jwp.shared.model.entities.LogEntity;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Helper {
    public static String hash(String plainText) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedHash = digest.digest(plainText.getBytes(StandardCharsets.UTF_8));
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
        request.setAttribute(AttributeEnum.HAS_ERROR.toString(), hasError);

        request.getRequestDispatcher(page.getJsp()).forward(request, response);
    }

    public static void setSessionData(HttpServletRequest request, AttributeEnum key, Object data) {
        request.getSession().setAttribute(key.toString(), data); // add to session
    }

    public static void removeSessionData(HttpServletRequest request, AttributeEnum key) {
        request.getSession().removeAttribute(key.toString());
    }

    @SuppressWarnings("unchecked")
    public static <T> T getSessionData(HttpServletRequest request, AttributeEnum key, Class<T> type) {
        var data = request.getSession().getAttribute(key.toString());
        if (data == null) {
            return null;
        }
        return (T) data;
    }

    public static void setSessionIfNotExists(HttpServletRequest request, AttributeEnum key, Object data) {
        if (request.getSession().getAttribute(key.toString()) == null) {
            request.setAttribute(key.toString(), data);
        }
    }

    public static boolean isUserAuthenticated(String jsonUserData) {
        return false;
    }

    public static CustomerEntity createGuestCustomer() {
        var guest = new CustomerEntity();
        guest.setRole(DbHelper.retrieveRole(3));
        return DbHelper.createCustomer(guest);
    }

    public static void addAttributeIfNotExists(HttpServletRequest request, AttributeEnum attribute, Object value) {
        if (request.getAttribute(attribute.toString()) == null) {
            request.setAttribute(attribute.toString(), value);
        }
    }


    public static void addAttribute(HttpServletRequest request, AttributeEnum attribute, Object value) {
        request.setAttribute(attribute.toString(), value);
    }

    public static void removeAttribute(HttpServletRequest request, AttributeEnum attribute) {
        request.removeAttribute(attribute.toString());
    }

    public static String getUploadPath(ServletContext context) {
        String uploadPath = context.getRealPath("") + File.separator + "assets" + File.separator + "images";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            //noinspection ResultOfMethodCallIgnored
            uploadDir.mkdir();
        }
        return uploadPath;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isNullOrEmpty(String string) {
        return !(string != null && !string.isEmpty());
    }

    public static void log(HttpServletRequest request, String customerMail, String actionName){
        var newLog = new LogEntity();
        newLog.setCustomer(customerMail);
        newLog.setActionTime(new java.sql.Timestamp(System.currentTimeMillis()));
        newLog.setActionName(actionName);
        newLog.setIpAddress(request.getRemoteAddr());
        DbHelper.addLog(newLog);
    }

    public static String formatAmount(String totalPrice) {
        var decimal = totalPrice.substring(totalPrice.lastIndexOf('.') + 1);
        if(decimal.equals("00") || decimal.equals("0")){
            return totalPrice.substring(0, totalPrice.lastIndexOf('.')).replace(",","");
        }
        return totalPrice.replace(",","");
    }
}
