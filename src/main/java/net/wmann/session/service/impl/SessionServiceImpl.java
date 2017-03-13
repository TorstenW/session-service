/**
 * 
 */
package net.wmann.session.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import net.wmann.session.entity.SessionEntity;
import net.wmann.session.exception.SessionNotFoundException;
import net.wmann.session.repository.SessionRepository;
import net.wmann.session.service.SessionService;

@Service
@Slf4j
public class SessionServiceImpl implements SessionService {
	
	@Autowired
	private SessionRepository sessionRepository;
	
	@Override
	public SessionEntity findSessionById(String sessionId) {
		log.debug("Get session with id: {}", sessionId);
		if(sessionId == null || sessionId.isEmpty()) {
			throw new IllegalArgumentException("sessionId is not valid: " + sessionId);
		}
		SessionEntity session = sessionRepository.findOne(sessionId);
		if(session != null) {
			return session;
		}
		throw new SessionNotFoundException();
	}	
	
	@Override
	public SessionEntity storeSession(SessionEntity session) {
		log.debug("Store session: {}", session);
		if(session == null) {
			throw new IllegalArgumentException("session must not be null: " + session);
		}
		if(session.getId() == null || session.getId().isEmpty()) {
			session.setId(UUID.randomUUID().toString());
			log.debug("SessionId: {}", session.getId());
		} else if(!sessionRepository.exists(session.getId())) {
			throw new SessionNotFoundException();
		}
		return sessionRepository.save(session);		
	}

}
