package com.example.designercompanybackend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "site_info")
public class SiteInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="key_name", nullable = false, unique = true)
    private String keyName;   // HOME_TITLE, ABOUT_TEXT, CONTACT_PHONE, ...

    @Column(columnDefinition = "TEXT")
    private String value;     // Georgian text is fine

    public SiteInfo() {}

    public SiteInfo(String keyName, String value) {
        this.keyName = keyName;
        this.value = value;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getKeyName() { return keyName; }
    public void setKeyName(String keyName) { this.keyName = keyName; }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
}