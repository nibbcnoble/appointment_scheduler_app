package main;

import javafx.beans.property.SimpleStringProperty;

import java.sql.SQLException;

/**
 * Created by Jeff Noble on 5/9/2017.
 */
public class Customer {
    private SimpleStringProperty  customerName;

    private SimpleStringProperty  phone_area;
    private SimpleStringProperty  phone;
    private SimpleStringProperty  address;
    private SimpleStringProperty  address2;
    private SimpleStringProperty  postalCode;
    private SimpleStringProperty  city;
    private SimpleStringProperty  country;
    private int customer_id = 0;


    public String getFirst_name() {
        return first_name.get();
    }

    public void setFirst_name(String first_name) {
        this.first_name.set(first_name);
        customerConnector.updateCustomer(this.getCustomer_id(), "firstName", first_name);
    }

    public String getLast_name() {
        return last_name.get();
    }

    public void setLast_name(String last_name) {
        this.last_name.set(last_name);
        customerConnector.updateCustomer(this.getCustomer_id(), "lastName", last_name);
    }

    public String getPhone_area() {
        return phone_area.get();
    }

    public void setPhone_area(String phone_area) {
        this.phone_area.set(phone_area);
        customerConnector.updateCustomer(this.getCustomer_id(), "phoneArea", phone_area);
    }

    public String getPhone_prefix() {
        return phone_prefix.get();
    }

    public void setPhone_prefix(String phone_prefix) {
        this.phone_prefix.set(phone_prefix);
        customerConnector.updateCustomer(this.getCustomer_id(), "phonePrefix", phone_prefix);
    }

    public String getPhone_phone() {
        return phone_phone.get();
    }

    public void setPhone_phone(String phone_phone) {
        this.phone_phone.set(phone_phone);
        customerConnector.updateCustomer(this.getCustomer_id(), "phoneLine", phone_phone);
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
        customerConnector.updateCustomer(this.getCustomer_id(), "address", address);
    }

    public boolean saveAll() {
        try {

            this.setCustomer_id(customerConnector.addCustomer(this));
        } catch (ClassNotFoundException | SQLException e) {

        }

        return true;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public void delete_self() {
        customerConnector.delete_customer(this.customer_id);
    }
    @Override
    public String toString() {
        return this.getLast_name()+", "+this.getFirst_name();
    }
}
