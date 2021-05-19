package com.policy.management.app.model;

public enum PolicyStatus {

    ACTIVE("ACTIVE"),INACTIVE("INACTIVE");
    private String value;

    public String getAction()
    {
        return this.value;
    }

    private PolicyStatus(String value)
    {
        this.value = value;
    }
}
