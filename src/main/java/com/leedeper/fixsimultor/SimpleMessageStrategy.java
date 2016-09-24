/*
 * Created by Leedeper on Sep 24, 2016.
 * All rights reserved.
 */
package com.leedeper.fixsimultor;
/**
 * 
 * @author Leedeper
 *
 */
public class SimpleMessageStrategy<M> implements IMessageStrategy<M>{
	private IMessageIterator<M> msgIterator=null;
	private IDistributor<M> distributor=null;
	private IDataStore<M> dataStore=null;
	@Override
	public void setMessageIterator(IMessageIterator<M> msgIterator) {
		this.msgIterator=msgIterator;
	}


	@Override
	public void setDistributor(IDistributor<M> distributor) {
		this.distributor=distributor;
		
	}

	@Override
	public IMessageIterator<M> getMessageIterator() {
		return this.msgIterator;
	}


	@Override
	public IDistributor<M> getDistributor() {
		return this.distributor;
	}
	
	@Override
	public void handle(Data<M> data) {
		while(msgIterator.hasNext()){
			M msg=msgIterator.next(data);
			distributor.send(msg,data);
		}
		
	}

	@Override
	public void setDataStore(IDataStore<M> dataStore) {
		this.dataStore=dataStore;
		
	}

	@Override
	public IDataStore<M> getDataStore() {
		return this.dataStore;
	}
}

