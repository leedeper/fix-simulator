/*
 * Created by Leedeper on Sep 22, 2016.
 * All rights reserved.
 */
package com.leedeper.fixsimultor.impl.qfixj;

import com.leedeper.fixsimultor.Data;
import com.leedeper.fixsimultor.SimulatorRuntimeException;

import quickfix.FieldMap;
import quickfix.FieldNotFound;
import quickfix.Message;

/**
 * It's simple
 * @author Leedeper
 *
 */
public class FromReqSimulation implements Simulation {
	private int field;
	public FromReqSimulation(int field){
		this.field=field;
	}

	@Override
	public String next(Data<Message> data) {
		try {
			return data.getMessage().getString(field);
		} catch (FieldNotFound e) {
			throw new SimulatorRuntimeException(e);
		}
	}
	
    @Override
    public Simulation clone() {
    	return new FromReqSimulation(field);
    }
    
    public static void copyFromReqMsg(int field,FieldMap msg){
    	msg.setField(field,SimulationField.newField(field, new FromReqSimulation(field)));
    }
}

