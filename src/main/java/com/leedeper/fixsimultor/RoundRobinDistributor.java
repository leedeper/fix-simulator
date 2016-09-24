/*
 * Created by Leedeper on Aug 28, 2016.
 * All rights reserved.
 */
package com.leedeper.fixsimultor;
/**
 * 
 * @author Leedeper
 *
 */
public class RoundRobinDistributor<M> implements IDistributor<M> {
	private ISession [] sessions=null;
	private int inx=0;
	public RoundRobinDistributor(ISession ...sessions){
		if(sessions==null || sessions.length==0){
			throw new SimulatorRuntimeException("No session is set.");
		}
		this.sessions=sessions;
	}
	public void send(M msg,Data<M> data){
		ISession s=sessions[getIndex()];
		s.send(msg);
		data.addTargetSession(s);
	}
	private synchronized int getIndex(){
		int curr=inx;
		inx++;
		if(inx==sessions.length){
			inx=0;
		}
		return curr;
	}
}


