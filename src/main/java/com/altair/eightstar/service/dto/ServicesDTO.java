package com.altair.eightstar.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.altair.eightstar.domain.Services} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ServicesDTO implements Serializable {

    private Long id;

    private String nom;

    private String description;

    private ServicesDTO parentService;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ServicesDTO getParentService() {
        return parentService;
    }

    public void setParentService(ServicesDTO parentService) {
        this.parentService = parentService;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServicesDTO)) {
            return false;
        }

        ServicesDTO servicesDTO = (ServicesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, servicesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServicesDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            ", parentService=" + getParentService() +
            "}";
    }
}
