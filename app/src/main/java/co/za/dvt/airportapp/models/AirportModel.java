package co.za.dvt.airportapp.models;

import com.google.gson.annotations.SerializedName;

public class AirportModel {
    @SerializedName("nameAirport")
    private String nameAirport;
    @SerializedName("codeIataAirport")
    private String codeIataAirport;
    @SerializedName("codeIcaoAirport")
    private String codeIcaoAirport;
    @SerializedName("nameTranslations")
    private String nameTranslations;
    @SerializedName("latitudeAirport")
    private String latitudeAirport;
    @SerializedName("longitudeAirport")
    private String longitudeAirport;
    @SerializedName("timezone")
    private String timezone;
    @SerializedName("GMT")
    private String GMT;
    @SerializedName("phone")
    private String phone;
    @SerializedName("nameCountry")
    private String nameCountry;
    @SerializedName("codeIso2Country")
    private String codeIso2Country;
    @SerializedName("vcodeIataCity")
    private String vcodeIataCity;
    @SerializedName("distance")
    private String distance;

    public String getNameAirport() {
        return nameAirport;
    }

    public void setNameAirport(String nameAirport) {
        this.nameAirport = nameAirport;
    }

    public String getCodeIataAirport() {
        return codeIataAirport;
    }

    public void setCodeIataAirport(String codeIataAirport) {
        this.codeIataAirport = codeIataAirport;
    }

    public String getCodeIcaoAirport() {
        return codeIcaoAirport;
    }

    public void setCodeIcaoAirport(String codeIcaoAirport) {
        this.codeIcaoAirport = codeIcaoAirport;
    }

    public String getNameTranslations() {
        return nameTranslations;
    }

    public void setNameTranslations(String nameTranslations) {
        this.nameTranslations = nameTranslations;
    }

    public String getLatitudeAirport() {
        return latitudeAirport;
    }

    public void setLatitudeAirport(String latitudeAirport) {
        this.latitudeAirport = latitudeAirport;
    }

    public String getLongitudeAirport() {
        return longitudeAirport;
    }

    public void setLongitudeAirport(String longitudeAirport) {
        this.longitudeAirport = longitudeAirport;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getGMT() {
        return GMT;
    }

    public void setGMT(String GMT) {
        this.GMT = GMT;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNameCountry() {
        return nameCountry;
    }

    public void setNameCountry(String nameCountry) {
        this.nameCountry = nameCountry;
    }

    public String getCodeIso2Country() {
        return codeIso2Country;
    }

    public void setCodeIso2Country(String codeIso2Country) {
        this.codeIso2Country = codeIso2Country;
    }

    public String getVcodeIataCity() {
        return vcodeIataCity;
    }

    public void setVcodeIataCity(String vcodeIataCity) {
        this.vcodeIataCity = vcodeIataCity;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}