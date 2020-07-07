package com.financeiro.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Address {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String streetAddress;
    private String number;
    private String complement;
    private String province;
    private String zipCode;
    private String latitude;
    private String longitude;

    @ManyToOne()
    @JoinColumn(name = "city_id")
    private City city;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "person_id")
    private Person person;

    public Address() {}

    public Address(Long id, String streetAddress, String number, String complement, String province, String zipCode, String latitude, String longitude, City city, Person person) {
        this.id = id;
        this.streetAddress = streetAddress;
        this.number = number;
        this.complement = complement;
        this.province = province;
        this.zipCode = zipCode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
        this.person = person;
    }

    public Long getId() { return this.id; }
    public void setId(Long id) { this.id = id; }

    public String getStreetAddress() { return this.streetAddress; }
    public void setStreetAddress(String streetAddress) { this.streetAddress = streetAddress; }

    public String getNumber() { return this.number; }
    public void setNumber(String number) { this.number = number; }

    public String getComplement() { return this.complement; } 
    public void setComplement(String complement) { this.complement = complement; }

    public String getProvince() { return this.province; }
    public void setProvince(String province) { this.province = province; }

    public String getZipCode() { return this.zipCode; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }

    public String getLatitude() { return this.latitude; }
    public void setLatitude(String latitude) { this.latitude = latitude; }

    public String getLongitude() { return this.longitude; }
    public void setLongitude(String longitude) { this.longitude = longitude; }

    public City getCity() { return this.city; }
    public void setCity(City city) { this.city = city; }

    public Person getPerson() { return this.person; }
    public void setPerson(Person person) { this.person = person; }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Address)) {
            return false;
        }
        Address address = (Address) o;
        return Objects.equals(id, address.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}