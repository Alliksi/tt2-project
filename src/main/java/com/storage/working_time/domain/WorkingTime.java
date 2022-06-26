package com.storage.working_time.domain;

import javax.persistence.*;
import java.sql.Time;
import java.util.Objects;

@Entity
@Table(name = "working_time", schema = "public", catalog = "restaurant-storage")
public class WorkingTime {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "working_time_id")
    private int workingTimeId;
    @Basic
    @Column(name = "shift_start")
    private Time shiftStart;
    @Basic
    @Column(name = "shift_end")
    private Time shiftEnd;
    @Basic
    @Column(name = "day_of_week")
    private int dayOfWeek;
    @Basic
    @Column(name = "restaurant_id")
    private Integer restaurantId;

    public int getWorkingTimeId() {
        return workingTimeId;
    }

    public void setWorkingTimeId(int workingTimeId) {
        this.workingTimeId = workingTimeId;
    }

    public Time getShiftStart() {
        return shiftStart;
    }

    public void setShiftStart(Time shiftStart) {
        this.shiftStart = shiftStart;
    }

    public Time getShiftEnd() {
        return shiftEnd;
    }

    public void setShiftEnd(Time shiftEnd) {
        this.shiftEnd = shiftEnd;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkingTime that = (WorkingTime) o;
        return workingTimeId == that.workingTimeId && dayOfWeek == that.dayOfWeek && Objects.equals(shiftStart, that.shiftStart) && Objects.equals(shiftEnd, that.shiftEnd) && Objects.equals(restaurantId, that.restaurantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workingTimeId, shiftStart, shiftEnd, dayOfWeek, restaurantId);
    }
}
