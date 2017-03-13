/**
 * 
 */
package net.wmann.session.testutil;

import java.time.LocalDateTime;
import java.util.UUID;

import net.wmann.session.entity.SessionEntity;

public class Testutils {
	
	public static SessionEntity createSessionEntity() {
		SessionEntity session = new SessionEntity();
		session.setId(UUID.randomUUID().toString());
		session.setUsername("testuser");
		session.setEmail("testuser@testmail.com");
		session.setPhone("0199888777");
		
		return session;
	}
	
	public static SessionEntity createSessionEntityWithCreatedDate() {
		SessionEntity session = createSessionEntity();
		session.setCreatedDate(LocalDateTime.now());
		
		return session;
	}

}
