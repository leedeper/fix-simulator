/*
 * Created by Leedeper on Sep 22, 2016.
 * All rights reserved.
 */
package com.leedeper.fixsimultor.impl.qfixj.simple;

import com.leedeper.fixsimultor.BroadDistributor;
import com.leedeper.fixsimultor.DataStoreFactory;
import com.leedeper.fixsimultor.ISession;
import com.leedeper.fixsimultor.ITrigger;
import com.leedeper.fixsimultor.IntervalMessageStrategyFactory;
import com.leedeper.fixsimultor.SimulatorFactory;
import com.leedeper.fixsimultor.SimulatorEngine;
import com.leedeper.fixsimultor.SimulatorException;
import com.leedeper.fixsimultor.impl.qfixj.FromReqSimulation;
import com.leedeper.fixsimultor.impl.qfixj.IncreaseSimulation;
import com.leedeper.fixsimultor.impl.qfixj.JexlSimulation;
import com.leedeper.fixsimultor.impl.qfixj.MessageIteratorFactory;
import com.leedeper.fixsimultor.impl.qfixj.QTrigger;
import com.leedeper.fixsimultor.impl.qfixj.RandomEnumSimulation;
import com.leedeper.fixsimultor.impl.qfixj.RandomSimulation;
import com.leedeper.fixsimultor.impl.qfixj.SessionManager;
import com.leedeper.fixsimultor.impl.qfixj.SimulationField;
import com.leedeper.fixsimultor.impl.qfixj.UniqueFromReqIdCreater;

import quickfix.Group;
import quickfix.Message;
import quickfix.field.MsgType;

/**
 * 
 * @author Leedeper
 *
 */
public class Starter {

	/**
	 * @param args
	 * @throws SimulatorException 
	 */
	public static void main(String[] args) throws SimulatorException {

		// 1. create the engine
		SimulatorEngine engine=new SimulatorEngine();
		engine.setSimualtorFactory(new SimulatorFactory());
		
		SessionManager sessionManager=new SessionManager(engine
				,System.getProperties()
				,"./config/quickfix.cfg");
		
		// 2. create the trigger
		ISession<Message> session=sessionManager.getSession("Price");
		ITrigger trigger1=new QTrigger();
		trigger1.watchSession(session)
		.whenMsgType("V")
		.setFlash(false)
		.setIdCreater(new UniqueFromReqIdCreater(262))
		.setMessageStrategyFactory(new IntervalMessageStrategyFactory(2000))
		.setMessageIteratorFactory(new MessageIteratorFactory(getMarketDataTmpl()))
		.setDataStoreFactory(new DataStoreFactory(10))
		.setDistributor(new BroadDistributor(session));
		
		// 3. register trigger to engine
		engine.registerTrigger(trigger1);
		
		sessionManager.start();
		while(true)
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

	}
	private static Message getMarketDataTmpl(){
		// request message as 
		// 8=FIX.4.39=12035=V34=249=CLIENT52=20160925-10:20:11.03256=CLIENT_PRICE262=REQ_001263=1264=2146=155=AUD/USD267=2269=0269=110=003
		Message msg=new Message();
		msg.getHeader().setField(new MsgType("W"));
		RandomEnumSimulation.set(55, msg, "AUD","EUR","USD");
		FromReqSimulation.copyFromReqMsg(262, msg);
		
		//hypothetical condition, just show how to use experssion
		msg.setField(291, SimulationField.newField(291,new JexlSimulation("size(getGroups(267))==2?'2':'1'")));
		Group g=new Group(268,0);
		g.setString(269, "0");
		//TODO I will simplify it setFiled(xx,yy(xx,zz))
		g.setField(270,SimulationField.newField(270, new RandomSimulation("0.71777","0.71888")));
		g.setDouble(271, 5000000);
		msg.addGroup(g);
		
		g=new Group(268,0);
		g.setString(269, "1");
		g.setField(270, SimulationField.newField(270,new IncreaseSimulation("0.71916")));
		g.setDouble(271, 5000000);
		msg.addGroup(g);
		return msg;
	} 

}

