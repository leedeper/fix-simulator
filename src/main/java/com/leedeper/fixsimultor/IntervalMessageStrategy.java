/*
 * Created by Leedeper on Sep 24, 2016.
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
public class IntervalMessageStrategy<M> extends SimpleMessageStrategy<M> {

	private List<Data<M>> request=new ArrayList<>();
	private long interval=0;
	private Thread thread=new Thread(new Task());
	public IntervalMessageStrategy(long millisecond){
		this.interval=millisecond;
		thread.setDaemon(true);
	}
	@Override
	public void handle(Data<M> data) {
		request.add(data);
		// start when the first request come.
		if(request.size()==1){
			thread.start();
		}
	}
	private IMessageStrategy<M> getThis(){
		return this;
	}
	private class Task implements Runnable{
		public void run(){
			IMessageIterator<M> iterator = getThis().getMessageIterator();
			while(iterator.hasNext()){
				try {
					Thread.currentThread().sleep(interval);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				for(Data<M> data:request){
					M msg=iterator.next(data);
					 getThis().getDistributor().send(msg, data);
				}
			}
		}
	}
}

