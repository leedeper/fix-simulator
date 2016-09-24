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
public interface IEngine <M>  {
	public void add(ITrigger<M> simulator);
	public ITrigger<M> findTrigger(String id);
	public void add(ISession<M> session);
	public ISession<M> findSession(String name);
}

