package com.policy.management.app.model;

public enum UserStatus {

    ACTIVE("ACTIVE"),INACTIVE("INACTIVE");
    
    private String value;

    public String getAction()
    {
        return this.value;
    }

    private UserStatus(String value)
    {
        this.value = value;
    }
}
