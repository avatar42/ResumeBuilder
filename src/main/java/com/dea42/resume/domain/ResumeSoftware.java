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
 * A ResumeSoftware.
 */
@Entity
@Table(name = "resume_software")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ResumeSoftware implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "software_name", nullable = false)
    private String softwareName;

    @NotNull
    @Column(name = "software_ver", nullable = false)
    private String softwareVer;

    @NotNull
    @Column(name = "software_cat_id", nullable = false)
    private Integer softwareCatId;

    @NotNull
    @Column(name = "summarry_cat_id", nullable = false)
    private Integer summarryCatId;

    @OneToMany(mappedBy = "resumeSoftware")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "softwareCatIds", "summarryCatIds", "resumeSoftx", "resumeSoftware" }, allowSetters = true)
    private Set<ResumeSoftware> softwareCatIds = new HashSet<>();

    @OneToMany(mappedBy = "resumeSoftware")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "resumeSoftware" }, allowSetters = true)
    private Set<ResumeSummary> summarryCatIds = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "jobIds", "softwareIds" }, allowSetters = true)
    private ResumeSoftx resumeSoftx;

    @ManyToOne
    @JsonIgnoreProperties(value = { "softwareCatIds", "summarryCatIds", "resumeSoftx", "resumeSoftware" }, allowSetters = true)
    private ResumeSoftware resumeSoftware;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ResumeSoftware id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSoftwareName() {
        return this.softwareName;
    }

    public ResumeSoftware softwareName(String softwareName) {
        this.setSoftwareName(softwareName);
        return this;
    }

    public void setSoftwareName(String softwareName) {
        this.softwareName = softwareName;
    }

    public String getSoftwareVer() {
        return this.softwareVer;
    }

    public ResumeSoftware softwareVer(String softwareVer) {
        this.setSoftwareVer(softwareVer);
        return this;
    }

    public void setSoftwareVer(String softwareVer) {
        this.softwareVer = softwareVer;
    }

    public Integer getSoftwareCatId() {
        return this.softwareCatId;
    }

    public ResumeSoftware softwareCatId(Integer softwareCatId) {
        this.setSoftwareCatId(softwareCatId);
        return this;
    }

    public void setSoftwareCatId(Integer softwareCatId) {
        this.softwareCatId = softwareCatId;
    }

    public Integer getSummarryCatId() {
        return this.summarryCatId;
    }

    public ResumeSoftware summarryCatId(Integer summarryCatId) {
        this.setSummarryCatId(summarryCatId);
        return this;
    }

    public void setSummarryCatId(Integer summarryCatId) {
        this.summarryCatId = summarryCatId;
    }

    public Set<ResumeSoftware> getSoftwareCatIds() {
        return this.softwareCatIds;
    }

    public void setSoftwareCatIds(Set<ResumeSoftware> resumeSoftwares) {
        if (this.softwareCatIds != null) {
            this.softwareCatIds.forEach(i -> i.setResumeSoftware(null));
        }
        if (resumeSoftwares != null) {
            resumeSoftwares.forEach(i -> i.setResumeSoftware(this));
        }
        this.softwareCatIds = resumeSoftwares;
    }

    public ResumeSoftware softwareCatIds(Set<ResumeSoftware> resumeSoftwares) {
        this.setSoftwareCatIds(resumeSoftwares);
        return this;
    }

    public ResumeSoftware addSoftwareCatId(ResumeSoftware resumeSoftware) {
        this.softwareCatIds.add(resumeSoftware);
        resumeSoftware.setResumeSoftware(this);
        return this;
    }

    public ResumeSoftware removeSoftwareCatId(ResumeSoftware resumeSoftware) {
        this.softwareCatIds.remove(resumeSoftware);
        resumeSoftware.setResumeSoftware(null);
        return this;
    }

    public Set<ResumeSummary> getSummarryCatIds() {
        return this.summarryCatIds;
    }

    public void setSummarryCatIds(Set<ResumeSummary> resumeSummaries) {
        if (this.summarryCatIds != null) {
            this.summarryCatIds.forEach(i -> i.setResumeSoftware(null));
        }
        if (resumeSummaries != null) {
            resumeSummaries.forEach(i -> i.setResumeSoftware(this));
        }
        this.summarryCatIds = resumeSummaries;
    }

    public ResumeSoftware summarryCatIds(Set<ResumeSummary> resumeSummaries) {
        this.setSummarryCatIds(resumeSummaries);
        return this;
    }

    public ResumeSoftware addSummarryCatId(ResumeSummary resumeSummary) {
        this.summarryCatIds.add(resumeSummary);
        resumeSummary.setResumeSoftware(this);
        return this;
    }

    public ResumeSoftware removeSummarryCatId(ResumeSummary resumeSummary) {
        this.summarryCatIds.remove(resumeSummary);
        resumeSummary.setResumeSoftware(null);
        return this;
    }

    public ResumeSoftx getResumeSoftx() {
        return this.resumeSoftx;
    }

    public void setResumeSoftx(ResumeSoftx resumeSoftx) {
        this.resumeSoftx = resumeSoftx;
    }

    public ResumeSoftware resumeSoftx(ResumeSoftx resumeSoftx) {
        this.setResumeSoftx(resumeSoftx);
        return this;
    }

    public ResumeSoftware getResumeSoftware() {
        return this.resumeSoftware;
    }

    public void setResumeSoftware(ResumeSoftware resumeSoftware) {
        this.resumeSoftware = resumeSoftware;
    }

    public ResumeSoftware resumeSoftware(ResumeSoftware resumeSoftware) {
        this.setResumeSoftware(resumeSoftware);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResumeSoftware)) {
            return false;
        }
        return id != null && id.equals(((ResumeSoftware) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResumeSoftware{" +
            "id=" + getId() +
            ", softwareName='" + getSoftwareName() + "'" +
            ", softwareVer='" + getSoftwareVer() + "'" +
            ", softwareCatId=" + getSoftwareCatId() +
            ", summarryCatId=" + getSummarryCatId() +
            "}";
    }
}
