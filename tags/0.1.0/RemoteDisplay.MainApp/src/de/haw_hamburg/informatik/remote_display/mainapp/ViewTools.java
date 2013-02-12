package de.haw_hamburg.informatik.remote_display.mainapp;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Enumeration;

import android.app.Activity;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.MotionEvent.PointerCoords;
import android.view.View;
import android.view.ViewGroup;

public class ViewTools {

	private static Activity host;
	private static ArrayList<View> touchables;
	public static void setActivity(Activity act)
	{
		host = act;
		touchables = host.getWindow().getDecorView().getTouchables();
		}
	public static View[] findViewsForMotionEvent(MotionEvent mEvent){
		touchables = host.getWindow().getDecorView().getTouchables();
		Rect viewArea = new Rect();
		int[] loc = new int[2];
		ArrayList<View> views = new ArrayList<View>();
		//check dimensions of touchable objects with coordinates.
		for(View view :touchables){
			view.getLocationOnScreen(loc);
			viewArea.left = loc[0];
			viewArea.top = loc[1];
			viewArea.right = view.getWidth()  + loc[0];
			viewArea.bottom = view.getHeight() + loc[1];
			
			PointerCoords coordinates = new PointerCoords();
			for(int i=0;i<mEvent.getPointerCount();i++){
				mEvent.getPointerCoords(i, coordinates);
				if(viewArea.contains((int)coordinates.x, (int)coordinates.y))
					views.add(view);
			}
		}
		return (View[])views.toArray();
	}
	
	public static View findViewAt(int x, int y){
		//update touchables list
		touchables = host.getWindow().getDecorView().getTouchables();
		Rect viewArea = new Rect();
		int[] loc = new int[2];
		//check dimensions of touchable objects with coordinates.
		for(View view :touchables){
			view.getLocationOnScreen(loc);
			viewArea.left = loc[0];
			viewArea.top = loc[1];
			viewArea.right = view.getWidth()  + loc[0];
			viewArea.bottom = view.getHeight() + loc[1];
			if(viewArea.contains(x, y))
				return view;
		}
//		if( host.getWindow().getDecorView().getRootView() instanceof ViewGroup){
//			ViewGroup root = (ViewGroup) host.getWindow().getDecorView().getRootView();
//			
//			int numChildren = root.getChildCount();
//			for(int i=0;i<numChildren;i++){
//				View view = root.getChildAt(i);
//				
//				view.getLocationOnScreen(loc);
//				viewArea.left = loc[0];
//				viewArea.top = loc[1];
//				viewArea.right = view.getWidth()  + loc[0];
//				viewArea.bottom = view.getHeight() + loc[1];
//				if(viewArea.contains(x, y))
//					return view;
//			}		
//		}
		View res = recursiveChildrenCheck(host.getWindow().getDecorView().getRootView(), x,y);
		return res;
	}
	
	private static View recursiveChildrenCheck(View target,int x,int y){
			if(target instanceof ViewGroup){
				ViewGroup parent = (ViewGroup) target;
				int numChildren = parent.getChildCount();
				for(int i=0;i<numChildren;i++){
					View child = parent.getChildAt(i);
					View result = recursiveChildrenCheck(child,x,y);
					if(result != null)
						return result;
				}
				return null;
			}
			else{
				if(!target.isShown())
					return null;
				Rect viewArea = new Rect();
				int[] loc = new int[2];
				target.getLocationOnScreen(loc);
				viewArea.left = loc[0];
				viewArea.top = loc[1];
				viewArea.right = target.getWidth()  + loc[0];
				viewArea.bottom = target.getHeight() + loc[1];
				if(viewArea.contains(x, y))
					return target;
				else
					return null;
			}
	}
}
