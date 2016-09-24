/*
 * Created by Leedeper on Sep 22, 2016.
 * All rights reserved.
 */
package com.leedeper.fixsimultor.impl.qfixj;

import java.util.Random;

import com.leedeper.fixsimultor.Data;
import com.leedeper.fixsimultor.SimulatorRuntimeException;

import quickfix.Message;

/**
 * create random data between min to max
 * @author Leedeper
 *
 */
public class RandomSimulation implements Simulation{

	private enum Type{
		DOUBLE,INT
	}
	private Type type=null;

	private int theTimes=1;
	private int theMin=0;
	private int theMax=0;
	private Random random = new  Random();
	private String curr=null;
	public RandomSimulation(String min,String max)  {
		// if contain '.', then create double value, otherwise integer
		if(min.indexOf('.')>0 || max.indexOf('.')>0){
			type=Type.DOUBLE;
		}else{
			type=Type.INT;
		}
		
		if(Double.parseDouble(min)>=Double.parseDouble(max)){
			throw new SimulatorRuntimeException("min >= max, it's wrong");
		}
		
		if(type==Type.DOUBLE){
			theTimes=getTimes(min,max);
			theMin=(int)(Double.parseDouble(min)*theTimes);
			theMax=(int)(Double.parseDouble(max)*theTimes);
		}else{
			theMin=Integer.parseInt(min);
			theMax=Integer.parseInt(max);
		}
	}
	@Override
	public String next(Data<Message> data) {
		curr = getRandom().toString();
		return curr;
	}
	public String toString() {
		return curr;
	}
	
    @Override
    public Simulation clone() {
    	RandomSimulation clone=new RandomSimulation("1","2");
    	clone.theMax=this.theMax;
    	clone.theMin=this.theMin;
    	clone.theTimes=this.theTimes;
    	clone.type=this.type;
    	return clone;
    }

	private Object getRandom(){
		// int randNumber = rand.nextInt(MAX - MIN + 1) + MIN
		int d=random.nextInt((theMax-theMin+1))+theMin;
		if(type==Type.DOUBLE){
			return (double)d/(double)theTimes;
		}
		
		return d;
	}

	
	private int getTimes(String min,String max){
		String minSub=min.substring(min.indexOf('.')+1);
		String maxSub=max.substring(max.indexOf('.')+1);
		
		int ten=0;
		if(minSub.length()>maxSub.length()){
			ten=minSub.length();
		}else{
			ten=maxSub.length();
		}
		int t=1;
		for(int i=0;i<ten;i++){
			t=t*10;
		}
		return t;
	}

}

