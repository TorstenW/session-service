/**
 * 
 */
package net.wmann.session.service;

import net.wmann.session.entity.SessionEntity;

public interface SessionService {
	
	
	/**
	 * @param sessionId - The id of the session we want to retrieve
	 * @return The session with the correct sessionId if it exists
	 */
	SessionEntity findSessionById(String sessionId);
	
	/**
	 * Stores a new session to the database after creating an id for it.
	 * <br><br>
	 * Updates a session in the database if the sessionId in the parameter
	 * is already stored in the database
	 * 
	 * @param session - The session we want to save to the database
	 * @return The updated session after it was saved in the database 
	 */
	SessionEntity storeSession(SessionEntity session);

}
