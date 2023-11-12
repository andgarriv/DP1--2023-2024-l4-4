package us.l4_4.dp1.end_of_line.admin;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import us.l4_4.dp1.end_of_line.model.User;

@Getter
@Setter
@Entity
@Table(name = "admins")
public class Admin extends User{
    
}
