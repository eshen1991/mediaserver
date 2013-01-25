/*
 * Telestax, Open Source Cloud Communications
 * Copyright 2013, Telestax, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.mobicents.media.server.component.oob;

import java.io.IOException;

import org.mobicents.media.server.impl.AbstractSink;
import org.mobicents.media.server.scheduler.ConcurrentLinkedList;
import org.mobicents.media.server.spi.memory.Frame;
import org.mobicents.media.server.spi.memory.Memory;

/**
 * Implements Out of Band events input for compound components
 * 
 * @author Yulian Oifa
 */
public class OOBInput extends AbstractSink {
    private int inputId;
    private int limit=10;
    private ConcurrentLinkedList<Frame> buffer = new ConcurrentLinkedList();
    
    /**
     * Creates new stream
     */
    public OOBInput(int inputId) {
        super("compound.input");
        this.inputId=inputId;        
    }
    
    public int getInputId()
    {
    	return inputId;
    }
    
    public void activate()
    {
    	
    }
    
    public void deactivate()
    {
    	
    }
    
    @Override
    public void onMediaTransfer(Frame frame) throws IOException {
    	if (buffer.size() >= limit) 
    		buffer.poll().recycle();
        
    	buffer.offer(frame);    	
    }

    /**
     * Indicates the state of the input buffer.
     *
     * @return true if input buffer has no frames.
     */
    public boolean isEmpty() {
        return buffer.isEmpty();
    }

    /**
     * Retrieves frame from the input buffer.
     *
     * @return the media frame.
     */
    public Frame poll() {
    	return buffer.poll();
    }

    /**
     * Recycles input stream
     */
    public void recycle() {
    	while(buffer.size()>0)
    		buffer.poll().recycle();    	    			       
    }
    
    public void resetBuffer()
    {
    	while(buffer.size()>0)
    		buffer.poll().recycle();
    }
}
