/**
 * 
 */
package net.wmann.session.integration;

import java.time.LocalDateTime;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import net.wmann.session.Application;
import net.wmann.session.entity.SessionEntity;
import net.wmann.session.repository.SessionRepository;
import net.wmann.session.service.SessionExpirationService;
import net.wmann.session.testutil.Testutils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
@WebAppConfiguration
@TestPropertySource(properties = { "sessionservice.session.timeout=2" })
public class SessionExpirationServiceIntegrationTest {

	@Autowired
	private SessionExpirationService sessionExpirationService;

	@Autowired
	private SessionRepository sessionRepository;

	@Before
	public void setup() {
		sessionRepository.deleteAll();
		populateDb();
	}
	
	@Test
	public void shouldDeleteExpiredSessions() {
		Assert.assertEquals("Wrong number of sessions in db", 8, sessionRepository.count());
		
		sessionExpirationService.removeExpiredSessions();
		
		Assert.assertEquals("Wrong number of sessions in db after cleanup", 5, sessionRepository.count());
	}

	@After
	public void teardown() {
		sessionRepository.deleteAll();
	}

	private void populateDb() {
		for (int i = 0; i < 5; i++) {
			sessionRepository.save(Testutils.createSessionEntity());
		}
		for (int i = 0; i < 3; i++) {
			SessionEntity session = Testutils.createSessionEntity();
			session.setCreatedDate(LocalDateTime.now().minusHours(3L));
			sessionRepository.save(session);
		}
	}

}
