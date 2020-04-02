package component;

import java.util.Objects;

public class Address
{
    private String streetName;
    private int houseNo;
    private int zipCode;


    public Address(String streetName, int houseNo, int zipCode) {
        this.streetName = streetName;
        this.houseNo = houseNo;
        this.zipCode = zipCode;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public int getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(int houseNo) {
        this.houseNo = houseNo;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o) return true;
        if(!(o instanceof Address)) return false;
        Address address = (Address)o;
        return getHouseNo() == address.getHouseNo() &&
               getZipCode() == address.getZipCode() &&
               getStreetName().equals(address.getStreetName());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getStreetName(), getHouseNo(), getZipCode());
    }
}

