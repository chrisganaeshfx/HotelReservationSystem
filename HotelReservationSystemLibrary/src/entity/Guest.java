/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


@Entity
public class Guest extends Customer implements Serializable {

    @NotNull(message = "Name cannot be null")
    @Column(nullable = false)
    private String name;
    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    @Column(nullable = false, unique = true)
    private String email;

    public Guest() {
    }

    public Guest(String name, String email, String username, String password) {
        super(username, password);
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (super.getCustomerId() != null ? super.getCustomerId().hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "entity.Guest[ id=" + super.getCustomerId() + " ]";
    }


}
