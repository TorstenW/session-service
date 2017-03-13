/**
 * 
 */
package net.wmann.session.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(exclude = "createdDate")
@Entity
public class SessionEntity {
	
	@Id
	private String id;
	
	private String username;
	
	private String email;
	
	private String phone;
	
	@JsonIgnore
	@Column(updatable = false)
	private LocalDateTime createdDate;
	
	@PrePersist
	private void onInsert(){
		if(createdDate == null) {
			createdDate = LocalDateTime.now();
		}
	}

}
