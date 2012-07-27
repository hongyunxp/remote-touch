package de.haw_hamburg.informatik.remote_display.mainapp;

import android.app.Activity;
import gnu.rfb.server.*;

public class RFBHostStarter {

	static RFBHost host;
	public static void start() throws NoSuchMethodException{
		host = new RFBHost(0, "display0", RFBServerImpl.class, new DefaultRFBAuthenticator("12345678"));	
	}
	public static void stop(){
		host.close();
	}
	public static void startOnUiThread(final Activity act) throws NoSuchMethodException{
		ThreadDispatcher dispatch = new ThreadDispatcher(){
			@Override
			public void startThread(final Thread t){
				new Thread(){
					@Override
					public void run(){act.runOnUiThread(t);}
					}.start();
				
			}
		};
		new RFBHost(0, "display0", RFBServerImpl.class, new DefaultRFBAuthenticator("12345678"),dispatch);
	}
	
}
