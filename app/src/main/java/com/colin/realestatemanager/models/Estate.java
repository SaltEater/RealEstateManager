package com.colin.realestatemanager.models;

import android.content.ContentValues;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Random;


@Entity(tableName = "estate_table")
public class Estate implements Serializable {

    @PrimaryKey
    @ColumnInfo(name = "estate_id")
    private long estateId;

    @ColumnInfo(name = "real_estate_agent")
    private String realEstateAgent;
    private boolean status;
    @ColumnInfo(name = "begin_date")
    private long beginDate;
    @ColumnInfo(name = "end_date")
    private long endDate;


    private String description;
    private String type;
    private int price;

    private String address;
    private String complement;
    @ColumnInfo(name = "postal_code")
    private String postalCode;
    private String state;
    private String city;

    private int surface;
    @ColumnInfo(name = "rooms_number")
    private int nbRooms;
    @ColumnInfo(name = "bedrooms_number")
    private int nbBedrooms;
    @ColumnInfo(name = "bathrooms_number")
    private int nbBathrooms;

    private boolean school;
    @ColumnInfo(name = "swimming_pool")
    private boolean swimmingPool;
    private boolean shop;
    private boolean library;
    private boolean park;
    private boolean restaurant;

    public Estate() {

    }

    public Estate(String realEstateAgent, boolean status, long beginDate, long endDate, String description, String type, int price, String address, String complement, String postalCode,
                  String city, String state, int surface, int nbRooms, int nbBedrooms, int nbBathrooms, boolean school,
                  boolean swimmingPool, boolean shop, boolean library, boolean park, boolean restaurant) {
        estateId = new Random().nextLong();
        this.realEstateAgent = realEstateAgent;
        this.status = status;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.description = description;
        this.type = type;
        this.price = price;
        this.address = address;
        this.complement = complement;
        this.postalCode = postalCode;
        this.city = city;
        this.state = state;
        this.surface = surface;
        this.nbRooms = nbRooms;
        this.nbBedrooms = nbBedrooms;
        this.nbBathrooms = nbBathrooms;
        this.school = school;
        this.swimmingPool = swimmingPool;
        this.shop = shop;
        this.library = library;
        this.park = park;
        this.restaurant = restaurant;
    }

    public long getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(long beginDate) {
        this.beginDate = beginDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public String getRealEstateAgent() {
        return realEstateAgent;
    }

    public void setRealEstateAgent(String realEstateAgent) {
        this.realEstateAgent = realEstateAgent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getSurface() {
        return surface;
    }

    public void setSurface(int surface) {
        this.surface = surface;
    }

    public int getNbRooms() {
        return nbRooms;
    }

    public void setNbRooms(int nbRooms) {
        this.nbRooms = nbRooms;
    }

    public int getNbBedrooms() {
        return nbBedrooms;
    }

    public void setNbBedrooms(int nbBedrooms) {
        this.nbBedrooms = nbBedrooms;
    }

    public int getNbBathrooms() {
        return nbBathrooms;
    }

    public void setNbBathrooms(int nbBathrooms) {
        this.nbBathrooms = nbBathrooms;
    }

    public void setEstateId(long estateId) {
        this.estateId = estateId;
    }

    public long getEstateId() {
        return estateId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    public boolean isSchool() {
        return school;
    }

    public void setSchool(boolean school) {
        this.school = school;
    }

    public boolean isSwimmingPool() {
        return swimmingPool;
    }

    public void setSwimmingPool(boolean swimmingPool) {
        this.swimmingPool = swimmingPool;
    }

    public boolean isShop() {
        return shop;
    }

    public void setShop(boolean shop) {
        this.shop = shop;
    }

    public boolean isLibrary() {
        return library;
    }

    public void setLibrary(boolean library) {
        this.library = library;
    }

    public boolean isPark() {
        return park;
    }

    public void setPark(boolean park) {
        this.park = park;
    }

    public boolean isRestaurant() {
        return restaurant;
    }

    public void setRestaurant(boolean restaurant) {
        this.restaurant = restaurant;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    // UTILS

    public static Estate fromContentValues(ContentValues values) {
        final Estate estate = new Estate();
        if (values.containsKey("description")) estate.setDescription(values.getAsString("description"));
        if (values.containsKey("type")) estate.setType(values.getAsString("type"));
        if (values.containsKey("price")) estate.setPrice(values.getAsInteger("price"));
        if (values.containsKey("address")) estate.setAddress(values.getAsString("address"));
        if (values.containsKey("complement")) estate.setComplement(values.getAsString("complement"));
        if (values.containsKey("postalCode")) estate.setPostalCode(values.getAsString("postalCode"));
        if (values.containsKey("city")) estate.setCity(values.getAsString("city"));
        if (values.containsKey("state")) estate.setState(values.getAsString("state"));
        if (values.containsKey("surface")) estate.setSurface(values.getAsInteger("surface"));
        if (values.containsKey("nbRooms")) estate.setNbRooms(values.getAsInteger("nbRooms"));
        if (values.containsKey("nbBedrooms")) estate.setNbBedrooms(values.getAsInteger("nbBedrooms"));
        if (values.containsKey("nbBathrooms")) estate.setNbBathrooms(values.getAsInteger("nbBathrooms"));
        if (values.containsKey("school")) estate.setSchool(values.getAsBoolean("school"));
        if (values.containsKey("swimmingPool")) estate.setSwimmingPool(values.getAsBoolean("swimmingPool"));
        if (values.containsKey("shop")) estate.setShop(values.getAsBoolean("shop"));
        if (values.containsKey("library")) estate.setLibrary(values.getAsBoolean("library"));
        if (values.containsKey("park")) estate.setPark(values.getAsBoolean("park"));
        if (values.containsKey("restaurant")) estate.setRestaurant(values.getAsBoolean("restaurant"));
        estate.setEstateId(new Random().nextLong());
        return estate;

    }
}
