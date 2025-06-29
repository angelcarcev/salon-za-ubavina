package mk.ukim.finki.web_seminarska.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Appointment {

    public Appointment(){
    }

    public Appointment(LocalDateTime start_time,
                       LocalDateTime end_time,
                       Salon salon, SalonUser user,
                       List<Uslugi> services) {
        this.start_time = start_time;
        this.end_time = end_time;
        this.salon = salon;
        this.user = user;
        this.services = services;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime start_time;

    private LocalDateTime end_time;

    @ManyToOne
    private Salon salon;

    @ManyToOne
    private SalonUser user;

    @ManyToMany
    private List<Uslugi> services;

    public Long getId() {
        return id;
    }

    public LocalDateTime getStart_time() {
        return start_time;
    }

    public LocalDateTime getEnd_time() {
        return end_time;
    }

    public Salon getSalon() {
        return salon;
    }

    public SalonUser getUser() {
        return user;
    }

    public List<Uslugi> getServices() {
        return services;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setStart_time(LocalDateTime start_time) {
        this.start_time = start_time;
    }

    public void setEnd_time(LocalDateTime end_time) {
        this.end_time = end_time;
    }

    public void setSalon(Salon salon) {
        this.salon = salon;
    }

    public void setUser(SalonUser user) {
        this.user = user;
    }

    public void setServices(List<Uslugi> services) {
        this.services = services;
    }
}
