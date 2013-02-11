package de.haw_hamburg.informatik.remote_display.mainapp;

import java.io.IOException;
import java.util.ArrayList;

import de.haw_hamburg.informatik.remote_touch.helper.Helper;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.MotionEvent.PointerCoords;
import android.view.View;
import android.view.ViewTreeObserver;

import org.json.JSONException;
import org.json.JSONObject;
import gnu.rfb.Colour;
import gnu.rfb.PixelFormat;
import gnu.rfb.Rect;
import gnu.rfb.server.*;
public class RFBServerImpl implements RFBServer,ViewTreeObserver.OnGlobalFocusChangeListener,ViewTreeObserver.OnPreDrawListener{
	public interface ClientAddedListener{
		public void OnClientAdded(RFBServerImpl server, RFBClient client);
	}
	abstract class ObjThread extends Thread{
		public Object parameter; 
	}
	static ArrayList<ClientAddedListener> _clientAddedListeners = new ArrayList<ClientAddedListener>(); 

	RFBClients clients = new RFBClients();
	int disp;
	String dName;
	private static Activity host;
	private static RFBServerImpl instance;
	private int updateAttempts = 3;
	static int offsetX,offsetY;
	public RFBServerImpl(){instance = this;}
	public static RFBServerImpl GetInstance(){return instance;}
	public static void setActivity(Activity act){
		host = act;
		ViewTools.setActivity(act);
		
		View rootView = host.getWindow().getDecorView().getRootView();		
		rootView.setDrawingCacheEnabled(true);
		rootView.buildDrawingCache();
		//host.getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(new LayoutObserver());
		
		
	}
	
