package us.l4_4.dp1.end_of_line.authorities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import us.l4_4.dp1.end_of_line.model.BaseEntity;

@Getter
@Setter
@Entity
@Table(name = "authorities")
public class Authorities extends BaseEntity{
	
	@Column(length = 20)
	String authority;
}
