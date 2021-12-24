package com.kcosic.jwp.shared.helpers;

import com.kcosic.jwp.shared.dal.Dal;
import com.kcosic.jwp.shared.exceptions.EntityNotFoundException;
import com.kcosic.jwp.shared.model.entities.CustomerEntity;

import java.util.List;

public class DbHelper {
    private DbHelper(){
    }

    public static List<CustomerEntity> retrieveAllCustomers(){
        Dal dal = new Dal();
        return dal.retrieveAll(CustomerEntity.class);
    }

    public static CustomerEntity retrieveCustomerByEmail(String email) throws EntityNotFoundException {
        Dal dal = new Dal();

        var data = dal.retrieveAll(CustomerEntity.class);
        var filteredUser = data.stream().filter((customer)-> customer.getEmail().equals(email)).findFirst();
        if(!filteredUser.isPresent()){
            throw new EntityNotFoundException("Customer with given email doesn't exist.");
        }
        return filteredUser.get();
    }

    public static CustomerEntity retrieveCustomerById(int id) throws EntityNotFoundException {
        Dal dal = new Dal();
        var data = dal.retrieveById(CustomerEntity.class, id);
        if(data == null){
            throw new EntityNotFoundException("Customer with given ID doesn't exist.");
        }
        return data;
    }

    public static CustomerEntity createCustomer(CustomerEntity customer){
        var dal = new Dal();
        return dal.create(CustomerEntity.class, customer);
    }

    public static boolean doesEmailExist(String email) {
        Dal dal = new Dal();
        var data = dal.retrieveAll(CustomerEntity.class);
        for (var customer : data) {
            if(customer.getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }
}
