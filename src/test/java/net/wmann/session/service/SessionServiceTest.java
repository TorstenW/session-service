/**
 * 
 */
package net.wmann.session.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import net.wmann.session.entity.SessionEntity;
import net.wmann.session.exception.SessionNotFoundException;
import net.wmann.session.repository.SessionRepository;
import net.wmann.session.service.impl.SessionServiceImpl;
import net.wmann.session.testutil.Testutils;

@RunWith(MockitoJUnitRunner.class)
public class SessionServiceTest {

	@Mock
	SessionRepository sessionRepository;

	@InjectMocks
	private SessionServiceImpl sessionService;

	private SessionEntity session;

	@Before
	public void setup() throws Exception {
		session = Testutils.createSessionEntityWithCreatedDate();
		when(sessionRepository.findOne(session.getId())).thenReturn(session);
		when(sessionRepository.save(any(SessionEntity.class))).thenReturn(session);
		when(sessionRepository.exists(session.getId())).thenReturn(true);
	}

	@Test
	public void shouldFindSessionById() throws Exception {
		SessionEntity returnedSession = sessionService.findSessionById(session.getId());
		Assert.assertEquals("Sessions do not match", session, returnedSession);
	}

	@Test
	public void shouldNotFindSessionByIdBadInput() throws Exception {
		try {
			sessionService.findSessionById(null);
			Assert.assertTrue("Test should not reach this point", false);
		} catch (IllegalArgumentException e) {
			// empty
		} catch (Exception e) {
			Assert.assertTrue("Test should not reach this point", false);
		}
	}

	@Test
	public void shouldNotFindSessionByIdNotFound() throws Exception {
		try {
			sessionService.findSessionById("invalidId");
			Assert.assertTrue("Test should not reach this point", false);
		} catch (SessionNotFoundException e) {
			// empty
		} catch (Exception e) {
			Assert.assertTrue("Test should not reach this point", false);
		}
	}

	@Test
	public void shouldStoreNewSession() throws Exception {
		SessionEntity testSession = Testutils.createSessionEntityWithCreatedDate();
		testSession.setId(null);
		when(sessionRepository.save(any(SessionEntity.class))).thenReturn(testSession);
		
		SessionEntity returnedSession = sessionService.storeSession(testSession);
		Assert.assertNotNull("Session Id should not be null", returnedSession.getId());
	}
	
	@Test
	public void shouldUpdateSession() throws Exception {
		SessionEntity returnedSession = sessionService.storeSession(session);
		Assert.assertEquals("Sessions do not match", session, returnedSession);
	}
	
	@Test
	public void shouldNotUpdateSessionNotFound() throws Exception {
		try {
			sessionService.storeSession(Testutils.createSessionEntityWithCreatedDate());
			Assert.assertTrue("Test should not reach this point", false);
		} catch (SessionNotFoundException e) {
			// empty
		} catch (Exception e) {
			Assert.assertTrue("Test should not reach this point", false);
		}
	}

}
