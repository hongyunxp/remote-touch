package gnu.awt.virtual.swing;

/**
* <br><br><center><table border="1" width="80%"><hr>
* <strong><a href="http://www.amherst.edu/~tliron/vncj">VNCj</a></strong>
* <p>
* Copyright (C) 2000-2002 by Tal Liron
* <p>
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU Lesser General Public License
* as published by the Free Software Foundation; either version 2.1
* of the License, or (at your option) any later version.
* <p>
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* <a href="http://www.gnu.org/copyleft/lesser.html">GNU Lesser General Public License</a>
* for more details.
* <p>
* You should have received a copy of the <a href="http://www.gnu.org/copyleft/lesser.html">
* GNU Lesser General Public License</a> along with this program; if not, write to the Free Software
* Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
* <hr></table></center>
**/

import java.awt.AWTEvent;
import java.awt.AWTException;
import java.awt.BufferCapabilities;
import java.awt.BufferCapabilities.FlipContents;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.event.PaintEvent;
import java.awt.peer.ContainerPeer;
import java.awt.peer.TextFieldPeer;

import javax.swing.JTextField;

import sun.awt.CausedFocusEvent.Cause;
import sun.java2d.pipe.Region;

class SwingTextFieldPeer extends JTextField implements TextFieldPeer
{
	//
	// Construction
	//
	
	public SwingTextFieldPeer( TextField textField )
	{
		super();
		SwingFramePeer.add( textField, this );
	}
	
	//
	// TextFieldPeer
	//
	
	public void setEchoChar( char echoChar )
	{
	}
	
	public Dimension getPreferredSize( int columns )
	{
		return null;
	}
	
	public Dimension getMinimumSize( int columns )
	{
		return null;
	}
	
	// Deprecated
	
	public void setEchoCharacter( char c )
	{
		setEchoChar( c );
	}

	public Dimension preferredSize( int cols )
	{
		return getPreferredSize( cols );
	}
	
	public Dimension minimumSize( int cols )
	{
		return getMinimumSize( cols );
	}
	
	//
	// TextComponentPeer
	//
	
	public int getIndexAtPoint( int x, int y )
	{
		return 0;
	}
	
	public Rectangle getCharacterBounds( int i )
	{
		return null;
	}
	
	public long filterEvents( long mask )
	{
		return 0;
	}
	
	//
	// ComponentPeer
	//
	
	// Events
	
	public void handleEvent( AWTEvent e )
	{
		//System.err.println(e);
	}
	
	public void coalescePaintEvent( PaintEvent e )
	{
		System.err.println(e);
	}
	
	// Misc
	
	public void dispose()
	{
	}

	public boolean canDetermineObscurity() {
		// TODO Auto-generated method stub
		return false;
	}

	public void createBuffers(int arg0, BufferCapabilities arg1) throws AWTException {
		// TODO Auto-generated method stub
		
	}

	public void destroyBuffers() {
		// TODO Auto-generated method stub
		
	}

	public void flip(FlipContents arg0) {
		// TODO Auto-generated method stub
		
	}

	public Image getBackBuffer() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean handlesWheelScrolling() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isObscured() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean requestFocus(Component arg0, boolean arg1, boolean arg2, long arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	public void updateCursorImmediately() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void applyShape(Region arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void flip(int arg0, int arg1, int arg2, int arg3, FlipContents arg4) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isReparentSupported() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void reparent(ContainerPeer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean requestFocus(Component arg0, boolean arg1, boolean arg2,
			long arg3, Cause arg4) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setBounds(int arg0, int arg1, int arg2, int arg3, int arg4) {
		// TODO Auto-generated method stub
		
	}
	
	///////////////////////////////////////////////////////////////////////////////////////
	// Private
}
