package com.dea42.resume.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ResumeHardx.
 */
@Entity
@Table(name = "resume_hardx")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ResumeHardx implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToMany(mappedBy = "resumeHardx")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "resumeHardx" }, allowSetters = true)
    private Set<ResumeHardware> hardwareIds = new HashSet<>();

    @OneToMany(mappedBy = "resumeHardx")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "resumeHardx", "resumeSoftx", "resumeRespon" }, allowSetters = true)
    private Set<ResumeJobs> jobIds = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ResumeHardx id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<ResumeHardware> getHardwareIds() {
        return this.hardwareIds;
    }

    public void setHardwareIds(Set<ResumeHardware> resumeHardwares) {
        if (this.hardwareIds != null) {
            this.hardwareIds.forEach(i -> i.setResumeHardx(null));
        }
        if (resumeHardwares != null) {
            resumeHardwares.forEach(i -> i.setResumeHardx(this));
        }
        this.hardwareIds = resumeHardwares;
    }

    public ResumeHardx hardwareIds(Set<ResumeHardware> resumeHardwares) {
        this.setHardwareIds(resumeHardwares);
        return this;
    }

    public ResumeHardx addHardwareId(ResumeHardware resumeHardware) {
        this.hardwareIds.add(resumeHardware);
        resumeHardware.setResumeHardx(this);
        return this;
    }

    public ResumeHardx removeHardwareId(ResumeHardware resumeHardware) {
        this.hardwareIds.remove(resumeHardware);
        resumeHardware.setResumeHardx(null);
        return this;
    }

    public Set<ResumeJobs> getJobIds() {
        return this.jobIds;
    }

    public void setJobIds(Set<ResumeJobs> resumeJobs) {
        if (this.jobIds != null) {
            this.jobIds.forEach(i -> i.setResumeHardx(null));
        }
        if (resumeJobs != null) {
            resumeJobs.forEach(i -> i.setResumeHardx(this));
        }
        this.jobIds = resumeJobs;
    }

    public ResumeHardx jobIds(Set<ResumeJobs> resumeJobs) {
        this.setJobIds(resumeJobs);
        return this;
    }

    public ResumeHardx addJobId(ResumeJobs resumeJobs) {
        this.jobIds.add(resumeJobs);
        resumeJobs.setResumeHardx(this);
        return this;
    }

    public ResumeHardx removeJobId(ResumeJobs resumeJobs) {
        this.jobIds.remove(resumeJobs);
        resumeJobs.setResumeHardx(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResumeHardx)) {
            return false;
        }
        return id != null && id.equals(((ResumeHardx) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResumeHardx{" +
            "id=" + getId() +
            "}";
    }
}
