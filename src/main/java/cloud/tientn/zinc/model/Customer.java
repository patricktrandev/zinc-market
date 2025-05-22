package cloud.tientn.zinc.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(indexes = {
        @Index(name = "user_username",columnList = "username"),
        @Index(name = "user_email",columnList = "email")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    private String role;
    private String address;
    private Double expenditure;
    private String membership;

    @OneToMany(mappedBy = "customer", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Order> orders= new ArrayList<>();

    public int getNumberTotalOrder(){
        return this.orders.size();
    }

    public int getNumberOfRoles(){
        String[] roles= this.role.split(" ");
        return roles.length;
    }

}
