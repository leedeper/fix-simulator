/*
 * Created by Leedeper on Aug 28, 2016.
 * All rights reserved.
 */
package com.leedeper.fixsimultor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * @author Leedeper
 *
 */
public class SimulatorEngine<M> {
	private static final Logger logger = LoggerFactory.getLogger(SimulatorEngine.class);
	private Register register=new Register();
	private ISimualtorFactory simualtorFactory=null;
	private Map<String,ISimulator<M>> allSimulator=new LinkedHashMap<>();
	public void setSimualtorFactory(ISimualtorFactory simualtorFactory){
		this.simualtorFactory=simualtorFactory;
	}
	public void registerTrigger(ITrigger<M> trigger) throws SimulatorException{
		check(trigger);
		sedDefaultIfNotSet(trigger);
		// add
		register.register(trigger);
	}
	private void check(ITrigger<M> trigger) throws SimulatorException{
		if(register.contains(trigger)){
			throw new SimulatorException("Trigger exists already. Can't register again.");
		}
		if(trigger.getCondition()==null){
			throw new SimulatorException("No condition was set.");
		}
		ISession<M>[] sessions = trigger.getWatchSession();
		if(sessions!=null ){
			for(int i=0;i<sessions.length;i++){
				for(int j=i+1;j<sessions.length;j++){
					if(sessions[i]==sessions[j] || sessions[i].equals(sessions[j])){
						throw new SimulatorException("Reduplicative session.");
					}
				}
			}
		}
	}
	private void sedDefaultIfNotSet(ITrigger<M> trigger){
/*		if(trigger.getMessageIteratorFactory()==null){
			logger.info("Set default message iterator factory : {}",trigger.getClass().getName());
			trigger.setMessageIteratorFactory(DefaultAction.messageIteratorFactory());
		}*/
		if(trigger.getMessageStrategyFactory()==null){
			logger.info("Set default message strategy factory : {}",trigger.getClass().getName());
			trigger.setMessageStrategyFactory(DefaultAction.messageStrategyFactory());
		}
/*		if(trigger.getDataStoreFactory()==null){
			logger.info("Set default history data factory : {}",trigger.getClass().getName());
			trigger.setHistoryDataFactory(DefaultAction.historyDataFactory());
		}*/
		if(trigger.getIdCreater()==null){
			logger.info("Set default id creater : {}",trigger.getClass().getName());
			trigger.setIdCreater(DefaultAction.idCreater());
		}
	}
	public ISimulator<M> findSimulator(String id){
		return null;
	}
	public void add(ISession<M> session){
		
	}
	public void handle(Data<M> data){
		List<ITrigger<M>> triggers = register.getTrigger(data.getFromSession());
		logger.debug("Find {}",triggers.size());
		for(ITrigger<M> trigger:triggers){
			ICondition<M> condition=trigger.getCondition();
			if(!(condition instanceof ICondition.LogCondition ) 
					&& condition.match(data.getMessage())){
				logger.info("Handle by {}",trigger.getClass().getName());
				handleMatchMsg(data,trigger);
			}
		}

	}
	public void handleLogin(ISession<M> session){
		// TODO xxx
	}
	public void handleLogout(ISession<M> session){
		// TODO xxx
	}
	

	private void handleMatchMsg(Data<M> data, ITrigger<M> trigger) {
		if (trigger.getMessageIteratorFactory() == null) {
			// no iterator, handle nothing
			logger.warn("No Message iterator, so do nothing");
			return;
		}

		if(trigger.isFlash()){
			ISimulator<M> simulator = newSimulator(trigger,null);
			simulator.go();
			simulator.add(data);
			return;
		}
		String id = trigger.getIdCreater().create(data);
		ISimulator<M> simulator = allSimulator.get(id);
		if (simulator == null) {
			simulator = newSimulator(trigger,id);
			allSimulator.put(id, simulator);
			simulator.go();
		}
		simulator.add(data);

	}
	private ISimulator<M> newSimulator(ITrigger<M> trigger,String id){
		ISimulator<M> simulator = simualtorFactory.newSimulator();
		simulator.setCondition(trigger.getCondition());
		simulator.setId(id);
		IMessageStrategy msgStrategy=trigger.getMessageStrategyFactory().getMessageStrategy();
		msgStrategy.setMessageIterator(trigger.getMessageIteratorFactory().getMessageIterator());
		msgStrategy.setDistributor(trigger.getDistributor());
		msgStrategy.setDataStore(trigger.getDataStoreFactory().newDataStore());
		simulator.setMessageStrategy(msgStrategy);

		return simulator;
	}
	public ISimulator<M> removeSimulator(String id){
		return allSimulator.remove(id);
	}

	public ISession<M> findSession(String name){
		return null;
	}

	
	private class Register{
		private List<ITrigger<M>> allTrigger=new ArrayList<>();
		private List<ITrigger<M>> triggerForAllSession=new ArrayList<>();
		private Map<ISession<M>,List<ITrigger<M>>> sessionTrigger=new HashMap<>();
		private final ArrayList<ITrigger<M>> EMPTY_LIST= new ArrayList<>(0); 
		synchronized void  register(ITrigger<M> trigger){
			allTrigger.add(trigger);
			// relationship between session -> trigger
			ISession<M>[] sessions = trigger.getWatchSession();
			if(sessions==null || sessions.length==0){
				triggerForAllSession.add(trigger);
			}else{
				for(ISession<M> s:sessions){
					List<ITrigger<M>> list = sessionTrigger.get(s);
					if(list==null){
						list=new ArrayList<>();
						list.add(trigger);
						sessionTrigger.put(s,list);
					}else{
						list.add(trigger);
					}
				}
			}
		}
		boolean contains(ITrigger<M> trigger){
			return allTrigger.contains(trigger);
		}
		List<ITrigger<M>> getTrigger(ISession<M> session){
			List<ITrigger<M>> list = sessionTrigger.get(session);
			if(list==null && triggerForAllSession.size()==0){
				return EMPTY_LIST;
			}
			if(list==null){
				return new ArrayList<ITrigger<M>>(triggerForAllSession);  
			}
			if(triggerForAllSession.size()==0){
				return new ArrayList<ITrigger<M>>(list);  
			}
			int total=triggerForAllSession.size()+list.size();
			ArrayList<ITrigger<M>> all=new ArrayList<>(total);
			all.addAll(triggerForAllSession);
			all.addAll(list);
			return all;
		}
	}
}

