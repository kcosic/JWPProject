package com.kcosic.jwp.shared.helpers;

import com.kcosic.jwp.shared.dal.Dal;
import com.kcosic.jwp.shared.exceptions.EntityNotFoundException;
import com.kcosic.jwp.shared.model.entities.CustomerEntity;

import java.util.List;

public class DbHelper {
    private DbHelper(){
    }

    public static List<CustomerEntity> retrieveAllCustomers(){
        return Dal.retrieveAll(CustomerEntity.class);
    }

    public static CustomerEntity retrieveCustomerByEmail(String email) throws EntityNotFoundException {
        var data = Dal.retrieveAll(CustomerEntity.class);
        var filteredUser = data.stream().filter((customer)-> customer.getEmail().equals(email)).findFirst();
        if(!filteredUser.isPresent()){
            throw new EntityNotFoundException("Customer with given email doesn't exist.");
        }
        return filteredUser.get();
    }

    public static CustomerEntity retrieveCustomerById(int id) throws EntityNotFoundException {
        var data = Dal.retrieveById(CustomerEntity.class, id);
        if(data == null){
            throw new EntityNotFoundException("Customer with given ID doesn't exist.");
        }
        return data;
    }

    public static CustomerEntity createCustomer(CustomerEntity customer){
        /*EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.persist(customer);
        entityManager.getTransaction().commit();
        entityManager.*/
        return null;
    }

}
