package de.haw_hamburg.informatik.remote_touch.helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.MotionEvent.PointerCoords;
import android.view.MotionEvent.PointerProperties;

public class Helper {
	
	public static JSONObject JObjFromPointer(PointerProperties prop, PointerCoords coords) throws JSONException{
		JSONObject pointer = new JSONObject();
		pointer.put("id",prop.id);
		pointer.put("toolType", prop.toolType);
		
		pointer.put("orientation",coords.orientation);
		pointer.put("pressure",coords.pressure);
		pointer.put("size",coords.size);
		pointer.put("toolMajor",coords.toolMajor);
		pointer.put("toolMinor",coords.toolMinor);
		pointer.put("touchMajor",coords.touchMajor);
		pointer.put("touchMinor",coords.touchMinor);
		pointer.put("x",coords.x);
		pointer.put("y",coords.y);
		
		return pointer;
	}
	
	public static void PointerValuesFromJSonObj(PointerProperties outProp, PointerCoords outCoords,JSONObject jObj) throws JSONException{
		outProp.id = jObj.getInt("id");
		outProp.toolType = jObj.getInt("toolType");
		 
		outCoords.orientation = (float) jObj.getDouble("orientation");
		outCoords.pressure = (float) jObj.getDouble("pressure");
		outCoords.size = (float) jObj.getDouble("size");
		outCoords.toolMajor = (float) jObj.getDouble("toolMajor");
		outCoords.toolMinor = (float) jObj.getDouble("toolMinor");
		outCoords.touchMajor = (float) jObj.getDouble("touchMajor");
		outCoords.touchMinor = (float) jObj.getDouble("touchMinor");
		outCoords.x = (float) jObj.getDouble("x");
		outCoords.y = (float) jObj.getDouble("y");
	}
	
	public static MotionEvent CreateMotionEventFromJSonObject(JSONObject jObj) throws JSONException{
		long uptime = SystemClock.uptimeMillis();
		//get pointer infos
		JSONArray array = jObj.optJSONArray("pointers");
		int arrlen = array != null ? array.length() : 1;
		PointerProperties[] props = new PointerProperties[arrlen];
		PointerCoords[] coords = new PointerCoords[arrlen];
		
		if(array != null){		
			for(int i=0;i<array.length();i++)
			{
				PointerProperties prop = new PointerProperties();
				PointerCoords coord = new PointerCoords(); 
				PointerValuesFromJSonObj(prop,coord,array.getJSONObject(i));
				props[i] = prop;
				coords[i] = coord;
			}
		}
		else{
			JSONObject pointer = jObj.optJSONObject("pointers");
			if(pointer != null ){
				PointerProperties prop = new PointerProperties();
				PointerCoords coord = new PointerCoords(); 
				PointerValuesFromJSonObj(prop,coord,pointer);
				props[0] = prop;
				coords[0] = coord;
			}
		}
		long downTimeOffset = jObj.getLong("eventTime") - jObj.getLong("downTime");
		/* TODO calculating the time offset is not a goog solution. the downtime should be the same of all events in a row.
		 * for an optimal use the events should be traced. then at the first occurency both down and event time
		 * are set from uptime. and then in the follow up events the uptime is used for event time and the stored downtime 
		 * for the downtime. but for this some identification of events is needed. 
		 * first events can be identified with (eventTime == downTime)
		 * */
		
		MotionEvent event = MotionEvent.obtain(
				uptime + downTimeOffset, 
				uptime,
				jObj.getInt("action"),
				jObj.getInt("pointerCount"), 
				props, 
				coords,
				jObj.getInt("metaState"),
				jObj.getInt("buttonState"),
				(float)jObj.getDouble("xPrecision"),
				(float)jObj.getDouble("yPrecision"),
				jObj.getInt("deviceId"), 
				jObj.getInt("edgeFlags"),
				jObj.getInt("source"),
				jObj.getInt("flags")
				);
		return event;
	}
	
	public static JSONObject CreateJSonObjectFromMotionEvent(MotionEvent e) throws JSONException{
		JSONObject jObj = new JSONObject();
		jObj.put("downTime", e.getDownTime());
		jObj.put("eventTime", e.getEventTime());
		jObj.put("action",e.getAction());
		jObj.put("pointerCount",e.getPointerCount());								
		jObj.put("metaState",e.getMetaState());
		jObj.put("buttonState",e.getButtonState());
		jObj.put("xPrecision",e.getXPrecision());
		jObj.put("yPrecision",e.getYPrecision());
		jObj.put("deviceId",e.getDeviceId());
		jObj.put("edgeFlags",e.getEdgeFlags());
		jObj.put("source",e.getSource()); 
		jObj.put("flags",e.getFlags());
			
		for(int i=0; i<e.getPointerCount(); i++)
		{
			PointerProperties prop = new PointerProperties();
			e.getPointerProperties(i, prop);
			
			PointerCoords coords = new PointerCoords();
			e.getPointerCoords(i, coords);
			
			JSONObject pointer = JObjFromPointer(prop,coords);
			jObj.accumulate("pointers", pointer);
		}

		return jObj;
	}
}
