/*
 * Created by Leedeper on Sep 23, 2016.
 * All rights reserved.
 */
package com.leedeper.fixsimultor.impl.qfixj;

import java.util.Iterator;
import java.util.List;

import com.leedeper.fixsimultor.IMessageIterator;
import com.leedeper.fixsimultor.IMessageIteratorFactory;

import quickfix.Field;
import quickfix.FieldMap;
import quickfix.Group;
import quickfix.Message;

/**
 * 
 * @author Leedeper
 *
 */
public class MessageIteratorFactory implements IMessageIteratorFactory {

	private Message tmpl=null;
	public MessageIteratorFactory( Message tmpl){
		this.tmpl=tmpl;
	}
	@Override
	public IMessageIterator getMessageIterator() {
		Message m=(Message)(tmpl.clone());
		cloneSimulation(m);
		return new MessageIterator(m);
	}
	private Message cloneSimulation(Message tmpl){
		Message newMsg=new Message();
		
		find(tmpl);
		find(tmpl.getHeader());
		find(tmpl.getTrailer());
		return newMsg;
	}
	private void find(FieldMap source){
		Iterator<Field<?>> fields = source.iterator();
		while(fields.hasNext()){
			Field<?> field = fields.next();
			if(field instanceof SimulationField){
				SimulationField sf=(SimulationField)field;
				SimulationField n=(SimulationField)(sf.clone());
				source.setField(n.getField(),n);
			}
		}
		Iterator<Integer> allKeys = source.groupKeyIterator();
		while(allKeys.hasNext()){
			Integer key=allKeys.next();
			List<Group> groups = source.getGroups(key);
			for(Group g:groups){
				find(g);
			}
		}
	}
}

