/*
 * Created by Leedeper on Aug 27, 2016.
 * All rights reserved.
 */
package com.leedeper.fixsimultor.impl.qfixj;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leedeper.fixsimultor.Data;
import com.leedeper.fixsimultor.ISession;
import com.leedeper.fixsimultor.ITrigger;
import com.leedeper.fixsimultor.SimulatorEngine;

import quickfix.Application;
import quickfix.DoNotSend;
import quickfix.FieldNotFound;
import quickfix.IncorrectDataFormat;
import quickfix.IncorrectTagValue;
import quickfix.Message;
import quickfix.RejectLogon;
import quickfix.SessionID;
import quickfix.UnsupportedMessageType;


/**
 * 
 * @author Leedeper
 *
 */
class FixApplication implements Application {
	private static final Logger logger = LoggerFactory.getLogger(FixApplication.class);
	private Map<SessionID,Boolean> status=new HashMap<SessionID,Boolean> ();
	private SessionManager sessionManager=null;
	private SimulatorEngine engine=null;
	public FixApplication(SessionManager sessionManage,SimulatorEngine engine) {
		this.sessionManager=sessionManage;
		this.engine=engine;
	}
	
	@Override
	public void fromAdmin(Message msg, SessionID sessionId) throws FieldNotFound,
			IncorrectDataFormat, IncorrectTagValue, RejectLogon {
		logger.trace("quickFix event fromAdmin {} {}",sessionId,msg);
		receive(msg,sessionId);
	}
	
	@Override
	public void fromApp(Message msg, SessionID sessionId) throws FieldNotFound,
			IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
		logger.trace("quickFix event fromApp {} {}",sessionId,msg);
		receive(msg,sessionId);
	}
	
	private void receive(Message msg, SessionID sessionId){
		ISession<Message> session = sessionManager.getSession(sessionId);
		
		engine.handle(new Data(msg,session));
		
	}
	@Override
	public void onCreate(SessionID sessionId) {
		logger.info("quickFix event onCreate : {}",sessionId);
	}

	@Override
	public void onLogon(SessionID sessionId) {
		logger.info("quickFix event onLogon: {}",sessionId);
	}

	@Override
	public void onLogout(SessionID sessionId) {
		logger.info("quickFix event onLogout: {}",sessionId);
		
	}

	@Override
	public void toAdmin(Message msg, SessionID sessionId) {
		logger.trace("quickFix event toAdmin {} {}",sessionId,msg);
		
	}

	@Override
	public void toApp(Message msg, SessionID sessionId) throws DoNotSend {
		logger.trace("quickFix event toApp {} {}",sessionId,msg);
	}

}
