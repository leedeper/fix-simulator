/*
 * Created by Leedeper on Aug 30, 2016.
 * All rights reserved.
 */
package com.leedeper.fixsimultor;
/**
 * 
 * @author Leedeper
 *
 */
public interface ISimulator<M> {
	
	public void setId(String id);
	public void add(Data<M> data);
	public void setCondition(ICondition<M> condition);
	public void setMessageStrategy(IMessageStrategy msgStrategy);

	public String getId();
	

	public ICondition<M> getCondition();
	public IMessageStrategy getMessageStrategy();
	public void go();
}

