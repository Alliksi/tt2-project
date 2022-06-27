package com.storage.logger.database.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "logs")
public class Log {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "log_id")
    private int logId;
    @Basic
    @Column(name = "created")
    private Timestamp created;
    @Basic
    @Column(name = "message")
    private String message;
    @Basic
    @Column(name = "restaurant_id")
    private Integer restaurantId;
    @Basic
    @Column(name = "status")
    private String status;

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Log logs = (Log) o;
        return logId == logs.logId && Objects.equals(created, logs.created) && Objects.equals(message, logs.message) && Objects.equals(restaurantId, logs.restaurantId) && Objects.equals(status, logs.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(logId, created, message, restaurantId, status);
    }
}
