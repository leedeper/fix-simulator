/*
 * Created by Leedeper on Sep 23, 2016.
 * All rights reserved.
 */
package com.leedeper.fixsimultor;
/**
 * 
 * @author Leedeper
 * @param <M>
 *
 */
public class Simulator<M> implements ISimulator<M> {

	private String id=null;
	private ICondition<M> condition=null;
	private IMessageStrategy<M> msgStrategy=null;
	@Override
	public void setId(String id) {
		this.id=id;
	}

	@Override
	public void setCondition(ICondition<M> condition) {
		this.condition=condition;
		
	}


	@Override
	public void setMessageStrategy(IMessageStrategy msgStrategy) {
		this.msgStrategy=msgStrategy;
		
	}


	@Override
	public String getId() {
		return id;
	}



	@Override
	public ICondition<M> getCondition() {
		return this.condition;
	}


	@Override
	public IMessageStrategy<M> getMessageStrategy() {
		return this.msgStrategy;
	}

	@Override
	public void go() {
		
	}
	
	@Override
	public void add(Data<M> data) {
		this.msgStrategy.handle(data);
	}


}

