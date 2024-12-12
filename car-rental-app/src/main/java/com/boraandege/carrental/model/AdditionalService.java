package com.boraandege.carrental.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "services")
public class AdditionalService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String name;

    private BigDecimal price;

    @ManyToMany(mappedBy = "additionalServices")
    private List<Reservation> reservations;

    public AdditionalService() {}

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}
