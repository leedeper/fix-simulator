/*
 * Created by Leedeper on Sep 22, 2016.
 * All rights reserved.
 */
package com.leedeper.fixsimultor.impl.qfixj;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.leedeper.fixsimultor.Data;
import com.leedeper.fixsimultor.IMessageIterator;
import com.leedeper.fixsimultor.SimulatorRuntimeException;

import quickfix.Field;
import quickfix.FieldMap;
import quickfix.Group;
import quickfix.Message;

/**
 * 
 * @author Leedeper
 *
 */
public class MessageIterator implements IMessageIterator<Message>   {
	private Message tmpl=null;
	private List<SimulationField> simulationFields=null;
	private long maxNumber=0; //  no limit if >0
	private long curr=0;
	public MessageIterator(Message tmpl){
		this(tmpl,0);
	} 
	public MessageIterator(Message tmpl,int maxNumber){
		this.tmpl=tmpl;
		simulationFields=findAllSimulation(tmpl);
		this.maxNumber= maxNumber;
	}
	@Override
	public boolean hasNext() {
		if(maxNumber>0 && curr>=maxNumber){
			return false;
		}
		return true;
	}


	@Override
	public Message next(Data<Message> data) {
		if(!hasNext()){
			throw new SimulatorRuntimeException("No more message. maxNumber="+maxNumber);
		}
		if(maxNumber>0){
			curr++;
		}
		change(data);
		return tmpl;
	}
	private void change(Data<Message> data){
		for(SimulationField sf:simulationFields){
			sf.next(data);
		}
	}

	private List<SimulationField> findAllSimulation(Message tmpl){
		List<SimulationField> simulationFields=new ArrayList<>();
		find(tmpl,simulationFields);
		find(tmpl.getHeader(),simulationFields);
		find(tmpl.getTrailer(),simulationFields);
		return simulationFields;
	}
	private void find(FieldMap source, List<SimulationField> simulationFields){
		Iterator<Field<?>> fields = source.iterator();
		while(fields.hasNext()){
			Field<?> field = fields.next();
			if(field instanceof SimulationField){
				simulationFields.add((SimulationField)field);
			}
		}
		Iterator<Integer> allKeys = source.groupKeyIterator();
		while(allKeys.hasNext()){
			Integer key=allKeys.next();
			List<Group> groups = source.getGroups(key);
			for(Group g:groups){
				find(g,simulationFields);
			}
		}
	}
}

