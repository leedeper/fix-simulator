/*
 * Created by Leedeper on Aug 28, 2016.
 * All rights reserved.
 */
package com.leedeper.fixsimultor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * @author Leedeper
 *
 */
public interface IDataStore<M> {

	public Iterator<IDataStoreObject<M>> getAll();
	public void add(M message);
	
}

