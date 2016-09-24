/*
 * Created by Leedeper on Aug 30, 2016.
 * All rights reserved.
 */
package com.leedeper.fixsimultor;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Leedeper
 *
 */
public class Data<M> {
	private M msg=null;
	private ISession<M> fromSession=null;
	private List<ISession<M>>  toSession=new ArrayList<>();
	public Data(M msg,ISession<M> fromSession){
		this.msg=msg;
		this.fromSession=fromSession;
	}
	public M getMessage(){
		return msg;
	}
	public ISession<M> getFromSession(){
		return this.fromSession;
	}
	public void addTargetSession(ISession<M> toSession){
		this.toSession.add(toSession);
	}
	public List<ISession<M>> getTargetSession(){
		return this.toSession;
	}
}

