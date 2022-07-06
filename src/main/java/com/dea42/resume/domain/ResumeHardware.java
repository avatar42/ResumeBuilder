package com.dea42.resume.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ResumeHardware.
 */
@Entity
@Table(name = "resume_hardware")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ResumeHardware implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "repair_certified", nullable = false)
    private Integer repairCertified;

    @NotNull
    @Column(name = "show_sum", nullable = false)
    private Integer showSum;

    @NotNull
    @Column(name = "hardware_name", nullable = false)
    private String hardwareName;

    @ManyToOne
    @JsonIgnoreProperties(value = { "hardwareIds", "jobIds" }, allowSetters = true)
    private ResumeHardx resumeHardx;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ResumeHardware id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRepairCertified() {
        return this.repairCertified;
    }

    public ResumeHardware repairCertified(Integer repairCertified) {
        this.setRepairCertified(repairCertified);
        return this;
    }

    public void setRepairCertified(Integer repairCertified) {
        this.repairCertified = repairCertified;
    }

    public Integer getShowSum() {
        return this.showSum;
    }

    public ResumeHardware showSum(Integer showSum) {
        this.setShowSum(showSum);
        return this;
    }

    public void setShowSum(Integer showSum) {
        this.showSum = showSum;
    }

    public String getHardwareName() {
        return this.hardwareName;
    }

    public ResumeHardware hardwareName(String hardwareName) {
        this.setHardwareName(hardwareName);
        return this;
    }

    public void setHardwareName(String hardwareName) {
        this.hardwareName = hardwareName;
    }

    public ResumeHardx getResumeHardx() {
        return this.resumeHardx;
    }

    public void setResumeHardx(ResumeHardx resumeHardx) {
        this.resumeHardx = resumeHardx;
    }

    public ResumeHardware resumeHardx(ResumeHardx resumeHardx) {
        this.setResumeHardx(resumeHardx);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResumeHardware)) {
            return false;
        }
        return id != null && id.equals(((ResumeHardware) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResumeHardware{" +
            "id=" + getId() +
            ", repairCertified=" + getRepairCertified() +
            ", showSum=" + getShowSum() +
            ", hardwareName='" + getHardwareName() + "'" +
            "}";
    }
}
