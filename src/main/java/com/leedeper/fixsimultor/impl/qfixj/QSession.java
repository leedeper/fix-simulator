/*
 * Created by Leedeper on Aug 27, 2016.
 * All rights reserved.
 */
package com.leedeper.fixsimultor.impl.qfixj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leedeper.fixsimultor.ISession;

import quickfix.Message;
import quickfix.Session;
import quickfix.SessionID;


/**
 * 
 * @author Leedeper
 *
 */
public class QSession implements ISession<Message> {
	private static final Logger logger = LoggerFactory.getLogger(QSession.class);
	private String name=null;
	private SessionID sessionId=null;
	public QSession(String name,SessionID sessionId){
		this.name=name;
		this.sessionId=sessionId;
	}
	@Override
	public boolean send(Message msg) {
		Session session=null;
		if((session=Session.lookupSession(sessionId))==null){
			// not happen
			logger.error("No found session "+name);
			return false;
		}
		return session.send(msg);
	}

	@Override
	public String getName() {
		return name;
	}
}

