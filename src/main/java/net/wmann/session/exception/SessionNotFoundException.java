/**
 * 
 */
package net.wmann.session.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No session with this id exists")
public class SessionNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 3712687083655800117L;

}
