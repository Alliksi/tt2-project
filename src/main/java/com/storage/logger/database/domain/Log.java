package com.storage.logger.database.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "logs", schema = "public", catalog = "restaurant-storage")
public class Log {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "log_id", nullable = false)
    private int logId;
    @Basic
    @Column(name = "created", nullable = false)
    private Timestamp created;
    @Basic
    @Column(name = "message", nullable = false, length = 1028)
    private String message;
    @Basic
    @Column(name = "restaurant_id", nullable = true)
    private Integer restaurantId;
    @Basic
    @Column(name = "user_id", nullable = true)
    private Integer userId;
    @Basic
    @Column(name = "status", nullable = true, length = 16)
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
        Log log = (Log) o;
        return logId == log.logId && Objects.equals(created, log.created) && Objects.equals(message, log.message) && Objects.equals(restaurantId, log.restaurantId) && Objects.equals(userId, log.userId) && Objects.equals(status, log.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(logId, created, message, restaurantId, userId, status);
    }
}
