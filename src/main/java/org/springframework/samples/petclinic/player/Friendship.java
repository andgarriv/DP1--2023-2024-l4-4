package org.springframework.samples.petclinic.player;

import org.springframework.samples.petclinic.model.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "friendships")
public class Friendship extends BaseEntity{

    @NotNull
    @ManyToOne(cascade = CascadeType.REFRESH, optional = false)
    Player sender;

    @NotNull
    @ManyToOne(cascade = CascadeType.REFRESH, optional = false)
    Player receiver;

    @NotNull
    @Enumerated(EnumType.STRING)
    FriendStatus friendStatus;
}
