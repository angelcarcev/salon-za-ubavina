package mk.ukim.finki.web_seminarska.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import mk.ukim.finki.web_seminarska.model.enumerations.SalonType;

import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Salon {

    public Salon(){
    }

    public Salon(String name, String address, byte[] image,
                 LocalTime open_time, LocalTime close_time,
                 String city, String phone_number, String email,
                 SalonType type, String description,
                 List<Uslugi> services) {
        this.name = name;
        this.address = address;
        this.image = image;
        this.open_time = open_time;
        this.close_time = close_time;
        this.city = city;
        this.phone_number = phone_number;
        this.email = email;
        this.type = type;
        this.description = description;
        this.services = services;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    private byte[] image;

    private LocalTime open_time;

    private LocalTime close_time;

    private String city;

    private String phone_number;

    private String email;

    @Enumerated(EnumType.STRING)
    private SalonType type;

    private String description;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Uslugi> services;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public byte[] getImage() {
        return image;
    }

    public LocalTime getOpen_time() {
        return open_time;
    }

    public LocalTime getClose_time() {
        return close_time;
    }

    public String getCity() {
        return city;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getEmail() {
        return email;
    }

    public SalonType getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public List<Uslugi> getServices() {
        return services;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setOpen_time(LocalTime open_time) {
        this.open_time = open_time;
    }

    public void setClose_time(LocalTime close_time) {
        this.close_time = close_time;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setType(SalonType type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setServices(List<Uslugi> services) {
        this.services = services;
    }
}
