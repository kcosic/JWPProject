package com.kcosic.jwp.servlets;

import com.kcosic.jwp.shared.enums.AttributeEnum;
import com.kcosic.jwp.shared.enums.JspEnum;
import com.kcosic.jwp.shared.helpers.DbHelper;
import com.kcosic.jwp.shared.helpers.Helper;
import com.kcosic.jwp.shared.model.BaseServlet;
import com.kcosic.jwp.shared.model.entities.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.time.Instant;
import java.util.List;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 5 * 5,
        location = "assets\\images")
@WebServlet(name = "AccountServlet", value = "/account")
public class AccountServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processAccountGetRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var type = request.getParameter(AttributeEnum.TYPE.toString());
        var action = request.getParameter(AttributeEnum.ACTION.toString());
        switch (type) {
            case "details" -> handleDetailsChange(request, action);
            case "default-address" -> handleDefaultAddressChange(request, action);
            case "address" -> handleAddressChange(request, action);
            case "admin-customers" -> handleAdminCustomerChange(request, action);
            case "admin-history" -> handleAdminHistoryChange(request, action);
            case "admin-items" -> handleAdminItemsChange(request, action);
            case "admin-categories" -> handleAdminCategoryChange(request, action);
            case "admin-logs" -> handleAdminLogsChange(request, action);
        }

        processAccountPostRequest(request, response);

    }

    private void processAccountGetRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        var customer = getOrCreateCustomer(request);

        if (customer.getRole().getId() == 3) {
            Helper.addAttribute(request, AttributeEnum.HAS_ERROR, false);
            request.getRequestDispatcher(JspEnum.LOGIN.getJsp()).forward(request, response);
            return;
        }
        Helper.addAttributeIfNotExists(request, AttributeEnum.CUSTOMER_DATA, customer);

        processDefaultAccountData(request, customer);

        if (customer.getRole().getId() == 1) {
            processAdminAccountData(request, customer);
        }

        request.getRequestDispatcher(JspEnum.ACCOUNT.getJsp()).forward(request, response);
    }

    /**
     * Processes administrator only data that is shown only to admins
     *
     * @param request  Current Http request
     * @param customer Current customer
     */
    private void processAdminAccountData(HttpServletRequest request, CustomerEntity customer) {
        var customers = DbHelper.retrieveCustomers().stream().filter(customerEntity -> customerEntity.getRole().getId() != 3).toList();
        Helper.addAttribute(request, AttributeEnum.CUSTOMERS, customers);

        if(request.getParameter(AttributeEnum.HISTORY_SEARCH_QUERY.toString()) == null) {
            var carts = DbHelper.retrieveAllCarts(true);
            Helper.addAttribute(request, AttributeEnum.ALL_CARTS, carts);
        }

        if(request.getParameter(AttributeEnum.ITEM_SEARCH_QUERY.toString()) == null){
            var items = DbHelper.retrieveItems(false);
            Helper.addAttribute(request, AttributeEnum.ITEMS, items);
        }

        if(request.getParameter(AttributeEnum.CATEGORY_SEARCH_QUERY.toString()) == null) {
            var categories = DbHelper.retrieveCategories();
            Helper.addAttribute(request, AttributeEnum.CATEGORIES, categories);
        }

        var roles = DbHelper.retrieveRoles();
        Helper.addAttribute(request, AttributeEnum.ROLES, roles);

        if(request.getParameter(AttributeEnum.LOG_SEARCH_QUERY.toString()) == null) {
            var logs = DbHelper.retrieveLogs();
            Helper.addAttribute(request, AttributeEnum.LOGS, logs);
        }
    }

    /**
     * Processes default data that is shown to every customer
     *
     * @param request  Current Http request
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

        if (customer.getRoleId() == 3) {
            Helper.addAttribute(request, AttributeEnum.HAS_ERROR, false);
            request.getRequestDispatcher(JspEnum.LOGIN.getJsp()).forward(request, response);
            return;
        }

        processDefaultAccountData(request, customer);

        if (customer.getRoleId() == 1) {
            processAdminAccountData(request, customer);
        }

        request.setAttribute(AttributeEnum.CUSTOMER_DATA.toString(), customer);
        request.getRequestDispatcher(JspEnum.ACCOUNT.getJsp()).forward(request, response);
    }


    private void handleAdminHistoryChange(HttpServletRequest request, String action) {
        var historyQuery = request.getParameter(AttributeEnum.HISTORY_SEARCH_QUERY.toString());
        List<CartEntity> carts;
        if (!Helper.isNullOrEmpty(historyQuery)) {
            carts = DbHelper.retrieveAllCarts(historyQuery, true);
        } else {
            carts = DbHelper.retrieveAllCarts(true);
        }
        Helper.addAttribute(request, AttributeEnum.HISTORY_SEARCH_QUERY, historyQuery != null ? historyQuery : "");
        Helper.addAttribute(request, AttributeEnum.ALL_CARTS, carts);

        Helper.addAttribute(request, AttributeEnum.PASSED_ADMIN_VIEW, "admin-history");
        Helper.addAttribute(request, AttributeEnum.PASSED_VIEW, "admin");
    }

    private void handleAdminLogsChange(HttpServletRequest request, String action) {
        var logQuery = request.getParameter(AttributeEnum.LOG_SEARCH_QUERY.toString());
        List<LogEntity> logs;
        if (!Helper.isNullOrEmpty(logQuery)) {
            logs = DbHelper.retrieveLogs(logQuery);
        } else {
            logs = DbHelper.retrieveLogs();
        }
        Helper.addAttribute(request, AttributeEnum.LOG_SEARCH_QUERY, logQuery != null ? logQuery : "");
        Helper.addAttribute(request, AttributeEnum.LOGS, logs);

        Helper.addAttribute(request, AttributeEnum.PASSED_ADMIN_VIEW, "admin-logs");
        Helper.addAttribute(request, AttributeEnum.PASSED_VIEW, "admin");

    }


    /**
     * Handles changes on categories by admin
     *
     * @param request
     * @param action
     */
    private void handleAdminCategoryChange(HttpServletRequest request, String action) {
        var categoryQuery = request.getParameter(AttributeEnum.CATEGORY_SEARCH_QUERY.toString());
        List<CategoryEntity> categories;
        if (!Helper.isNullOrEmpty(categoryQuery)) {
            categories = DbHelper.retrieveCategories(categoryQuery);
        } else {
            categories = DbHelper.retrieveCategories();
        }
        Helper.addAttribute(request, AttributeEnum.CATEGORY_SEARCH_QUERY, categoryQuery != null ? categoryQuery : "");
        Helper.addAttribute(request, AttributeEnum.CATEGORIES, categories);

        if(!action.equals("search")){

            var exists = request.getParameter(AttributeEnum.ID.toString()) != null;
            var category = new CategoryEntity();
            if (exists) {
                var id = Integer.parseInt(request.getParameter(AttributeEnum.ID.toString()));
                category = DbHelper.retrieveCategory(id);
            }

            var name = request.getParameter(AttributeEnum.CATEGORY_NAME.toString());

            if (!Helper.isNullOrEmpty(name)) {
                category.setName(name);
            }

            if (exists) {
                DbHelper.updateCategory(category);
            } else {
                DbHelper.createCategory(category);
            }

        }
        Helper.addAttribute(request, AttributeEnum.PASSED_ADMIN_VIEW, "admin-categories");
        Helper.addAttribute(request, AttributeEnum.PASSED_VIEW, "admin");
    }

    /**
     * Handles changes on items by admin
     *
     * @param request
     * @param action
     * @throws ServletException
     * @throws IOException
     */
    private void handleAdminItemsChange(HttpServletRequest request, String action) throws ServletException, IOException {
        var itemQuery = request.getParameter(AttributeEnum.ITEM_SEARCH_QUERY.toString());
        List<ItemEntity> items;
        if (!Helper.isNullOrEmpty(itemQuery)) {
            items = DbHelper.retrieveItems(itemQuery, false);
        } else {
            items = DbHelper.retrieveItems(false);
        }
        Helper.addAttribute(request, AttributeEnum.ITEM_SEARCH_QUERY, itemQuery != null ? itemQuery : "");
        Helper.addAttribute(request, AttributeEnum.ITEMS, items);

        if(!action.equals("search")){
            var exists = request.getParameter(AttributeEnum.ID.toString()) != null;
            var item = new ItemEntity();
            if (exists) {
                var id = Integer.parseInt(request.getParameter(AttributeEnum.ID.toString()));
                item = DbHelper.retrieveItem(id);
            }

            if (request.getContentType().contains("multipart")) {

                var optPart = request.getParts().stream().filter(part1 -> part1.getSubmittedFileName() != null && !part1.getSubmittedFileName().isEmpty()).findFirst();

                if (optPart.isPresent()) {
                    var part = optPart.get();
                    try {
                        var name = Helper.hash(part.getSubmittedFileName() + Date.from(Instant.now())) + part.getSubmittedFileName().substring(part.getSubmittedFileName().lastIndexOf('.'));

                        var path = Helper.getUploadPath(getServletContext()) +
                                File.separator + name;
                        var file = new File(path);
                        //noinspection ResultOfMethodCallIgnored
                        file.createNewFile();
                        Files.copy(part.getInputStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);

                        item.setImage(name);
                    } catch (NoSuchAlgorithmException e) {
                        //TODO proper error page
                        e.printStackTrace();
                    }
                }
            }

            var name = request.getParameter(AttributeEnum.ITEM_NAME.toString());
            var manufacturer = request.getParameter(AttributeEnum.MANUFACTURER.toString());
            var description = request.getParameter(AttributeEnum.DESCRIPTION.toString());
            var price = BigDecimal.valueOf(Double.parseDouble(request.getParameter(AttributeEnum.ITEM_PRICE.toString())));
            var categoryId = Integer.parseInt(request.getParameter(AttributeEnum.CATEGORY.toString()));


            var active = request.getParameter(AttributeEnum.ACTIVE.toString());

            item.setIsActive(active != null && active.equals("true"));

            if (!Helper.isNullOrEmpty(name)) {
                item.setName(name);
            }
            if (!Helper.isNullOrEmpty(manufacturer)) {
                item.setManufacturer(manufacturer);
            }
            if (!Helper.isNullOrEmpty(description)) {
                item.setDescription(description);
            }
            item.setPrice(price);


            item.setCategory(DbHelper.retrieveCategory(categoryId));


            if (exists) {
                DbHelper.updateItem(item);
            } else {
                DbHelper.createItem(item);
            }

        }

        Helper.addAttribute(request, AttributeEnum.PASSED_ADMIN_VIEW, "admin-items");
        Helper.addAttribute(request, AttributeEnum.PASSED_VIEW, "admin");
    }

    /**
     * Handles changes on customers by admin
     *
     * @param request
     * @param action
     * @throws ServletException
     * @throws IOException
     */
    private void handleAdminCustomerChange(HttpServletRequest request, String action) throws ServletException, IOException {
        var customerQuery = request.getParameter(AttributeEnum.CUSTOMER_SEARCH_QUERY.toString());
        List<CustomerEntity> customers;
        if (!Helper.isNullOrEmpty(customerQuery)) {
            customers = DbHelper.retrieveCustomers(customerQuery);
        } else {
            customers = DbHelper.retrieveCustomers();
        }
        Helper.addAttribute(request, AttributeEnum.CUSTOMER_SEARCH_QUERY, customerQuery != null ? customerQuery : "");
        Helper.addAttribute(request, AttributeEnum.CUSTOMERS, customers);

        if(!action.equals("search")){
            var exists = request.getParameter(AttributeEnum.ID.toString()) != null;
            var customer = new CustomerEntity();
            if (exists) {
                var id = Integer.parseInt(request.getParameter(AttributeEnum.ID.toString()));
                customer = DbHelper.retrieveCustomerById(id);
            }

            var roleId = Integer.parseInt(request.getParameter(AttributeEnum.ROLE_ID.toString()));

            customer.setRole(DbHelper.retrieveRole(roleId));

            if (exists) {
                DbHelper.updateCustomer(customer);
            } else {
                DbHelper.createCustomer(customer);
            }
        }


        Helper.addAttribute(request, AttributeEnum.PASSED_ADMIN_VIEW, "admin-customers");
        Helper.addAttribute(request, AttributeEnum.PASSED_VIEW, "admin");
    }

    /**
     * Handles create and update of the customer addresses
     *
     * @param request
     * @param action
     */
    private void handleAddressChange(HttpServletRequest request, String action) {
        var exists = request.getParameter(AttributeEnum.ID.toString()) != null;
        var address = new AddressEntity();
        var customer = getOrCreateCustomer(request);
        if (exists) {
            var id = Integer.parseInt(request.getParameter(AttributeEnum.ID.toString()));
            address = DbHelper.retrieveAddressById(id);
        } else {
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

        if (exists) {
            DbHelper.updateAddress(address);
        } else {
            DbHelper.createAddress(address);
        }
        Helper.removeAttribute(request, AttributeEnum.PASSED_ADMIN_VIEW);
        Helper.addAttribute(request, AttributeEnum.PASSED_VIEW, "address");
    }

    /**
     * Handles customer default address change
     *
     * @param request
     * @param action
     */
    private void handleDefaultAddressChange(HttpServletRequest request, String action) {
        var addressId = Integer.parseInt(request.getParameter(AttributeEnum.ADDRESS.toString()));
        var addresses = DbHelper.retrieveAddresses(getOrCreateCustomer(request).getId());
        for (var address :
                addresses) {
            if (address.getId() == addressId) {
                address.setDefault(true);
                DbHelper.updateAddress(address);
            } else if (address.getDefault()) {
                address.setDefault(false);
                DbHelper.updateAddress(address);
            }
        }
        Helper.removeAttribute(request, AttributeEnum.PASSED_ADMIN_VIEW);
        Helper.addAttribute(request, AttributeEnum.PASSED_VIEW, "address");
    }

    /**
     * Handles customer details change
     *
     * @param request Incoming request
     * @param action
     */
    private void handleDetailsChange(HttpServletRequest request, String action) {
        var customer = getOrCreateCustomer(request);
        var newFirstName = request.getParameter(AttributeEnum.FIRST_NAME.toString());
        var newLastName = request.getParameter(AttributeEnum.LAST_NAME.toString());
        var newDateOfBirth = Date.valueOf(request.getParameter(AttributeEnum.DATE_OF_BIRTH.toString()));

        customer.setFirstName(newFirstName);
        customer.setLastName(newLastName);
        customer.setDateOfBirth(newDateOfBirth);

        DbHelper.updateCustomer(customer);
        Helper.removeAttribute(request, AttributeEnum.PASSED_ADMIN_VIEW);
        Helper.addAttribute(request, AttributeEnum.PASSED_VIEW, "details");
    }

}
