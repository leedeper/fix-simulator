/*
 * Created by Leedeper on Sep 22, 2016.
 * All rights reserved.
 */
package com.leedeper.fixsimultor.impl.qfixj;

import java.util.Random;

import com.leedeper.fixsimultor.Data;

import quickfix.Message;

/**
 * 
 * @author Leedeper
 *
 */
public class RandomEnumSimulation implements Simulation{
	private String[] values=null;
	private Random random =new Random();
	private String curr=null;
	public RandomEnumSimulation(String ...values){
		this.values=values;
	}
	public String toString(){
		return curr;
	}

	@Override
	public String next(Data<Message> data) {
		curr= values[random.nextInt(values.length)];
		return curr;
	}
	
    @Override
    public Simulation clone() {
    	return new RandomEnumSimulation(values);
    }
}

