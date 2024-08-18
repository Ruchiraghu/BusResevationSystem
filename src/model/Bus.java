package model;

public class Bus {
    private int busId;
    private String busName;
    private String busType;
    private int availableSeats;
    private String departureTime;
    private String arrivalTime;
    private String route;

  public Bus(){}

    public Bus(int busId, String busName, String busType,int availableSeats, String departureTime, String arrivalTime, String route) {
        this.busId = busId;
        this.busName = busName;
        this.busType = busType;
        this.availableSeats = availableSeats;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.route = route;
  }

    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getBusType()
    {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }
    public void addBus(Bus bus) {
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getRoute() {
        return route;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setRoute(String route) {
        this.route = route;
    }
    public void bookSeat() throws Exception {
        if (availableSeats > 0) {
            availableSeats--;
        } else {
            throw new Exception("No seats available!");
        }
    }

    public void cancelSeat() {
        availableSeats++;
    }
    @Override
    public String toString() {
        return "Bus{" +
                "busId=" + busId +
                ", busName='" + busName + '\'' +
                ", busType='" + busType + '\'' +
                ", availableSeats=" + availableSeats +
                ", departureTime='" + departureTime + '\'' +
                ", arrivalTime='" + arrivalTime + '\'' +
                ", route='" + route + '\'' +
                '}';
    }
}
