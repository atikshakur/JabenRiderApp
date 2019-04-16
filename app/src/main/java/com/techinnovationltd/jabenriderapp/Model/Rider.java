package com.techinnovationltd.jabenriderapp.Model;

public class Rider {

    private String id;
    private String Name;
    private String Address;
    private String NID_PASSPORT;
    private String Rider_Birth;
    private String Phone;
    private String City;
    private String Vehicle;
    private String Driving_license;
        private String Fitness;
    private String Tax_token;
    private String Referrel;
    private String Rider_Vehicle;
    private String User_image;
    private String Service_type;
    private String Bikas_Nume;
    private String Bikas_Trans;
    private String Status;

    public Rider() {
    }

    public Rider(String id, String name, String address, String NID_PASSPORT, String rider_Birth, String phone, String city, String vehicle, String driving_license, String fitness, String tax_token, String referrel, String rider_Vehicle, String user_image, String service_type, String bikas_Nume, String bikas_Trans, String status) {
        this.id = id;
        Name = name;
        Address = address;
        this.NID_PASSPORT = NID_PASSPORT;
        Rider_Birth = rider_Birth;
        Phone = phone;
        City = city;
        Vehicle = vehicle;
        Driving_license = driving_license;
        Fitness = fitness;
        Tax_token = tax_token;
        Referrel = referrel;
        Rider_Vehicle = rider_Vehicle;
        User_image = user_image;
        Service_type = service_type;
        Bikas_Nume = bikas_Nume;
        Bikas_Trans = bikas_Trans;
        Status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getNID_PASSPORT() {
        return NID_PASSPORT;
    }

    public void setNID_PASSPORT(String NID_PASSPORT) {
        this.NID_PASSPORT = NID_PASSPORT;
    }

    public String getRider_Birth() {
        return Rider_Birth;
    }

    public void setRider_Birth(String rider_Birth) {
        Rider_Birth = rider_Birth;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getVehicle() {
        return Vehicle;
    }

    public void setVehicle(String vehicle) {
        Vehicle = vehicle;
    }

    public String getDriving_license() {
        return Driving_license;
    }

    public void setDriving_license(String driving_license) {
        Driving_license = driving_license;
    }

    public String getFitness() {
        return Fitness;
    }

    public void setFitness(String fitness) {
        Fitness = fitness;
    }

    public String getTax_token() {
        return Tax_token;
    }

    public void setTax_token(String tax_token) {
        Tax_token = tax_token;
    }

    public String getReferrel() {
        return Referrel;
    }

    public void setReferrel(String referrel) {
        Referrel = referrel;
    }

    public String getRider_Vehicle() {
        return Rider_Vehicle;
    }

    public void setRider_Vehicle(String rider_Vehicle) {
        Rider_Vehicle = rider_Vehicle;
    }

    public String getUser_image() {
        return User_image;
    }

    public void setUser_image(String user_image) {
        User_image = user_image;
    }

    public String getService_type() {
        return Service_type;
    }

    public void setService_type(String service_type) {
        Service_type = service_type;
    }

    public String getBikas_Nume() {
        return Bikas_Nume;
    }

    public void setBikas_Nume(String bikas_Nume) {
        Bikas_Nume = bikas_Nume;
    }

    public String getBikas_Trans() {
        return Bikas_Trans;
    }

    public void setBikas_Trans(String bikas_Trans) {
        Bikas_Trans = bikas_Trans;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
