package de.haw_hamburg.informatik.remote_display.mainapp;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Rect;
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
