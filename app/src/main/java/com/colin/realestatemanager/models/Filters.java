package com.colin.realestatemanager.models;

import com.colin.realestatemanager.utils.Utils;

public class Filters {

    private String realEstateAgent;
    private String type;
    private String postalCode;
    private String state ;
    private String city;

    private Long entryBeginDate;
    private Long entryEndDate;
    private Long soldBeginDate;
    private Long soldEndDate;

    private Integer minPrice;
    private Integer maxPrice;
    private Integer minSurface;
    private Integer maxSurface;
    private Integer minNbRooms;
    private Integer maxNbRooms;
    private Integer minNbBedrooms;
    private Integer maxNbBedrooms;
    private Integer minNbBathrooms;
    private Integer maxNbBathrooms;

    private Boolean soldStateAccepted;
    private Boolean onSaleStateAccepted;
    private Boolean school;
    private Boolean swimmingPool;
    private Boolean shop ;
    private Boolean library;
    private Boolean park ;
    private Boolean restaurant;

    // DEFAULT FILTERS
    public Filters() {
        realEstateAgent = "%%";
        type = "%%";
        postalCode = "%%";
        state  = "%%";
        city = "%%";

        entryBeginDate = Long.MIN_VALUE;
        entryEndDate = Long.MAX_VALUE;
        soldBeginDate = Long.MIN_VALUE;
        soldEndDate = Long.MAX_VALUE;

        minPrice = Integer.MIN_VALUE;
        maxPrice = Integer.MAX_VALUE;
        minSurface = Integer.MIN_VALUE;
        maxSurface = Integer.MAX_VALUE;
        minNbRooms = Integer.MIN_VALUE;
        maxNbRooms = Integer.MAX_VALUE;
        minNbBedrooms = Integer.MIN_VALUE;
        maxNbBedrooms = Integer.MAX_VALUE;
        minNbBathrooms = Integer.MIN_VALUE;
        maxNbBathrooms = Integer.MAX_VALUE;

        soldStateAccepted = true;
        onSaleStateAccepted = true;
        school = false;
        swimmingPool = false;
        shop  = false;
        library = false;
        park  = false;
        restaurant = false;
    }

    public Filters(String realEstateAgent, String type, String postalCode, String state, String city, Long entryBeginDate, Long entryEndDate, Long soldBeginDate, Long soldEndDate, Integer minPrice, Integer maxPrice, Integer minSurface, Integer maxSurface, Integer minNbRooms, Integer maxNbRooms, Integer minNbBedrooms, Integer maxNbBedrooms, Integer minNbBathrooms, Integer maxNbBathrooms, Boolean soldStateAccepted, Boolean onSaleStateAccepted, Boolean school, Boolean swimmingPool, Boolean shop, Boolean library, Boolean park, Boolean restaurant) {
        this.realEstateAgent = realEstateAgent;
        this.type = type;
        this.postalCode = postalCode;
        this.state = state;
        this.city = city;
        this.entryBeginDate = entryBeginDate;
        this.entryEndDate = entryEndDate;
        this.soldBeginDate = soldBeginDate;
        this.soldEndDate = soldEndDate;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.minSurface = minSurface;
        this.maxSurface = maxSurface;
        this.minNbRooms = minNbRooms;
        this.maxNbRooms = maxNbRooms;
        this.minNbBedrooms = minNbBedrooms;
        this.maxNbBedrooms = maxNbBedrooms;
        this.minNbBathrooms = minNbBathrooms;
        this.maxNbBathrooms = maxNbBathrooms;
        this.soldStateAccepted = soldStateAccepted;
        this.onSaleStateAccepted = onSaleStateAccepted;
        this.school = school;
        this.swimmingPool = swimmingPool;
        this.shop = shop;
        this.library = library;
        this.park = park;
        this.restaurant = restaurant;
    }

    // CREATE FILTERS FROM UI

    public Filters(String realEstateAgent, String type, String postalCode, String state, String city, String entryBeginDate, String entryEndDate, String soldBeginDate, String soldEndDate, String minPrice, String maxPrice, String minSurface, String maxSurface, String minNbRooms, String maxNbRooms, String minNbBedrooms, String maxNbBedrooms, String minNbBathrooms, String maxNbBathrooms, Boolean soldStateAccepted, Boolean onSaleStateAccepted, Boolean school, Boolean swimmingPool, Boolean shop, Boolean library, Boolean park, Boolean restaurant) {
        this.realEstateAgent = setStringText(realEstateAgent);
        this.type = setStringText(type);
        this.postalCode = setStringText(postalCode);
        this.state = setStringText(state);
        this.city = setStringText(city);
        this.entryBeginDate = setMinDateText(entryBeginDate);
        this.entryEndDate = setMaxDateText(entryEndDate);
        this.soldBeginDate = setMinDateText(soldBeginDate);
        this.soldEndDate = setMaxDateText(soldEndDate);
        this.minPrice = setMinIntegerText(minPrice);
        this.maxPrice = setMaxIntegerText(maxPrice);
        this.minSurface = setMinIntegerText(minSurface);
        this.maxSurface = setMaxIntegerText(maxSurface);
        this.minNbRooms = setMinIntegerText(minNbRooms);
        this.maxNbRooms = setMaxIntegerText(maxNbRooms);
        this.minNbBedrooms = setMinIntegerText(minNbBedrooms);
        this.maxNbBedrooms = setMaxIntegerText(maxNbBedrooms);
        this.minNbBathrooms = setMinIntegerText(minNbBathrooms);
        this.maxNbBathrooms = setMaxIntegerText(maxNbBathrooms);
        this.soldStateAccepted = soldStateAccepted;
        this.onSaleStateAccepted = onSaleStateAccepted;
        this.school = school;
        this.swimmingPool = swimmingPool;
        this.shop = shop;
        this.library = library;
        this.park = park;
        this.restaurant = restaurant;
    }

