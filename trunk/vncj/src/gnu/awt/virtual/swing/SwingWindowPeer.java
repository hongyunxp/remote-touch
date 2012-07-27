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
import java.awt.Dialog;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.PaintEvent;
import java.awt.peer.ContainerPeer;
import java.awt.peer.WindowPeer;

import javax.swing.JInternalFrame;

import sun.awt.CausedFocusEvent.Cause;
import sun.java2d.pipe.Region;

class SwingWindowPeer extends JInternalFrame implements WindowPeer
{
	//
	// Construction
	//
	
	public SwingWindowPeer( Window window )
	{
		super();
	}
	
	//
	// WindowPeer
	//

	public int handleFocusTraversalEvent( KeyEvent e )
	{
		return -1;
	}
	
	//
	// ContainerPeer
	//

	public void beginValidate()
	{
	}
	
	public void endValidate()
	{
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

	public void beginLayout() {
		// TODO Auto-generated method stub
		
	}

	public void endLayout() {
		// TODO Auto-generated method stub
		
	}

	public boolean isPaintPending() {
		// TODO Auto-generated method stub
		return false;
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
	public boolean isRestackSupported() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void restack() {
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

	@Override
	public void repositionSecurityWarning() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean requestWindowFocus() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setAlwaysOnTop(boolean arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setModalBlocked(Dialog arg0, boolean arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setOpacity(float arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateFocusableWindowState() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateIconImages() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateMinimumSize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateWindow() {
		// TODO Auto-generated method stub
		
	}
	
	///////////////////////////////////////////////////////////////////////////////////////
	// Private
}
