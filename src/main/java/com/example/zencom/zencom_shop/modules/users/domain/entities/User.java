package com.example.zencom.zencom_shop.modules.users.domain.entities;

import com.example.zencom.zencom_shop.modules.shared.domain.AggregateRoot;
import com.example.zencom.zencom_shop.modules.shared.ids.UserId;
import com.example.zencom.zencom_shop.modules.shared.security.Role;
import com.example.zencom.zencom_shop.modules.users.domain.enums.NotificationChannel;
import com.example.zencom.zencom_shop.modules.users.domain.events.UserCreatedDomainEvent;
import jakarta.persistence.Id;

import java.time.Instant;
import java.util.Set;

public class User extends AggregateRoot {

    private final UserId id;
    private String email;
    private String password;
    private Set<Role> role;
    private NotificationChannel channel;//default email
    private String phoneNumber;//optional
    private final Instant createdAt;
    private Instant updatedAt;

    private User(
            UserId id,
            String email,
            String password,
            Set<Role> role,
            NotificationChannel channel,
            String phoneNumber,
            Instant createdAt,
            Instant updatedAt){

        if(id == null) throw new IllegalArgumentException("id cannot be null");
        if(email == null) throw new IllegalArgumentException("email cannot be null");
        if(password == null) throw new IllegalArgumentException("password cannot be null");
        if(role == null) throw new IllegalArgumentException("role cannot be null");
        if(channel == null) throw new IllegalArgumentException("channel cannot be null");
        if(createdAt == null) throw new IllegalArgumentException("createdAt cannot be null");

    this.id = id;
    this.email = email;
    this.password = password;
    this.role = role;
    this.channel = channel;
    this.phoneNumber = phoneNumber;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;

    }

    public static User create(
            String email,
            String password,
            NotificationChannel channel,
            String phoneNumber
    ){
        User user = new User(
                UserId.newId(),
                email,
                password,
                Set.of(Role.CLIENT),
                channel,
                phoneNumber,
                Instant.now(),
                null
        );
        user.raise(UserCreatedDomainEvent.now(
                user.getId().getId()
        ));

        return user;
    }

    public static User restore(
            UserId id,
            String email,
            String passwordHash,
            Set<Role> role,
            NotificationChannel channel,
            String phoneNumber,
            Instant createdAt,
            Instant updatedAt
    ){
        return new User(
                id,
                email,
                passwordHash,
                role,
                channel,
                phoneNumber,
                createdAt,
                updatedAt
        );
    }

    private void touch(){
        this.updatedAt = Instant.now();
    }

    public void changePassword(String newPassword){
        this.password = newPassword;
        touch();
    }
    public void changeEmail(String newEmail){
        this.email = newEmail;
        touch();
    }
    public void changeChannel(NotificationChannel channel){
        this.channel = channel;
        touch();
    }
    public void changePhoneNumber(String newPhoneNumber){
        this.phoneNumber = newPhoneNumber;
        touch();
    }
    public void assignRole(Role role){
        if(role == null) throw new IllegalArgumentException("role cannot be null");
        if(this.role.contains(role)) return;
        this.role.add(role);
        touch();
    }

    public void removeRole(Role role){
        if(role == null) throw new IllegalArgumentException("role cannot be null");
        if(!this.role.contains(role)) {
            return;
        }
        this.role.remove(role);
        touch();
    }

    public boolean hasRole(Role role){
        if(role == null) throw new IllegalArgumentException("role cannot be null");
        return this.role.contains(role);
    }



    public UserId getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Set<Role> getRole() {
        return role;
    }

    public NotificationChannel getChannel() {
        return channel;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
