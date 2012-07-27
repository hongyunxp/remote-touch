package de.haw_hamburg.informatik.remote_display.mainapp;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class ChooseOrientationActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

//        super.onCreate(savedInstanceState);
//
//		setContentView(R.layout.main);
		
		   super.onCreate(savedInstanceState);
	        setContentView(R.layout.choose_layout);
	        
	        ImageView moveable = (ImageView)findViewById( R.id.moveableImageView1 );
	        moveable.setOnTouchListener(new OnTouchListener(){
	        	float oldX=0.f,oldY=0.f;	        	
	        	boolean multidown = false;
	        	private int clamp(int value, int min, int max){
	        		return Math.min(
	        				max,
	        				Math.max(value,min));
	        	}
	        	
	        	private void rotate(View view){
	        		ImageView im = (ImageView)view;
	        		if(im.getImageMatrix() == null){
		        		Matrix m = new Matrix();
		        		m.postRotate(90);
		        		im.setImageMatrix(m);
	        		}
	        		else
	        			im.setImageMatrix(null);
	        		im.requestLayout();
	        		
	        		
	        		//view.setRotation( Math.abs(view.getRotation() - 90.f)  );//alternate 0°/90°
	        	}
				@Override
				public boolean onTouch(View arg0, MotionEvent arg1) {
					if(!(arg0 instanceof ImageView))
						return false;

					ImageView img = (ImageView)arg0;
					ViewGroup parent = (ViewGroup)img.getParent();
					FrameLayout.LayoutParams params = ((FrameLayout.LayoutParams)img.getLayoutParams());
					int maxPadLeft = parent.getWidth() - img.getWidth();
					int maxPadTop = parent.getHeight() - img.getHeight();
	        		switch (arg1.getActionMasked()) {
	        		
		        		case MotionEvent.ACTION_DOWN:{		        			
		        			oldX = arg1.getRawX();
		        			oldY = arg1.getRawY();
		        			break;
		        			}
		        		case MotionEvent.ACTION_MOVE:{
		        			float dragX = arg1.getRawX() - oldX;
		        			float dragY = arg1.getRawY() - oldY;

		        			int padL = Math.round(params.leftMargin + dragX);
		        			int padT = Math.round(params.topMargin + dragY);

		        			padL = clamp(padL,0,maxPadLeft);
		        			padT = clamp(padT,0,maxPadTop);
		        			params.leftMargin = padL;
		        			params.topMargin = padT;
		        			img.requestLayout();
		        			
		        			oldX = arg1.getRawX();
		        			oldY = arg1.getRawY();
		        			break;
		        		}
		        		case MotionEvent.ACTION_UP:{
		        			if(multidown){
		        				rotate(arg0);
		        				multidown = false;
		        			}		        				
		        			break;}
		        		case MotionEvent.ACTION_CANCEL:{
		        			break;
		        		}
		        		case MotionEvent.ACTION_POINTER_DOWN:{
		        			int cnt = arg1.getPointerCount();
	        				multidown = ( cnt == 2)?true:false;
		        			break;}

	        		}
					return true;
				}


	        	
	        });
		
        
    }
    public void ok_clicked(View view){

    	View moveable = findViewById( R.id.moveableImageView1 );
    	//FrameLayout.LayoutParams movableParams = ((FrameLayout.LayoutParams)moveable.getLayoutParams());
    	View fixedView = findViewById( R.id.imageView1 );
    	float scale = moveable.getRootView().getWidth() / fixedView.getWidth();
    	//FrameLayout.LayoutParams fixedParams = ((FrameLayout.LayoutParams)fixedView.getLayoutParams());
    	
    	int[] position = this.getIntent().getIntArrayExtra("position");
    	position[0] = moveable.getLeft() - fixedView.getLeft();
    	position[1] = moveable.getTop() - fixedView.getTop();
    	position[2] = (int)((moveable.getRight() - fixedView.getLeft())*scale);
    	position[3] = (int)( (moveable.getBottom() - fixedView.getTop())*scale);
    	
    	try {
			Intent resultInt = this.getIntent();//new Intent(this,ServerActivity.class);
			//resultInt.putExtra("position", position);
			setResult(Activity.RESULT_OK,resultInt);
			finish();
		} catch (Exception e) {
			Log.e("RemoteDisplay","error",e);
			e.printStackTrace();
		}
    	
    }
    
  
}