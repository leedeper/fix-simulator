/*
 * Created by Leedeper on Sep 23, 2016.
 * All rights reserved.
 */
package com.leedeper.fixsimultor;

/**
 * 
 * @author Leedeper
 *
 */
public class SimulatorFactory implements ISimualtorFactory {

	@Override
	public ISimulator newSimulator() {
		// TODO Auto-generated method stub
		return new Simulator(){

			@Override
			public void go() {
				
			}
			
		};
	}

}