    // ===============================================
    //              UI PURPOSES
    // ===============================================

    public static String getStringText(String s) {
        return (s == null || s.equals("%%")) ? "" : s;
    }

    public static String getDateText(Long l) {
        return (l == null || l == Long.MAX_VALUE || l == Long.MIN_VALUE) ? "" : Utils.timestampToString(l);
    }

    public static String getIntegerText(Integer i) {
        return (i == null || i == Integer.MAX_VALUE || i == Integer.MIN_VALUE) ? "" : Utils.castIntToString(i);
    }

    public static String setStringText(String text) {
        return (text == null || text.equals("")) ? "%%" : text;
    }

    public static Long setMinDateText(String text) {
        return (text == null || text.equals("")) ? Long.MIN_VALUE : Utils.stringToDate(text).getTime();
    }

    public static Long setMaxDateText(String text) {
        return (text == null || text.equals("")) ? Long.MAX_VALUE : Utils.stringToDate(text).getTime();
    }

    public static Integer setMinIntegerText(String text) {
        return (text == null || text.equals("")) ? Integer.MIN_VALUE : Integer.parseInt(text);
    }

    public static Integer setMaxIntegerText(String text) {
        return (text == null || text.equals("")) ? Integer.MAX_VALUE : Integer.parseInt(text);
    }


    // ================================================
    //              GETTERS AND SETTERS
    // ================================================


    public String getRealEstateAgent() {
        return realEstateAgent;
    }

    public void setRealEstateAgent(String realEstateAgent) {
        this.realEstateAgent = realEstateAgent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getEntryBeginDate() {
        return entryBeginDate;
    }

    public void setEntryBeginDate(Long entryBeginDate) {
        this.entryBeginDate = entryBeginDate;
    }

    public Long getEntryEndDate() {
        return entryEndDate;
    }

    public void setEntryEndDate(Long entryEndDate) {
        this.entryEndDate = entryEndDate;
    }

    public Long getSoldBeginDate() {
        return soldBeginDate;
    }

    public void setSoldBeginDate(Long soldBeginDate) {
        this.soldBeginDate = soldBeginDate;
    }

    public Long getSoldEndDate() {
        return soldEndDate;
    }

    public void setSoldEndDate(Long soldEndDate) {
        this.soldEndDate = soldEndDate;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Integer getMinSurface() {
        return minSurface;
    }

    public void setMinSurface(Integer minSurface) {
        this.minSurface = minSurface;
    }

    public Integer getMaxSurface() {
        return maxSurface;
    }

    public void setMaxSurface(Integer maxSurface) {
        this.maxSurface = maxSurface;
    }

    public Integer getMinNbRooms() {
        return minNbRooms;
    }

    public void setMinNbRooms(Integer minNbRooms) {
        this.minNbRooms = minNbRooms;
    }

    public Integer getMaxNbRooms() {
        return maxNbRooms;
    }

    public void setMaxNbRooms(Integer maxNbRooms) {
        this.maxNbRooms = maxNbRooms;
    }

    public Integer getMinNbBedrooms() {
        return minNbBedrooms;
    }

    public void setMinNbBedrooms(Integer minNbBedrooms) {
        this.minNbBedrooms = minNbBedrooms;
    }

    public Integer getMaxNbBedrooms() {
        return maxNbBedrooms;
    }

    public void setMaxNbBedrooms(Integer maxNbBedrooms) {
        this.maxNbBedrooms = maxNbBedrooms;
    }

    public Integer getMinNbBathrooms() {
        return minNbBathrooms;
    }

    public void setMinNbBathrooms(Integer minNbBathrooms) {
        this.minNbBathrooms = minNbBathrooms;
    }

    public Integer getMaxNbBathrooms() {
        return maxNbBathrooms;
    }

    public void setMaxNbBathrooms(Integer maxNbBathrooms) {
        this.maxNbBathrooms = maxNbBathrooms;
    }

    public Boolean getSoldStateAccepted() {
        return soldStateAccepted;
    }

    public void setSoldStateAccepted(Boolean soldStateAccepted) {
        this.soldStateAccepted = soldStateAccepted;
    }

    public Boolean getOnSaleStateAccepted() {
        return onSaleStateAccepted;
    }

    public void setOnSaleStateAccepted(Boolean onSaleStateAccepted) {
        this.onSaleStateAccepted = onSaleStateAccepted;
    }

    public Boolean getSchool() {
        return school;
    }

    public void setSchool(Boolean school) {
        this.school = school;
    }

    public Boolean getSwimmingPool() {
        return swimmingPool;
    }

    public void setSwimmingPool(Boolean swimmingPool) {
        this.swimmingPool = swimmingPool;
    }

    public Boolean getShop() {
        return shop;
    }

    public void setShop(Boolean shop) {
        this.shop = shop;
    }

    public Boolean getLibrary() {
        return library;
    }

    public void setLibrary(Boolean library) {
        this.library = library;
    }

    public Boolean getPark() {
        return park;
    }

    public void setPark(Boolean park) {
        this.park = park;
    }

    public Boolean getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Boolean restaurant) {
        this.restaurant = restaurant;
    }
}
