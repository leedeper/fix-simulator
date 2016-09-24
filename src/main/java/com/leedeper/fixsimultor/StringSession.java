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
public abstract class StringSession<M> implements ISession<M> {
	private String name=null;
	public StringSession(String name){
		this.name=name;
	}
	public String getName(){
		return this.name;
	}
	
	@Override
	public abstract boolean send(M msg) ;

}