	public RFBServerImpl(int display, String displayName){
		disp = display;
		dName = displayName;
		//host.getWindow().getDecorView().getViewTreeObserver().addOnGlobalFocusChangeListener(this);
	}
	public static void addClientAddedListener(ClientAddedListener listener){
		_clientAddedListeners.add(listener);
	}
	public void close(){
		while(clients.elements().hasMoreElements())
			try {
				((RFBClient) clients.elements().nextElement()).close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	public RFBClient getClientForView(View view){
		// TODO FIXME!  
		if(clients.elements().hasMoreElements())
			return (RFBClient) clients.elements().nextElement();
		else return null;
	}
	
	@Override
	public void addClient(RFBClient client) {
		clients.addClient(client);
		for(ClientAddedListener listener: _clientAddedListeners)
			listener.OnClientAdded(this, client);

	}

	@Override
	public void removeClient(RFBClient client) {
		clients.removeClient(client);
		
	}

	@Override
	public String getDesktopName(RFBClient client) {
		return dName;
	}

	@Override
	public int getFrameBufferWidth(RFBClient client) {
		return host.getWindow().getDecorView().getRootView().getWidth();
	}

	@Override
	public int getFrameBufferHeight(RFBClient client) {
		// TODO Auto-generated method stub
		return host.getWindow().getDecorView().getRootView().getHeight();
	}

	@Override
	public PixelFormat getPreferredPixelFormat(RFBClient client) {
		return PixelFormat.RGB888;
	}

	@Override
	public boolean allowShared() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setClientProtocolVersionMsg(RFBClient client,
			String protocolVersionMsg) throws IOException {
		// TODO Auto-generated method stub
	}

	@Override
	public void setShared(RFBClient client, boolean shared) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPixelFormat(RFBClient client, PixelFormat pixelFormat)
			throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEncodings(RFBClient client, int[] encodings)
			throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fixColourMapEntries(RFBClient client, int firstColour,
			Colour[] colourMap) throws IOException {
		// TODO Auto-generated method stub
		
	}
	private boolean _locked = false;
	private synchronized boolean getLock(){
		if(! _locked){
			_locked = true;
			return true;
		}
		else
			return false;
		
	}
	private void releaseLock(){
		_locked = false;
	} 
	private void sendWholeScreen(final RFBClient client) throws IOException{
//		if( updateAttempts < 0  )
//			return;
//		if(!getLock() )
//			return;

		View rootView = host.getWindow().getDecorView().getRootView();
//		View rootView = host.findViewById(R.id.scroller);


		rootView.setDrawingCacheEnabled(true);
		//rootView.buildDrawingCache();

//		rootView.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), 
//	            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//		rootView.layout(0, 0, rootView.getMeasuredWidth(), rootView.getMeasuredHeight()); 
		//rootView.layout(0, 0, rootView.getLayoutParams().width, rootView.getLayoutParams().height);
		
		//rootView.buildDrawingCache(false);
		Bitmap cache = rootView.getDrawingCache();

//		host.runOnUiThread(null);
		

		int[] pixels = new int[cache.getWidth()* cache.getHeight()];
		cache.getPixels(pixels, 0, cache.getWidth(), 0, 0, cache.getWidth(), cache.getHeight());
		Rect rect = Rect.encode(
				client.getPreferredEncoding(), 
				pixels, 
				client.getPixelFormat(), 
				cache.getWidth(),
				0,
				0,
				cache.getWidth(), 
				cache.getHeight());
		
		Rect[] rects = {rect};
		rootView.setDrawingCacheEnabled(false);
		try {
			client.writeFrameBufferUpdate(rects);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		ObjThread t = 	new ObjThread(){
//			public void run(){
//				final Bitmap cache = rootView.getDrawingCache();
//				new Thread(){@Override
//					public void run(){
//						int[] pixels = new int[cache.getWidth()* cache.getHeight()];
//						cache.getPixels(pixels, 0, cache.getWidth(), 0, 0, cache.getWidth(), cache.getHeight());
//						Rect rect = Rect.encode(
//								client.getPreferredEncoding(), 
//								pixels, 
//								client.getPixelFormat(), 
//								cache.getWidth(),
//								0,
//								0,
//								cache.getWidth(), 
//								cache.getHeight());
//						Rect[] rects = {rect};
//						
//						try {
//							client.writeFrameBufferUpdate(rects);
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//				
////				updateAttempts--;
////				releaseLock();
//				}}.start();
				
//			}
//		};
//		t.setName("building cache");
//		host.runOnUiThread(t);
//		try {
//			while(t.getState() == Thread.State.NEW)
//				this.wait(1);
//			t.join();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		cache = (Bitmap)t.parameter;
//		t.stop();
		
		

	}
	@Override
	public void frameBufferUpdateRequest(final RFBClient client, boolean incremental,
			int x, int y, int w, int h) throws IOException {
		// TODO Auto-generated method stub
//		host.getWindow().getDecorView().getViewTreeObserver().removeOnPreDrawListener(this);
//		host.getWindow().getDecorView().getViewTreeObserver().addOnPreDrawListener(this);
//		updateAttempts = 3;
		//sendWholeScreen(client);
		
		Thread t = new Thread(){
			
			@Override
			public void run(){
				while(true){
					try {
						sendWholeScreen(client);
						Thread.sleep(40);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		};
		t.setName("loop update thread");
		t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			
			@Override
			public void uncaughtException(Thread thread, Throwable ex) {
				// TODO Auto-generated method stub
				//thread.start();
			}
		});
		t.start();
		
	}

	@Override
	public void keyEvent(RFBClient client, boolean down, int key)
			throws IOException {
		// TODO Auto-generated method stub
		
	}
	private ArrayList<View> observed = new ArrayList<View>();
	@Override
	public void pointerEvent(final RFBClient client, int buttonMask, int x, int y)
			throws IOException {
		int action = (buttonMask == 1)?MotionEvent.ACTION_DOWN : MotionEvent.ACTION_UP;
		final MotionEvent e = MotionEvent.obtain(
				SystemClock.uptimeMillis(),
				SystemClock.uptimeMillis(), 
				action,
				x, y, 
				1.f, 0.5f, 0, 1, 1, 0, 0);
		final View target = ViewTools.findViewAt(x, y);
		if(target == null)
			return;
		updateAttempts = 3;

		host.runOnUiThread(new Runnable(){@Override
		public void run(){
			target.dispatchTouchEvent(e);
			target.invalidate();
			//target.getViewTreeObserver().addOnPreDrawListener(new PreDrawListener(target,client,4));
		}});
//		target.dispatchTouchEvent(e);
		//target.postInvalidate();
		return;

	}
	View lastTarget = null;
	@Override
	public void onGlobalFocusChanged(View oldFocus, View newFocus) {
		// TODO Auto-generated method stub
//		newFocus.getViewTreeObserver().addOnPreDrawListener(this);
//		oldFocus.getViewTreeObserver().removeOnPreDrawListener(this);
//		lastTarget = newFocus;
	}

	@Override
	public boolean onPreDraw() {
		// TODO Auto-generated method stub
	
			
//			final RFBClient client = getClientForView(lastTarget);
//			if(client != null){
//				new Thread(){@Override
//				public void run(){try {
//					sendWholeScreen(client);
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//					Log.e("RemoteDisplay","error while sending screen",e);
//				}}}.start();
//				
//				
//				}
//
//		//updateForView(getClientForView(lastTarget),lastTarget);
		return true;
	}
	
	private static void updateForView(final RFBClient client, View view){
		view.setDrawingCacheEnabled(true);
		//view.destroyDrawingCache();
//		view.buildLayer();
		
//		Bitmap bm = Bitmap.createBitmap( view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);                
//		Canvas c = new Canvas(bm);
//		view.invalidate();
//		view.forceLayout();
//		view.layout(0, 0, view.getLayoutParams().width, view.getLayoutParams().height);
//		view.draw(c);
//		
		//view.getDrawingCache();
		Bitmap cache = view.getDrawingCache();
//		Canvas renderC = new Canvas(cache);
		//view.destroyDrawingCache();
//		view.layout(0, 0, cache.getWidth(), cache.getHeight());
//		view.draw(renderC);
		//view.buildDrawingCache(false);

		
		int[] location = new int[2];
		view.getLocationOnScreen(location);
		int[] pixels = new int[cache.getWidth()* cache.getHeight()];
		cache.getPixels(pixels, 0, cache.getWidth(), view.getLeft(),view.getTop(), cache.getWidth(), cache.getHeight());
		Rect rect = Rect.encode(
				client.getPreferredEncoding(), 
				pixels, 
				client.getPixelFormat(), 
				cache.getWidth(),
				view.getLeft(),view.getTop(),
				cache.getWidth(), 
				cache.getHeight());
		rect.transform(location[0],location[1]);
		Rect[] rects = {rect};

		try {
			client.writeFrameBufferUpdate(rects);
		} catch (IOException e) {
			Log.e("RemoteDisplay", "error while sending update rect", e);
		}
		
	}
//	class EventDispatcher implements Runnable{
//		private MotionEvent event;
//		private ArrayList<View> hits;
//		public EventDispatcher(MotionEvent e){
//			event=e;
//			hits = new ArrayList<View>();
//		}
//		public ArrayList<View> GetHits(){return hits;}
//		public boolean isTarget(View view){
//			android.graphics.Rect outRect = new android.graphics.Rect();
//			view.getHitRect(outRect);
//			for(int i=0;i<event.getPointerCount();i++){
//				android.graphics.Rect pointerRect = new android.graphics.Rect();
//				PointerCoords coords = new PointerCoords();
//				event.getPointerCoords(i, coords);
//				pointerRect.set(left, top, right, bottom);
//			}
//		}
//		public synchronized void run(){
//			ArrayList<View> touchables = host.getWindow().getDecorView().getTouchables();
//			for(View view : touchables){
//				view.getHitRect(outRect)
//				if(view.onTouchEvent(event)){
//					view.invalidate();view.gethit
//					hits.add(view);
//				}
//			}
//			
//			//this.notify();
//		}
//	}
	protected  void dispatchMotionEvent(RFBClient client, final MotionEvent e){
		//ViewTools.findViewsForMotionEvent(e);
		
		final View view = ViewTools.findViewAt((int)e.getX(), (int)e.getY());
		if(view == null)
			return;
		host.runOnUiThread(new Runnable(){@Override
			public void run(){
				view.dispatchTouchEvent(e);
				view.invalidate();
				//target.getViewTreeObserver().addOnPreDrawListener(new PreDrawListener(target,client,4));
			}});
		
	}
	
	@Override
	public void clientCutText(RFBClient client, String text) throws IOException {
		Log.d("RemoteTouch", "received cutTextMessage. message:\n"+text);
		try{
			JSONObject jsonMotion = new JSONObject(text);
			MotionEvent motionEvent = Helper.CreateMotionEventFromJSonObject(jsonMotion);
			dispatchMotionEvent(client, motionEvent);
			sendWholeScreen(client);
		}
		catch(JSONException e){
			//the text could not be parsed to a json object. message will be dropped.
			Log.e(
					"RemoteTouch", 
					"message could not be parsed to JSON Object. Dropping message. message was:"+text,
					e);
		}
	}
	public class PreDrawListener implements ViewTreeObserver.OnPreDrawListener{
		View target;
		RFBClient client;
		int updates;
		public PreDrawListener(View target,RFBClient client,int updatecount){
			this.target = target;
			this.client=client;
			updates = updatecount;
		}
		@Override
		public boolean onPreDraw() {
			
			if(updates-- <= 0){
				target.getViewTreeObserver().removeOnPreDrawListener(this);
				Log.d("Remote display", "PreDrawListener remove from listerners");
				return true;
			}else{
				
				//RFBServerImpl.host.runOnUiThread(
					Thread t = new Thread(){
						
						@Override public void run(){
						updateForView(client,target);
						}
					};
					t.setPriority(Thread.MIN_PRIORITY);
					t.setName("update for view thread");
					t.start();
			}
			
			return true;
		}
		
	}

}
