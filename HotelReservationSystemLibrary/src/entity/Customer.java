package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


@Entity
public class Customer extends Guest implements Serializable {

    @Column(nullable = false)
    private String name;
    @Column(nullable = false,unique = true)
    private String email;

    public Customer() {
    }

    public Customer(String name, String email, String username, String password) {
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
        hash += (super.getGuestId() != null ? super.getGuestId().hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "entity.Customer[ id=" + super.getGuestId() + " ]";
    }


}
