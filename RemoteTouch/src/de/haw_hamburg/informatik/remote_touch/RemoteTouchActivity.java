package de.haw_hamburg.informatik.remote_touch;


import org.json.JSONObject;

import de.haw_hamburg.informatik.remote_touch.helper.Helper;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;

public class RemoteTouchActivity extends Activity implements OnGestureListener{
	private GestureDetector gestureDetector;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //gestureDetector = new GestureDetector(this);
        setContentView(R.layout.main);
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent e){
    	
    	try{
	    	JSONObject jsonobj = Helper.CreateJSonObjectFromMotionEvent(e); 
	    	String str = jsonobj.toString();
	    	JSONObject jObj = new JSONObject(str);
	    	MotionEvent event = Helper.CreateMotionEventFromJSonObject(jObj);
	    	event.describeContents();
    	}
    	catch(Exception ex){
    		ex.printStackTrace();
    	}
    	
    	return false;
    }

	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	public void onLongPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

}