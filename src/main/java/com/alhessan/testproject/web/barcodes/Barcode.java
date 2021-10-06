package com.alhessan.testproject.web.barcodes;

import javax.persistence.*;

@Entity
@Table(name = "barcodes")
public class Barcode{

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Column(name = "name", nullable = false, length = 20, unique = true)
    private String name;
    @Column(nullable = true, length = 250)
    private String details;

    private int code;

    @Column(name = "user_id")
    private long userId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}