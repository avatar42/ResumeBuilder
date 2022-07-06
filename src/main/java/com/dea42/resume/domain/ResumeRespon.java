package com.dea42.resume.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ResumeRespon.
 */
@Entity
@Table(name = "resume_respon")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ResumeRespon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "job_id", nullable = false)
    private Integer jobId;

    @NotNull
    @Column(name = "respon_order", nullable = false)
    private Integer responOrder;

    @NotNull
    @Column(name = "respon_show", nullable = false)
    private Integer responShow;

    @OneToMany(mappedBy = "resumeRespon")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "resumeHardx", "resumeSoftx", "resumeRespon" }, allowSetters = true)
    private Set<ResumeJobs> jobIds = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ResumeRespon id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getJobId() {
        return this.jobId;
    }

    public ResumeRespon jobId(Integer jobId) {
        this.setJobId(jobId);
        return this;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public Integer getResponOrder() {
        return this.responOrder;
    }

    public ResumeRespon responOrder(Integer responOrder) {
        this.setResponOrder(responOrder);
        return this;
    }

    public void setResponOrder(Integer responOrder) {
        this.responOrder = responOrder;
    }

    public Integer getResponShow() {
        return this.responShow;
    }

    public ResumeRespon responShow(Integer responShow) {
        this.setResponShow(responShow);
        return this;
    }

    public void setResponShow(Integer responShow) {
        this.responShow = responShow;
    }

    public Set<ResumeJobs> getJobIds() {
        return this.jobIds;
    }

    public void setJobIds(Set<ResumeJobs> resumeJobs) {
        if (this.jobIds != null) {
            this.jobIds.forEach(i -> i.setResumeRespon(null));
        }
        if (resumeJobs != null) {
            resumeJobs.forEach(i -> i.setResumeRespon(this));
        }
        this.jobIds = resumeJobs;
    }

    public ResumeRespon jobIds(Set<ResumeJobs> resumeJobs) {
        this.setJobIds(resumeJobs);
        return this;
    }

    public ResumeRespon addJobId(ResumeJobs resumeJobs) {
        this.jobIds.add(resumeJobs);
        resumeJobs.setResumeRespon(this);
        return this;
    }

    public ResumeRespon removeJobId(ResumeJobs resumeJobs) {
        this.jobIds.remove(resumeJobs);
        resumeJobs.setResumeRespon(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResumeRespon)) {
            return false;
        }
        return id != null && id.equals(((ResumeRespon) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResumeRespon{" +
            "id=" + getId() +
            ", jobId=" + getJobId() +
            ", responOrder=" + getResponOrder() +
            ", responShow=" + getResponShow() +
            "}";
    }
}
