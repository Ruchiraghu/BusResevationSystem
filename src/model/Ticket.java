package model;

import java.time.LocalDate;

public class Ticket {
    private int reservationId;
    private int busId;
    private int customerId;
    private LocalDate reservationDate;
    private String status;

//    public Ticket(int reservation_id, int bus_id, int customer_id, LocalDate reservation_date, String status){}

    public Ticket(int reservationId, int busId, int customerId, LocalDate reservationDate, String status) {
        this.reservationId = reservationId;
        this.busId = busId;
        this.customerId = customerId;
        this.reservationDate = reservationDate;
        this.status = status;
    }

    public int getreservationId() {
        return reservationId;
    }

    public void setreservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "reservationId=" + reservationId +
                ", busId=" + busId +
                ", customerId=" + customerId +
                ", reservationDate=" + reservationDate +
                ", status='" + status + '\'' +
                '}';
    }
}
