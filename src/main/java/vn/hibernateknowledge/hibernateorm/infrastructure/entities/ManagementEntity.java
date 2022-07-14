package vn.hibernateknowledge.hibernateorm.infrastructure.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Entity(name = "management")
public class ManagementEntity implements Serializable {

    private static final long serialVersionUID = -495703064152328044L;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true, cascade = { CascadeType.PERSIST, CascadeType.DETACH,
            CascadeType.MERGE, CascadeType.REFRESH })
    @JoinColumn(name = "management_id")
    @MapsId
    private DataEntity dataEntity;

    @Id
    private Long managementId;

    private String status;

    @Column(name = "start_time")
    private long startTime;

    @Column(name = "end_time")
    private long endTime;

    public DataEntity getDataEntity() {
        return dataEntity;
    }

    public void setDataEntity(DataEntity dataEntity) {
        this.dataEntity = dataEntity;
    }

    public Long getManagementId() {
        return managementId;
    }

    public void setManagementId(Long managementId) {
        this.managementId = managementId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}