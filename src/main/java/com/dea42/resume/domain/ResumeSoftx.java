package com.dea42.resume.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ResumeSoftx.
 */
@Entity
@Table(name = "resume_softx")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ResumeSoftx implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToMany(mappedBy = "resumeSoftx")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "resumeHardx", "resumeSoftx", "resumeRespon" }, allowSetters = true)
    private Set<ResumeJobs> jobIds = new HashSet<>();

    @OneToMany(mappedBy = "resumeSoftx")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "softwareCatIds", "summarryCatIds", "resumeSoftx", "resumeSoftware" }, allowSetters = true)
    private Set<ResumeSoftware> softwareIds = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ResumeSoftx id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<ResumeJobs> getJobIds() {
        return this.jobIds;
    }

    public void setJobIds(Set<ResumeJobs> resumeJobs) {
        if (this.jobIds != null) {
            this.jobIds.forEach(i -> i.setResumeSoftx(null));
        }
        if (resumeJobs != null) {
            resumeJobs.forEach(i -> i.setResumeSoftx(this));
        }
        this.jobIds = resumeJobs;
    }

    public ResumeSoftx jobIds(Set<ResumeJobs> resumeJobs) {
        this.setJobIds(resumeJobs);
        return this;
    }

    public ResumeSoftx addJobId(ResumeJobs resumeJobs) {
        this.jobIds.add(resumeJobs);
        resumeJobs.setResumeSoftx(this);
        return this;
    }

    public ResumeSoftx removeJobId(ResumeJobs resumeJobs) {
        this.jobIds.remove(resumeJobs);
        resumeJobs.setResumeSoftx(null);
        return this;
    }

    public Set<ResumeSoftware> getSoftwareIds() {
        return this.softwareIds;
    }

    public void setSoftwareIds(Set<ResumeSoftware> resumeSoftwares) {
        if (this.softwareIds != null) {
            this.softwareIds.forEach(i -> i.setResumeSoftx(null));
        }
        if (resumeSoftwares != null) {
            resumeSoftwares.forEach(i -> i.setResumeSoftx(this));
        }
        this.softwareIds = resumeSoftwares;
    }

    public ResumeSoftx softwareIds(Set<ResumeSoftware> resumeSoftwares) {
        this.setSoftwareIds(resumeSoftwares);
        return this;
    }

    public ResumeSoftx addSoftwareId(ResumeSoftware resumeSoftware) {
        this.softwareIds.add(resumeSoftware);
        resumeSoftware.setResumeSoftx(this);
        return this;
    }

    public ResumeSoftx removeSoftwareId(ResumeSoftware resumeSoftware) {
        this.softwareIds.remove(resumeSoftware);
        resumeSoftware.setResumeSoftx(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResumeSoftx)) {
            return false;
        }
        return id != null && id.equals(((ResumeSoftx) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResumeSoftx{" +
            "id=" + getId() +
            "}";
    }
}
