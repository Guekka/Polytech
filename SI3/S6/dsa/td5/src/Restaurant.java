public class Restaurant {
    private double latitude;
    private double longitude;
    private String name;
    private String address;
    private String city;
    private String state;
    private String phone;

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double latitude() {
        return latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double longitude() {
        return longitude;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String address() {
        return address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String city() {
        return city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String state() {
        return state;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String phone() {
        return phone;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
