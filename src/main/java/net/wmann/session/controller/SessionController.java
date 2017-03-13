/**
 * 
 */
package net.wmann.session.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import net.wmann.session.entity.SessionEntity;
import net.wmann.session.service.SessionService;

@RestController
@Slf4j
public class SessionController {

	@Autowired
	private SessionService sessionService;

	@RequestMapping(value = "/session/{sessionid}", method = RequestMethod.GET)
	public SessionEntity getSession(@PathVariable(name = "sessionid") String sessionId) {
		log.info("Received GET request to /session with sessionid={}", sessionId);
		SessionEntity result = sessionService.findSessionById(sessionId);
		log.debug("Returning: {}", result);
		return result;
	}

	@RequestMapping(value = "/session", method = RequestMethod.POST)
	public SessionEntity postSession(@RequestBody SessionEntity session) {
		log.info("Received POST request to /session with session={}", session);
		SessionEntity result = sessionService.storeSession(session);
		log.debug("Returning: {}", result);
		return result;
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IllegalArgumentException.class)
	public String illegalArgumentException(IllegalArgumentException e) {
		return e.getMessage(); 
	}
}
