package com.dea42.resume.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ResumeSummary.
 */
@Entity
@Table(name = "resume_summary")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ResumeSummary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "summary_name", nullable = false)
    private String summaryName;

    @NotNull
    @Column(name = "summary_order", nullable = false)
    private Integer summaryOrder;

    @ManyToOne
    @JsonIgnoreProperties(value = { "softwareCatIds", "summarryCatIds", "resumeSoftx", "resumeSoftware" }, allowSetters = true)
    private ResumeSoftware resumeSoftware;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ResumeSummary id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSummaryName() {
        return this.summaryName;
    }

    public ResumeSummary summaryName(String summaryName) {
        this.setSummaryName(summaryName);
        return this;
    }

    public void setSummaryName(String summaryName) {
        this.summaryName = summaryName;
    }

    public Integer getSummaryOrder() {
        return this.summaryOrder;
    }

    public ResumeSummary summaryOrder(Integer summaryOrder) {
        this.setSummaryOrder(summaryOrder);
        return this;
    }

    public void setSummaryOrder(Integer summaryOrder) {
        this.summaryOrder = summaryOrder;
    }

    public ResumeSoftware getResumeSoftware() {
        return this.resumeSoftware;
    }

    public void setResumeSoftware(ResumeSoftware resumeSoftware) {
        this.resumeSoftware = resumeSoftware;
    }

    public ResumeSummary resumeSoftware(ResumeSoftware resumeSoftware) {
        this.setResumeSoftware(resumeSoftware);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResumeSummary)) {
            return false;
        }
        return id != null && id.equals(((ResumeSummary) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResumeSummary{" +
            "id=" + getId() +
            ", summaryName='" + getSummaryName() + "'" +
            ", summaryOrder=" + getSummaryOrder() +
            "}";
    }
}
