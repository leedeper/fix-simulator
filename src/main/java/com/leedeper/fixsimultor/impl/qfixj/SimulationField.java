/*
 * Created by Leedeper on Sep 22, 2016.
 * All rights reserved.
 */
package com.leedeper.fixsimultor.impl.qfixj;

import com.leedeper.fixsimultor.Data;

import quickfix.Field;
import quickfix.FieldMap;
import quickfix.Message;

/**
 * 
 * @author Leedeper
 *
 */
public class SimulationField extends Field<String> {
	
	private static final long serialVersionUID = 1L;
	private Simulation simulation=null;
	public SimulationField(int field, Simulation simulation) {
		super(field, "");
		this.simulation=simulation;
	}
	public void next(Data<Message> data){
		String value=simulation.next(data);
		this.setObject(value);
	}
	
    @Override
    public Object clone() {
    	return new SimulationField(this.getField(),this.simulation.clone());
    }
	

    public static SimulationField newField(int field, Simulation simulation){
    	return new SimulationField(field, simulation);
    }
/*    public static void setField(int field,Simulation simulation, FieldMap msg){
    	msg.setField(field, simulation);
    	return new SimulationField(field, simulation);
    }*/
}

