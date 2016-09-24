/*
 * Created by Leedeper on Aug 25, 2016.
 * All rights reserved.
 */
package com.leedeper.fixsimultor;
/**
 * Define how to send message.
 * @author Leedeper
 *
 */
public interface IMessageStrategy<M> {
	public void setMessageIterator(IMessageIterator<M> msgIterator);
	public void setDistributor(IDistributor<M> distributor);
	public void setDataStore(IDataStore<M> dataStore);
	public IDataStore<M> getDataStore();
	public void handle(Data<M> data);
	public IMessageIterator<M> getMessageIterator();
	public IDistributor<M> getDistributor();
}

