package com.altair.eightstar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Services.
 */
@Entity
@Table(name = "services")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Services implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "parentService")
    @JsonIgnoreProperties(value = { "childServices", "hotelServices", "parentService", "serviceRequests" }, allowSetters = true)
    private Set<Services> childServices = new HashSet<>();

    @OneToMany(mappedBy = "services")
    @JsonIgnoreProperties(value = { "hotel", "services" }, allowSetters = true)
    private Set<HotelServices> hotelServices = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "childServices", "hotelServices", "parentService", "serviceRequests" }, allowSetters = true)
    private Services parentService;

    @OneToMany(mappedBy = "services")
    @JsonIgnoreProperties(value = { "parkingAll", "deliveryRequestPlace", "productRequests", "services", "checkIn" }, allowSetters = true)
    private Set<ServiceRequest> serviceRequests = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Services id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Services nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return this.description;
    }

    public Services description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Services> getChildServices() {
        return this.childServices;
    }

    public void setChildServices(Set<Services> services) {
        if (this.childServices != null) {
            this.childServices.forEach(i -> i.setParentService(null));
        }
        if (services != null) {
            services.forEach(i -> i.setParentService(this));
        }
        this.childServices = services;
    }

    public Services childServices(Set<Services> services) {
        this.setChildServices(services);
        return this;
    }

    public Services addChildService(Services services) {
        this.childServices.add(services);
        services.setParentService(this);
        return this;
    }

    public Services removeChildService(Services services) {
        this.childServices.remove(services);
        services.setParentService(null);
        return this;
    }

    public Set<HotelServices> getHotelServices() {
        return this.hotelServices;
    }

    public void setHotelServices(Set<HotelServices> hotelServices) {
        if (this.hotelServices != null) {
            this.hotelServices.forEach(i -> i.setServices(null));
        }
        if (hotelServices != null) {
            hotelServices.forEach(i -> i.setServices(this));
        }
        this.hotelServices = hotelServices;
    }

    public Services hotelServices(Set<HotelServices> hotelServices) {
        this.setHotelServices(hotelServices);
        return this;
    }

    public Services addHotelServices(HotelServices hotelServices) {
        this.hotelServices.add(hotelServices);
        hotelServices.setServices(this);
        return this;
    }

    public Services removeHotelServices(HotelServices hotelServices) {
        this.hotelServices.remove(hotelServices);
        hotelServices.setServices(null);
        return this;
    }

    public Services getParentService() {
        return this.parentService;
    }

    public void setParentService(Services services) {
        this.parentService = services;
    }

    public Services parentService(Services services) {
        this.setParentService(services);
        return this;
    }

    public Set<ServiceRequest> getServiceRequests() {
        return this.serviceRequests;
    }

    public void setServiceRequests(Set<ServiceRequest> serviceRequests) {
        if (this.serviceRequests != null) {
            this.serviceRequests.forEach(i -> i.setServices(null));
        }
        if (serviceRequests != null) {
            serviceRequests.forEach(i -> i.setServices(this));
        }
        this.serviceRequests = serviceRequests;
    }

    public Services serviceRequests(Set<ServiceRequest> serviceRequests) {
        this.setServiceRequests(serviceRequests);
        return this;
    }

    public Services addServiceRequest(ServiceRequest serviceRequest) {
        this.serviceRequests.add(serviceRequest);
        serviceRequest.setServices(this);
        return this;
    }

    public Services removeServiceRequest(ServiceRequest serviceRequest) {
        this.serviceRequests.remove(serviceRequest);
        serviceRequest.setServices(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Services)) {
            return false;
        }
        return id != null && id.equals(((Services) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Services{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
