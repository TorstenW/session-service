/**
 * 
 */
package net.wmann.session.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.wmann.session.entity.SessionEntity;

public interface SessionRepository extends JpaRepository<SessionEntity, String> {

}
