package com.kasunthilina.elegentmedia;

public class Data {
    private String title;
    private String description;
    private String address;
    private String postcode;
    private String phoneNumber;
    private String latitude;
    private String longitude;
    private String image;

    public Data(){

    }
    public Data(String title,String description,String address,String postcode,String phoneNumber,String latitude,
                String longitude,String image){
        this.title=title;
        this.description=description;
        this.address=address;
        this.postcode=postcode;
        this.phoneNumber=phoneNumber;
        this.latitude=latitude;
        this.longitude=longitude;
        this.image=image;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getAddress() {
        return address;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getImage() {
        return image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
