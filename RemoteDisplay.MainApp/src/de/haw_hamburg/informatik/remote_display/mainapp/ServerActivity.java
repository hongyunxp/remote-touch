package de.haw_hamburg.informatik.remote_display.mainapp;

import de.haw_hamburg.informatik.remote_display.mainapp.RFBServerImpl.ClientAddedListener;
import gnu.rfb.server.RFBClient;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

public class ServerActivity extends Activity implements ClientAddedListener{
	
	static int REQUEST_LAYOUTACTIVITY = 0xBEEF;
	static String BUNDLE_CL_WIDTH = "client-width";
	static String BUNDLE_CL_HEIGH = "client-height";
	
	
    /** Called when the activity is first created. */
	//private NetworkServer server;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        int ip = wifiManager.getConnectionInfo().getIpAddress();

        if(ip == 0){//seems to be in access-point mode
        	ip = wifiManager.getDhcpInfo().ipAddress;
        }
        String address = Formatter.formatIpAddress(ip);
        ((TextView)this.findViewById(R.id.textView)).setText(address);
    }

    boolean serverRunning = false;
    public void connectClicked(View view){
    	if(!serverRunning){
	    	((Button)view).setText("running");
	    	try {
	    		RFBServerImpl.setActivity(this);
				RFBHostStarter.start();
	    		RFBServerImpl.addClientAddedListener(this);
				serverRunning = true;
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(Exception e){
				e.printStackTrace();
			}
	    	
    	}
    	else{
    		serverRunning = false;
    		RFBHostStarter.stop();
    		((Button)view).setText("Start Server");
    	}
    }
    

    private RFBClient clientForLayout = null;
    
	@Override
	public void OnClientAdded(RFBServerImpl server, RFBClient client) {
		final Intent in = new Intent(this, ChooseOrientationActivity.class);
		int[] posRect = new int[4];
		in.putExtra("position", posRect);
		client.pause();
		clientForLayout = client;
		this.startActivityForResult(in, REQUEST_LAYOUTACTIVITY);
	}
	@Override 
	public void onDestroy(){
		super.onDestroy();
//		RFBHostStarter.stop();
//		RFBServerImpl.GetInstance().close();
//		int pid = android.os.Process.myPid();
//		android.os.Process.killProcess(pid);
	}
	
	@Override
	public void onBackPressed() {
	    super.onBackPressed();
	    int pid = android.os.Process.myPid();
	    android.os.Process.killProcess(pid);
	}

	@Override 
	public void onActivityResult(int requestCode, int resultCode, final Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		this.runOnUiThread(
				new Thread(){
					public void run(){
						findViewById(R.id.scroller).scrollTo(0, 400);
					}
					});
		/*
		this.runOnUiThread(
				new Thread(){
					public void run(){
				
						View scroller = findViewById(R.id.scroller);
						View scrollContent = findViewById(R.id.scrollContent);
						
						LayoutParams params = scrollContent.getLayoutParams();
						//params.height
						int[] clientPosition = data.getIntArrayExtra("position");
						Rect clientRect = new Rect(
								clientPosition[0],
								clientPosition[1],
								clientPosition[2],
								clientPosition[3]);
						Rect baseRect = new Rect(
								scroller.getLeft(),
								scroller.getTop(),
								scroller.getRight(),
								scroller.getBottom());
						Rect boundingRect = new Rect(baseRect);
						boundingRect.union(clientRect);
										
						params.width = baseRect.width();
						params.height = baseRect.height();
						scrollContent.requestLayout();
						int scrL = baseRect.left - boundingRect.left;
						int scrT = baseRect.top - boundingRect.top;
						scroller.scrollTo(scrL,scrT);
						scroller.requestLayout();
						
				}});
		*/
		clientForLayout.resume();

		
	}
}