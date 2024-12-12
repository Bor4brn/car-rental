package com.boraandege.carrental.dto;

public class LocationDTO {

    private Long id;
    private String code;
    private String name;
    private String address;

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public Long getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
