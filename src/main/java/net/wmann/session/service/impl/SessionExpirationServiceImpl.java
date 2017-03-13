/**
 * 
 */
package net.wmann.session.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.wmann.session.entity.SessionEntity;
import net.wmann.session.repository.SessionRepository;
import net.wmann.session.service.SessionExpirationService;

@Component
@Slf4j
public class SessionExpirationServiceImpl implements SessionExpirationService {
	
	@Autowired
	private SessionRepository sessionRepository;
	
	@Value(value = "${sessionservice.session.timeout}")
	private long sessionTimeout;
	
	@Scheduled(fixedRate = 60000, initialDelay = 5000)
	@Transactional
	public void removeExpiredSessions() {
		long sessionsCount = sessionRepository.count();
		List<SessionEntity> sessions = sessionRepository.findAll();
		sessions = sessions.stream().filter(s -> s.getCreatedDate().plusHours(sessionTimeout).isBefore(LocalDateTime.now())).collect(Collectors.toList());
		sessionRepository.delete(sessions);
		log.info("Deleted {} sessions from db", sessionsCount - sessionRepository.count());
	}

}
