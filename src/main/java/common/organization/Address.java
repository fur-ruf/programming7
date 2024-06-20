package common.organization;

import java.io.Serializable;

public class Address implements Serializable {
    private String street; //Поле может быть null
    private String zipCode; //Поле не может быть null, длина <= 18

    public Address(String street, String zipCode) {
        this.street = street;
        this.zipCode = zipCode;
    }

    public boolean isValid() {
        if (street == null | zipCode == null) {
            return false;
        }
        return true;
    }

    public String getStreet() {
        return street;
    }

    public String getZipCode() {
        return zipCode;
    }
}
