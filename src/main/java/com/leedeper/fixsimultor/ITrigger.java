/*
 * Created by Leedeper on Aug 21, 2016.
 * All rights reserved.
 */
package com.leedeper.fixsimultor;
/**
 * Trigger is a simulator builder. <br>
 * When message comes, trigger builds simulator dynamically.
 * @author Leedeper
 *
 */
public interface ITrigger<M>  {
	public ITrigger<M> watchSession(@SuppressWarnings("unchecked") ISession<M>...session);
	
	public ITrigger<M> whenLogon();
	public ITrigger<M> whenLogout();
	public ITrigger<M> whenMsgType(String ...types);
	public ITrigger<M> when(ICondition<M> condition);
	
	public ITrigger<M> setMessageIteratorFactory(IMessageIteratorFactory msgIteratorFactory);
	public ITrigger<M> setMessageStrategyFactory(IMessageStrategyFactory msgStrategyFactory);
	public ITrigger<M> setDistributor(IDistributor<M> distributor);
	public ITrigger<M> setIdCreater(IIdCreater<M> creater);
	public  ITrigger<M> setDataStoreFactory(IDataStoreFactory<M> dataStoreFactory);
	public ITrigger<M> setFlash(boolean isFlash);
	
	public ISession<M>[] getWatchSession();
	public ICondition<M> getCondition();
	public IMessageIteratorFactory getMessageIteratorFactory();
	public IMessageStrategyFactory getMessageStrategyFactory();
	public IDataStoreFactory<M> getDataStoreFactory();
	public IDistributor<M> getDistributor();
	public IIdCreater<M> getIdCreater();
	public boolean isFlash();

	

}
