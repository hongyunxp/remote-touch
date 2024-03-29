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

import gnu.awt.virtual.VirtualToolkit;

import java.awt.Button;
import java.awt.Canvas;
import java.awt.Checkbox;
import java.awt.CheckboxMenuItem;
import java.awt.Choice;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.PopupMenu;
import java.awt.ScrollPane;
import java.awt.Scrollbar;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Window;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.peer.DragSourceContextPeer;
import java.awt.image.DirectColorModel;
import java.awt.peer.ButtonPeer;
import java.awt.peer.CanvasPeer;
import java.awt.peer.CheckboxMenuItemPeer;
import java.awt.peer.CheckboxPeer;
import java.awt.peer.ChoicePeer;
import java.awt.peer.DialogPeer;
import java.awt.peer.FileDialogPeer;
import java.awt.peer.FramePeer;
import java.awt.peer.LabelPeer;
import java.awt.peer.LightweightPeer;
import java.awt.peer.ListPeer;
import java.awt.peer.MenuBarPeer;
import java.awt.peer.MenuItemPeer;
import java.awt.peer.MenuPeer;
import java.awt.peer.PanelPeer;
import java.awt.peer.PopupMenuPeer;
import java.awt.peer.ScrollPanePeer;
import java.awt.peer.ScrollbarPeer;
import java.awt.peer.TextAreaPeer;
import java.awt.peer.TextFieldPeer;
import java.awt.peer.WindowPeer;

import javax.swing.JDesktopPane;

public class VirtualDesktop extends VirtualToolkit
{
	//
	// Construction
	//
	
	public VirtualDesktop( DirectColorModel colorModel, String title, int width, int height )
	{
		super( colorModel, width, height );
		init( title );
	}
	
	public VirtualDesktop( int bitsPerPixel, int rMask, int gMask, int bMask, String title, int width, int height )
	{
		super( bitsPerPixel, rMask, gMask, bMask, width, height );
		init( title );
	}
	
	public VirtualDesktop( String title, int width, int height )
	{
		super( width, height );
		init( title );
	}
	
	//
	// Operations
	//
	
	public void show()
	{
		desktopFrame.show();
	}
	
	public void dispose()
	{
		desktopFrame.dispose();
	}
	
	//
	// Toolkit
	//

	// Peers
	
	public ButtonPeer createButton( Button target )
	{
		return new SwingButtonPeer( target );
	}

	public CanvasPeer createCanvas( Canvas target )
	{
		//return super.createCanvas( target );
		return new SwingCanvasPeer( target );
	}

	public CheckboxPeer createCheckbox( Checkbox target )
	{
		return new SwingCheckboxPeer( target );
	}

	public CheckboxMenuItemPeer createCheckboxMenuItem( CheckboxMenuItem target )
	{
		return new SwingCheckboxMenuItemPeer( target );
	}

	public ChoicePeer createChoice( Choice target )
	{
		return new SwingChoicePeer( target );
	}

	public LightweightPeer createComponent( Component target )
	{
		return super.createComponent( target );
	}

	public DialogPeer createDialog( Dialog target )
	{
		return new SwingDialogPeer( target );
	}

	public DragSourceContextPeer createDragSourceContextPeer( DragGestureEvent dge )
	{
		return null;
	}

	public FileDialogPeer createFileDialog( FileDialog target )
	{
		return null;
	}
 
	public FramePeer createFrame( Frame target )
	{
		if( !initialized )
		{
			// Only desktop is real frame
			initialized = true;
			return super.createFrame( target );
		}
		else
			// Other frames are emulated
			return new SwingFramePeer( desktop, target );
	}

	public LabelPeer createLabel( Label target )
	{
		return new SwingLabelPeer( target );
	}

	public ListPeer createList( java.awt.List target )
	{
		return new SwingListPeer( target );
	}

	public MenuPeer createMenu( Menu target )
	{
		return new SwingMenuPeer( target );
	}

	public MenuBarPeer createMenuBar( MenuBar target )
	{
		return new SwingMenuBarPeer( target );
	}

	public MenuItemPeer createMenuItem( MenuItem target )
	{
		return new SwingMenuItemPeer( target );
	}

	public PanelPeer createPanel( Panel target )
	{
		return new SwingPanelPeer( target );
	}

	public PopupMenuPeer createPopupMenu( PopupMenu target )
	{
		return new SwingPopupMenuPeer( target );
	}

	public ScrollbarPeer createScrollbar( Scrollbar target )
	{
		return new SwingScrollbarPeer( target );
	}

	public ScrollPanePeer createScrollPane( ScrollPane target )
	{
		return new SwingScrollPanePeer( target );
	}

	public TextAreaPeer createTextArea( TextArea target )
	{
		return new SwingTextAreaPeer( target );
	}

	public TextFieldPeer createTextField( TextField target )
	{
		return new SwingTextFieldPeer( target );
	}

	public WindowPeer createWindow( Window target )
	{
		return new SwingWindowPeer( target );
	}

	///////////////////////////////////////////////////////////////////////////////////////
	// Private
	
	protected VirtualJFrame desktopFrame = null;
	protected JDesktopPane desktop = null;
	private boolean initialized = false;
	
	private void init( String title )
	{
		synchronized(VirtualJFrame.class){
			VirtualJFrame.defaultToolkit = this;
			desktopFrame = new VirtualJFrame(title );
			
		}
		desktopFrame.setSize( getScreenSize().width, getScreenSize().height );
		desktop = new JDesktopPane();
		desktopFrame.getContentPane().add( desktop );
	}
}
