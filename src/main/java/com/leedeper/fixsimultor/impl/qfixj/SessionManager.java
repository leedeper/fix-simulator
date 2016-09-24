/*
 * Created by Leedeper on Aug 27, 2016.
 * All rights reserved.
 */
package com.leedeper.fixsimultor.impl.qfixj;

import java.io.FileInputStream;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import javax.management.JMException;
import javax.management.ObjectName;

import org.quickfixj.jmx.JmxExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leedeper.fixsimultor.ISession;
import com.leedeper.fixsimultor.ITrigger;
import com.leedeper.fixsimultor.SimulatorEngine;
import com.leedeper.fixsimultor.SimulatorException;
import com.leedeper.fixsimultor.SimulatorRuntimeException;

import quickfix.ConfigError;
import quickfix.Connector;
import quickfix.Group;
import quickfix.LogFactory;
import quickfix.MemoryStoreFactory;
import quickfix.Message;
import quickfix.MessageFactory;
import quickfix.MessageStoreFactory;
import quickfix.RuntimeError;
import quickfix.SLF4JLogFactory;
import quickfix.Session;
import quickfix.SessionFactory;
import quickfix.SessionID;
import quickfix.SessionSettings;
import quickfix.SocketAcceptor;
import quickfix.SocketInitiator;
import quickfix.field.MsgType;

/**
 * 
 * @author Leedeper
 *
 */
public class SessionManager {
	private final Logger logger = LoggerFactory.getLogger(SessionManager.class);
	private ArrayList<Connector> allConnector = new ArrayList<>();
	private SessionRegister register=new SessionRegister();
	public SessionManager(SimulatorEngine engine,Properties variables, String... configPath) 
			throws SimulatorException {
		FixApplication application = new FixApplication(this,engine);
		for (String f : configPath) {
			logger.info("Init from config path : {}", f);
			createSession(f, application, variables);
		}
	}
	
	public void start() {
		boolean allStarted=true;
		for(Connector connector:allConnector){
			try {
				connector.start();
			} catch (Exception e) {
				logger.error("Start sesison error ,",e);
				allStarted=false;
				break;
			} 
		}
		if(!allStarted){
			this.stop();
		}
	}

	private void createSession(String cfgPath, FixApplication application, Properties variables) 
			throws SimulatorException {
		SessionSettings settings = null;
		InputStream in = null;
		try {
			in = new FileInputStream(cfgPath);
			settings = new SessionSettings(in);
		} catch (Exception e) {
			throw new SimulatorException(e);
		}

		settings.setVariableValues(variables);

		MessageStoreFactory messageStoreFactory = new MemoryStoreFactory();

		MessageFactory messageFactory = newMessageFactory();
		LogFactory logFactory = new SLF4JLogFactory(settings);

		Connector connector = null;
		try {
			register(settings);
			setNoPersistMessagesWhenMemoryType(settings, messageStoreFactory);
			// Use ConnectionType of [default] define these session type.
			String connectionType = settings.getString(SessionFactory.SETTING_CONNECTION_TYPE);
			if (connectionType.equals(SessionFactory.INITIATOR_CONNECTION_TYPE)) {
				connector = new SocketInitiator(application, messageStoreFactory, settings, logFactory, messageFactory);
			} else {
				connector = new SocketAcceptor(application, messageStoreFactory, settings, logFactory, messageFactory);
			}
			
			allConnector.add(connector);
			try {
				JmxExporter exporter = new JmxExporter();
				ObjectName connectorObjectName = exporter.register(connector);
				logger.info("Registered  jmx object name for fix : {}-{}", connectorObjectName.getDomain(),
						connectorObjectName.getCanonicalName());
			} catch (JMException e) {
				logger.warn("", e);
			}
		} catch (Exception e) {
			logger.error("", e);
			throw new SimulatorException("Can't init session ", e);
		}
		

	}
	private void register(SessionSettings settings) throws ConfigError, SimulatorException{
		Iterator<SessionID> it = settings.sectionIterator();
		while (it.hasNext()) {
			SessionID sessionId = it.next();
			String name=settings.getSessionProperties(sessionId).getProperty("AliasName");
			if(name==null){
				throw new SimulatorException("No set AliasName for "+sessionId.toString());
			}
			register.register(name, sessionId);
		}
	
	}

	// avoid OOM
	private void setNoPersistMessagesWhenMemoryType(SessionSettings settings, MessageStoreFactory messageStoreFactory)
			throws ConfigError {
		if (messageStoreFactory instanceof MemoryStoreFactory) {
			Iterator<SessionID> it = settings.sectionIterator();
			while (it.hasNext()) {
				SessionID sessionId = it.next();
				settings.getSessionProperties(sessionId).setProperty(Session.SETTING_PERSIST_MESSAGES, "N");
			}
		}
	}

	private MessageFactory newMessageFactory() {
		return new MessageFactory() {
			@Override
			public Message create(String beginString, String msgType) {
				Message message = new Message();
				message.getHeader().setString(MsgType.FIELD, msgType);
				return message;
			}

			@Override
			public Group create(String beginString, String msgType, int correspondingFieldID) {
				//No use
				throw new IllegalArgumentException("Unsupported FIX version: " + beginString + ", msgType:" + msgType);
			}

		};
	}

	public void stop() {
		logger.info("stop {}", this.getClass().getName());
		for (Connector connector : allConnector) {
			connector.stop();
		}
	}

	public ISession<Message> getSession(SessionID sessionId){
		return register.getSession(sessionId);
	} 
	public ISession<Message> getSession(String name){
		return register.getSession(name);
	}
	public class SessionRegister {
		private  final Logger logger = LoggerFactory.getLogger(SessionRegister.class);
		private  HashMap<String,ISession<Message>> nameSession=new HashMap<>();
		private  HashMap<SessionID,ISession<Message>> idSsession=new HashMap<>();
		public  void register(String name, SessionID sessionId){
			if(nameSession.containsKey(name) || idSsession.containsKey(sessionId)){
				throw new SimulatorRuntimeException("Duplicate Alias Name "+name);
			}
			logger.info("Register QuickFix Session {}->{}",name,sessionId);
			ISession<Message> session=new QSession(name,sessionId);
			nameSession.put(name, session);
			idSsession.put(sessionId, session);
		}
		public ISession<Message> getSession(SessionID sessionId){
			return idSsession.get(sessionId);
		}
		public ISession<Message> getSession(String name){
			return nameSession.get(name);
		}
	
	}
}
