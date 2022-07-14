package vn.hibernateknowledge.hibernateorm.infrastructure.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "session")
public class SessionEntity implements Serializable {

    private static final long serialVersionUID = -5687634074129094497L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "session_sequence")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "management_id")
    private ManagementEntity managementEntity;

    @Column(name = "user_name")
    private String username;

    @Column(name = "start_time")
    private long startTime;

    @Column(name = "end_time")
    private long endTime;

    public ManagementEntity getManagementEntity() {
        return managementEntity;
    }

    public void setManagementEntity(ManagementEntity managementEntity) {
        this.managementEntity = managementEntity;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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