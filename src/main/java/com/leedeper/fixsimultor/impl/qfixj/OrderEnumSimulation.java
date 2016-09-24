/*
 * Created by Leedeper on Sep 22, 2016.
 * All rights reserved.
 */
package com.leedeper.fixsimultor.impl.qfixj;

import com.leedeper.fixsimultor.Data;

import quickfix.Message;

/**
 * 
 * @author Leedeper
 *
 */
public class OrderEnumSimulation implements Simulation{
	private String[] values=null;
	private int inx=0;
	private String curr=null;
	public OrderEnumSimulation(String ...values){
		this.values=values;
	}
	public String toString(){
		return curr;
	}

	@Override
	public String next(Data<Message> data) {
		curr=values[inx];
		inx++;
		if(inx==values.length){
			inx=0;
		}
		return curr;
	}
	
    @Override
    public Simulation clone() {
    	return new OrderEnumSimulation(values);
    }
}

