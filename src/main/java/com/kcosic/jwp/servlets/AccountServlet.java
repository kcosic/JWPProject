package com.kcosic.jwp.servlets;

import com.kcosic.jwp.shared.enums.AttributeEnum;
import com.kcosic.jwp.shared.enums.JspEnum;
import com.kcosic.jwp.shared.helpers.DbHelper;
import com.kcosic.jwp.shared.helpers.Helper;
import com.kcosic.jwp.shared.model.BaseServlet;
import com.kcosic.jwp.shared.model.entities.AddressEntity;
import com.kcosic.jwp.shared.model.entities.CustomerEntity;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Date;
import java.util.Objects;

@WebServlet(name = "AccountServlet", value = "/account")
public class AccountServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processAccountGetRequest(request, response);
    }
//TODO File upload, provjeriti sto se uploada

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var type = request.getParameter(AttributeEnum.TYPE.toString());
        switch (type) {
            case "details" -> handleDetailsChange(request);
            case "default-address" -> handleDefaultAddressChange(request);
            case "address" -> handleAddressChange(request);
            case "admin-customers" -> handleAdminCustomerChange(request);
            case "admin-items" -> handleAdminItemsChange(request);
            case "admin-categories" -> handleAdminCategoryChange(request);
        }

        processAccountPostRequest(request, response);

    }

    private void processAccountGetRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        var customer = getOrCreateCustomer(request);

        if(customer.getRoleId() == 3){
            Helper.addAttribute(request, AttributeEnum.HAS_ERROR, false);
            request.getRequestDispatcher(JspEnum.LOGIN.getJsp()).forward(request, response);
            return;
        }
        Helper.addAttributeIfNotExists(request, AttributeEnum.USER_DATA, customer);

        processDefaultAccountData(request, customer);

        if(customer.getRoleId() == 1){
            processAdminAccountData(request, customer);
        }

        request.getRequestDispatcher(JspEnum.ACCOUNT.getJsp()).forward(request, response);
    }

    /**
     * Processes administrator only data that is shown to only to admins
     * @param request Current Http request
     * @param customer Current customer
     */
    private void processAdminAccountData(HttpServletRequest request, CustomerEntity customer) {
        var customers = DbHelper.retrieveAllCustomers();
        Helper.addAttribute(request, AttributeEnum.USERS, customers);

        var carts = DbHelper.retrieveAllCarts(true);
        Helper.addAttribute(request, AttributeEnum.ALL_CARTS, carts);

        var items = DbHelper.retrieveItems(false);
        Helper.addAttribute(request, AttributeEnum.ITEMS, items);

        var categories = DbHelper.retrieveCategories();
        Helper.addAttribute(request, AttributeEnum.CATEGORIES, categories);
    }

    /**
     * Processes default data that is shown to every customer
     * @param request Current Http request
     * @param customer Current customer
     */
    private void processDefaultAccountData(HttpServletRequest request, CustomerEntity customer) {
        var addresses = DbHelper.retrieveAddresses(customer.getId());
        var defaultAddress = addresses
                .stream()
                .filter(AddressEntity::getDefault)
                .findFirst();
        Helper.addAttributeIfNotExists(request, AttributeEnum.DEFAULT_ADDRESS, defaultAddress.orElse(null));
        Helper.addAttributeIfNotExists(request, AttributeEnum.ADDRESSES, addresses);

        var carts = customer.getCarts().stream().filter(cartEntity -> !cartEntity.getCurrent()).toList();//DbHelper.retrieveCarts(customer.getId(), true);
        Helper.addAttributeIfNotExists(request, AttributeEnum.CARTS, carts);
    }


    private void processAccountPostRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var customer = getOrCreateCustomer(request);

        if(customer.getRoleId() == 3){
            Helper.addAttribute(request, AttributeEnum.HAS_ERROR, false);
            request.getRequestDispatcher(JspEnum.LOGIN.getJsp()).forward(request, response);
            return;
        }

        processDefaultAccountData(request, customer);

        if(customer.getRoleId() == 1){
            processAdminAccountData(request, customer);
        }

        request.setAttribute(AttributeEnum.USER_DATA.toString(), customer);
        request.getRequestDispatcher(JspEnum.ACCOUNT.getJsp()).forward(request, response);
    }


    private void handleAdminCategoryChange(HttpServletRequest request) {

    }

    private void handleAdminItemsChange(HttpServletRequest request) {

    }

    private void handleAdminCustomerChange(HttpServletRequest request) {

    }

    private void handleAddressChange(HttpServletRequest request) {
        var exists = request.getParameter(AttributeEnum.ID.toString()) != null;
        var address = new AddressEntity();
        var customer = getOrCreateCustomer(request);
        if(exists){
            var id = Integer.parseInt(request.getParameter(AttributeEnum.ID.toString()));
            address = DbHelper.retrieveAddressById(id);
        }
        else{
            address.setCustomer(customer);
        }
        var street = request.getParameter(AttributeEnum.STREET_NAME.toString());
        var streetNumber = request.getParameter(AttributeEnum.STREET_NUMBER.toString());
        var aptNumber = request.getParameter(AttributeEnum.APT_NUMBER.toString());
        var floorNumber = request.getParameter(AttributeEnum.FLOOR_NUMBER.toString());
        var zipCode = request.getParameter(AttributeEnum.ZIP_CODE.toString());
        var city = request.getParameter(AttributeEnum.CITY.toString());
        var county = request.getParameter(AttributeEnum.COUNTY.toString());
        var country = request.getParameter(AttributeEnum.COUNTRY.toString());

        address.setStreet(street);
        address.setStreetNumber(streetNumber);
        address.setApartmentNumber(aptNumber);
        address.setFloorNumber(floorNumber);
        address.setZipCode(zipCode);
        address.setCity(city);
        address.setCounty(county);
        address.setCountry(country);
        address.setDefault(false);

        if(exists){
            DbHelper.updateAddress(address);
        }
        else {
            DbHelper.createAddress(address);
        }
    }

    private void handleDefaultAddressChange(HttpServletRequest request) {
        var addressId = Integer.parseInt(request.getParameter(AttributeEnum.ADDRESS.toString()));
        var addresses = DbHelper.retrieveAddresses(getOrCreateCustomer(request).getId());
        for (var address:
             addresses) {
            if(address.getId() == addressId){
                address.setDefault(true);
                DbHelper.updateAddress(address);
            } else if (address.getDefault()){
                address.setDefault(false);
                DbHelper.updateAddress(address);
            }
        }
    }

    /**
     * Handles customer details change
     * @param request Incoming request
     */
    private void handleDetailsChange(HttpServletRequest request) {
        var customer = getOrCreateCustomer(request);
        var newFirstName = request.getParameter(AttributeEnum.FIRST_NAME.toString());
        var newLastName = request.getParameter(AttributeEnum.LAST_NAME.toString());
        var newDateOfBirth = Date.valueOf(request.getParameter(AttributeEnum.DATE_OF_BIRTH.toString()));

        customer.setFirstName(newFirstName);
        customer.setLastName(newLastName);
        customer.setDateOfBirth(newDateOfBirth);

        DbHelper.updateCustomer(customer);
    }

}
