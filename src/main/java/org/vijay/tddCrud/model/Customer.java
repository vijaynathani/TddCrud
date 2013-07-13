package org.vijay.tddCrud.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/* create table customer (id int generated always as Identity, 
 * number int not null, name varchar(20) not null, address varchar(60) 
 * not null,  mobile varchar(12) not null,primary key(id)); //Derby SQL syntax
 * create unique index customerNumber on Customer (number); 
 */
@Entity
@Table(name="CUSTOMER")
public class Customer implements Serializable {

	public static final String findByNumber = "from Customer c where c.number = :number";
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id = 0L;
	private Long number = 0L;
	private String name = "", address = "", mobile = "";

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long id) {
		this.number = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void copyFields(Customer c) {
		setNumber(c.getNumber());
		setName(c.getName());
		setAddress(c.getAddress());
		setMobile(c.getMobile());
	}

	public Customer() {
	}

	public Customer(Long number, String name, String address,
	        String mobile) {
		this.number = number;
		this.name = name;
		this.address = address;
		this.mobile = mobile;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		final Customer other = (Customer) obj;

		if ((this.number != other.number)
		        && ((this.number == null) || !this.number
		                .equals(other.number))) return false;
		if ((this.name != other.name)
		        && ((this.name == null) || !this.name
		                .equals(other.name))) return false;
		if ((this.address != other.address)
		        && ((this.address == null) || !this.address
		                .equals(other.address))) return false;
		if ((this.mobile != other.mobile)
		        && ((this.mobile == null) || !this.mobile
		                .equals(other.mobile))) return false;
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 3;

		hash = 23 * hash
		        + (this.number != null ? this.number.hashCode() : 0);
		hash = 23 * hash
		        + (this.name != null ? this.name.hashCode() : 0);
		hash = 23
		        * hash
		        + (this.address != null ? this.address.hashCode() : 0);
		hash = 23 * hash
		        + (this.mobile != null ? this.mobile.hashCode() : 0);
		return hash;
	}

	public static final int numberScreenLength = 9,
	        nameScreenLength = 20, addressScreenRows = 3,
	        addressScreenCols = 20, addressLength = 60,
	        mobileScreenLength = 12, idLength = 9;

	public static final String numberDescription = "Number",
	        nameDescription = "Name", addressDescription = "Address",
	        mobileDescription = "Mobile";
	public static final String numberField = "number",
	        nameField = "name", addressField = "address",
	        mobileField = "mobile";

	public String getNumberDescription() {
		return numberDescription;
	}

	public int getNumberScreenLength() {
		return numberScreenLength;
	}

	public String getNumberField() {
		return numberField;
	}

	public String getNameDescription() {
		return nameDescription;
	}

	public String getNameField() {
		return nameField;
	}

	public int getNameScreenLength() {
		return nameScreenLength;
	}

	public String getAddressDescription() {
		return addressDescription;
	}

	public String getAddressField() {
		return addressField;
	}

	public int getAddressScreenRows() {
		return addressScreenRows;
	}

	public int getAddressScreenCols() {
		return addressScreenCols;
	}

	public int getAddressLength() {
		return addressLength;
	}

	public String getMobileDescription() {
		return mobileDescription;
	}

	public int getMobileScreenLength() {
		return mobileScreenLength;
	}

	public String getMobileField() {
		return mobileField;
	}
}
