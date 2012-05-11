package uk.icat3.useransto.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import uk.icat3.exceptions.SessionException;

@SuppressWarnings("serial")
@Entity
@Table(name = "USER_SESSION_ANSTO")
@NamedQuery(name = "AnstoSession.findByUserSessionId", query = "SELECT s FROM Session s WHERE s.userSessionId = :userSessionId")
public class Session implements Serializable {

	@Id
	@Column(name = "USER_SESSION_ID")
	private String userSessionId;

	@Column(name = "EXPIRE_DATE_TIME", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date expireDateTime;

	@Column(name = "RUN_AS", nullable = false)
	private String runAs;

	// Needed by JPA
	public Session() {
	}

	public void checkValid() throws SessionException {
		if (expireDateTime.before(new Date()))
			throw new SessionException("Session id:" + getUserSessionId() + " has expired");
	}

	public Session(String userSessionId, String runAs, Date expireDateTime) {
		this.userSessionId = userSessionId;
		this.runAs = runAs;
		this.expireDateTime = expireDateTime;
	}

	public String getUserSessionId() {
		return this.userSessionId;
	}

	public String toString() {
		return runAs + (expireDateTime.before(new Date()) ? " expired" : " valid");
	}

	public String getRunAs() {
		return runAs;
	}

}