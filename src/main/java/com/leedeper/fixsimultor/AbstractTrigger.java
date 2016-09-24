/*
 * Created by Leedeper on Aug 28, 2016.
 * All rights reserved.
 */
package com.leedeper.fixsimultor;

import com.leedeper.fixsimultor.ICondition;
import com.leedeper.fixsimultor.IDistributor;
import com.leedeper.fixsimultor.IDataStoreFactory;
import com.leedeper.fixsimultor.IIdCreater;
import com.leedeper.fixsimultor.IMessageIteratorFactory;
import com.leedeper.fixsimultor.IMessageStrategyFactory;
import com.leedeper.fixsimultor.ISession;
import com.leedeper.fixsimultor.ITrigger;


/**
 * 
 * @author Leedeper
 *
 */
public abstract class AbstractTrigger<M> implements ITrigger<M> {

	private ISession<M> session[]=null;
	private ICondition<M> condition=null;
	private IMessageIteratorFactory msgIteratorFactory=null;
	private IMessageStrategyFactory msgStrategyFactory=null;
	private IDistributor<M> distributor=null;
	private IIdCreater<M>  idCreater=null;
	private IDataStoreFactory<M> dataStoreFactory=null;
	private boolean isFlash=true;
	@Override
	public ITrigger<M> watchSession(ISession<M>... session) {
		this.session=session;
		return this;
	}


	@Override
	public ITrigger<M> whenLogon() {
		condition=ICondition.LOGIN;
		return this;
	}


	@Override
	public ITrigger<M> whenLogout() {
		condition=ICondition.LOGOUT;
		return this;
	}

	@Override
	public ITrigger<M> when(ICondition<M> condition) {
		this.condition = condition;
		return this;
	}


	@Override
	public ITrigger<M> setMessageIteratorFactory(IMessageIteratorFactory msgIteratorFactory) {
		this.msgIteratorFactory=msgIteratorFactory;
		return this;
	}


	@Override
	public ITrigger<M> setMessageStrategyFactory(IMessageStrategyFactory msgStrategyFactory) {
		this.msgStrategyFactory=msgStrategyFactory;
		return this;
	}


	@Override
	public ITrigger<M> setDistributor(IDistributor<M> distributor) {
		this.distributor=distributor;
		return this;
	}


	@Override
	public ITrigger<M> setIdCreater(IIdCreater<M> idCreater) {
		this.idCreater=idCreater;
		return this;
	}


	@Override
	public ITrigger<M> setDataStoreFactory(IDataStoreFactory<M> dataStoreFactory) {
		this.dataStoreFactory=dataStoreFactory;
		return this;
	}


	@Override
	public ITrigger<M> setFlash(boolean isFlash) {
		this.isFlash=isFlash;
		return this;
	}


	@Override
	public ISession<M>[] getWatchSession() {
		return this.session;
	}


	@Override
	public ICondition<M> getCondition() {
		return this.condition;
	}


	@Override
	public IMessageIteratorFactory getMessageIteratorFactory() {
		return this.msgIteratorFactory;
	}


	@Override
	public IMessageStrategyFactory getMessageStrategyFactory() {
		return this.msgStrategyFactory;
	}


	@Override
	public IDataStoreFactory<M> getDataStoreFactory() {
		return this.dataStoreFactory;
	}


	@Override
	public IDistributor<M> getDistributor() {
		return this.distributor;
	}


	@Override
	public IIdCreater<M> getIdCreater() {
		return this.idCreater;
	}


	@Override
	public boolean isFlash() {
		return this.isFlash;
	}



}

