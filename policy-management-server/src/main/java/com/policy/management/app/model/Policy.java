package com.policy.management.app.model;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "policies")
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "policy_id")
    private Long policyId;

    @NotNull
    @Size(max = 255)
    @Column(name = "policy_name")
    private String policyName;

    @NotNull
    @Size(max = 255)
    @Column(name = "policy_type")
    private String policyType;

    @NotNull
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "years")
    private Integer years;

    @NotNull
    @Column(name = "premium")
    private Integer premium;

    @NotNull
    @Column(name = "sum_assured")
    private Integer sumAssured;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Size(max = 20)
    @Column(name = "policy_status")
    private PolicyStatus status;

    public Policy() {
    }

    public Policy(String policyName, String policyType, String description, Integer years, Integer premium, Integer sumAssured, PolicyStatus status) {
        this.policyName = policyName;
        this.policyType = policyType;
        this.description = description;
        this.years = years;
        this.premium = premium;
        this.sumAssured = sumAssured;
        this.status = status;
    }

    public Long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Long policyId) {
        this.policyId = policyId;
    }

    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    public String getPolicyType() {
        return policyType;
    }

    public void setPolicyType(String policyType) {
        this.policyType = policyType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getYears() {
        return years;
    }

    public void setYears(Integer years) {
        this.years = years;
    }

    public Integer getPremium() {
        return premium;
    }

    public void setPremium(Integer premium) {
        this.premium = premium;
    }

    public Integer getSumAssured() {
        return sumAssured;
    }

    public void setSumAssured(Integer sumAssured) {
        this.sumAssured = sumAssured;
    }

    public PolicyStatus getStatus() {
        return status;
    }

    public void setStatus(PolicyStatus status) {
        this.status = status;
    }
    
    @Override
    public int hashCode() {
    	return (int) policyId.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Policy policy = (Policy) o;
        return Objects.equals(policyId, policy.policyId);
    }
}
