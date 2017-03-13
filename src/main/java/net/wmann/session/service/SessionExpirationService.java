/**
 * 
 */
package net.wmann.session.service;

public interface SessionExpirationService {
	
	/**
	 * Removes all sessions from the database that are older than
	 * the currently valid timeout
	 */
	void removeExpiredSessions();

}
