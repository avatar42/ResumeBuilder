package com.dea42.resume.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ResumeJobs.
 */
@Entity
@Table(name = "resume_jobs")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ResumeJobs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @NotNull
    @Column(name = "company", nullable = false)
    private String company;

    @Column(name = "work_street")
    private String workStreet;

    @NotNull
    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "work_zip")
    private String workZip;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "months")
    private Double months;

    @NotNull
    @Column(name = "contract", nullable = false)
    private Integer contract;

    @Column(name = "agency")
    private String agency;

    @NotNull
    @Column(name = "mgr", nullable = false)
    private String mgr;

    @Column(name = "mgr_phone")
    private String mgrPhone;

    @Column(name = "pay")
    private String pay;

    @Column(name = "corp_addr")
    private String corpAddr;

    @Column(name = "corp_phone")
    private String corpPhone;

    @NotNull
    @Column(name = "agency_addr", nullable = false)
    private String agencyAddr;

    @Column(name = "agency_phone")
    private String agencyPhone;

    @ManyToOne
    @JsonIgnoreProperties(value = { "hardwareIds", "jobIds" }, allowSetters = true)
    private ResumeHardx resumeHardx;

    @ManyToOne
    @JsonIgnoreProperties(value = { "jobIds", "softwareIds" }, allowSetters = true)
    private ResumeSoftx resumeSoftx;

    @ManyToOne
    @JsonIgnoreProperties(value = { "jobIds" }, allowSetters = true)
    private ResumeRespon resumeRespon;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ResumeJobs id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public ResumeJobs startDate(LocalDate startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public ResumeJobs endDate(LocalDate endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getCompany() {
        return this.company;
    }

    public ResumeJobs company(String company) {
        this.setCompany(company);
        return this;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getWorkStreet() {
        return this.workStreet;
    }

    public ResumeJobs workStreet(String workStreet) {
        this.setWorkStreet(workStreet);
        return this;
    }

    public void setWorkStreet(String workStreet) {
        this.workStreet = workStreet;
    }

    public String getLocation() {
        return this.location;
    }

    public ResumeJobs location(String location) {
        this.setLocation(location);
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWorkZip() {
        return this.workZip;
    }

    public ResumeJobs workZip(String workZip) {
        this.setWorkZip(workZip);
        return this;
    }

    public void setWorkZip(String workZip) {
        this.workZip = workZip;
    }

    public String getTitle() {
        return this.title;
    }

    public ResumeJobs title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getMonths() {
        return this.months;
    }

    public ResumeJobs months(Double months) {
        this.setMonths(months);
        return this;
    }

    public void setMonths(Double months) {
        this.months = months;
    }

    public Integer getContract() {
        return this.contract;
    }

    public ResumeJobs contract(Integer contract) {
        this.setContract(contract);
        return this;
    }

    public void setContract(Integer contract) {
        this.contract = contract;
    }

    public String getAgency() {
        return this.agency;
    }

    public ResumeJobs agency(String agency) {
        this.setAgency(agency);
        return this;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getMgr() {
        return this.mgr;
    }

    public ResumeJobs mgr(String mgr) {
        this.setMgr(mgr);
        return this;
    }

    public void setMgr(String mgr) {
        this.mgr = mgr;
    }

    public String getMgrPhone() {
        return this.mgrPhone;
    }

    public ResumeJobs mgrPhone(String mgrPhone) {
        this.setMgrPhone(mgrPhone);
        return this;
    }

    public void setMgrPhone(String mgrPhone) {
        this.mgrPhone = mgrPhone;
    }

    public String getPay() {
        return this.pay;
    }

    public ResumeJobs pay(String pay) {
        this.setPay(pay);
        return this;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public String getCorpAddr() {
        return this.corpAddr;
    }

    public ResumeJobs corpAddr(String corpAddr) {
        this.setCorpAddr(corpAddr);
        return this;
    }

    public void setCorpAddr(String corpAddr) {
        this.corpAddr = corpAddr;
    }

    public String getCorpPhone() {
        return this.corpPhone;
    }

    public ResumeJobs corpPhone(String corpPhone) {
        this.setCorpPhone(corpPhone);
        return this;
    }

    public void setCorpPhone(String corpPhone) {
        this.corpPhone = corpPhone;
    }

    public String getAgencyAddr() {
        return this.agencyAddr;
    }

    public ResumeJobs agencyAddr(String agencyAddr) {
        this.setAgencyAddr(agencyAddr);
        return this;
    }

    public void setAgencyAddr(String agencyAddr) {
        this.agencyAddr = agencyAddr;
    }

    public String getAgencyPhone() {
        return this.agencyPhone;
    }

    public ResumeJobs agencyPhone(String agencyPhone) {
        this.setAgencyPhone(agencyPhone);
        return this;
    }

    public void setAgencyPhone(String agencyPhone) {
        this.agencyPhone = agencyPhone;
    }

    public ResumeHardx getResumeHardx() {
        return this.resumeHardx;
    }

    public void setResumeHardx(ResumeHardx resumeHardx) {
        this.resumeHardx = resumeHardx;
    }

    public ResumeJobs resumeHardx(ResumeHardx resumeHardx) {
        this.setResumeHardx(resumeHardx);
        return this;
    }

    public ResumeSoftx getResumeSoftx() {
        return this.resumeSoftx;
    }

    public void setResumeSoftx(ResumeSoftx resumeSoftx) {
        this.resumeSoftx = resumeSoftx;
    }

    public ResumeJobs resumeSoftx(ResumeSoftx resumeSoftx) {
        this.setResumeSoftx(resumeSoftx);
        return this;
    }

    public ResumeRespon getResumeRespon() {
        return this.resumeRespon;
    }

    public void setResumeRespon(ResumeRespon resumeRespon) {
        this.resumeRespon = resumeRespon;
    }

    public ResumeJobs resumeRespon(ResumeRespon resumeRespon) {
        this.setResumeRespon(resumeRespon);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResumeJobs)) {
            return false;
        }
        return id != null && id.equals(((ResumeJobs) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResumeJobs{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", company='" + getCompany() + "'" +
            ", workStreet='" + getWorkStreet() + "'" +
            ", location='" + getLocation() + "'" +
            ", workZip='" + getWorkZip() + "'" +
            ", title='" + getTitle() + "'" +
            ", months=" + getMonths() +
            ", contract=" + getContract() +
            ", agency='" + getAgency() + "'" +
            ", mgr='" + getMgr() + "'" +
            ", mgrPhone='" + getMgrPhone() + "'" +
            ", pay='" + getPay() + "'" +
            ", corpAddr='" + getCorpAddr() + "'" +
            ", corpPhone='" + getCorpPhone() + "'" +
            ", agencyAddr='" + getAgencyAddr() + "'" +
            ", agencyPhone='" + getAgencyPhone() + "'" +
            "}";
    }
}
