/*
 * Created by Leedeper on Aug 27, 2016.
 * All rights reserved.
 */
package com.leedeper.fixsimultor;
/**
 * 
 * @author Leedeper
 *
 */
public class BroadDistributor<M> implements IDistributor<M> {
	private ISession [] sessions=null;
	public BroadDistributor(ISession ...sessions){
		this.sessions=sessions;
	}
	public void send(M msg,Data<M> data){
		for(ISession session:sessions){
			session.send(msg);
			data.addTargetSession(session);
		}
		
	}
}

