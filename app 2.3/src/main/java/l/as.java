package l;
import android.accessibilityservice.*;
import android.view.accessibility.*;
import android.content.*;
import android.widget.*;

public class as extends AccessibilityService
{

	@Override
	protected void onServiceConnected()
	{
		AccessibilityServiceInfo serviceInfo = new AccessibilityServiceInfo();
        serviceInfo.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED;
        serviceInfo.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
		
		//serviceInfo.flags = AccessibilityServiceInfo.FLAG_REQUEST_FINGERPRINT_GESTURES;
		setServiceInfo(serviceInfo);
		super.onServiceConnected();
		l.r_as=true;
		startService(new Intent(this,s.class));
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onAccessibilityEvent(AccessibilityEvent e)
	{
		if(l.r_a)
		sb(e.getPackageName()+"/"+e.getClassName());
		//else Toast.makeText(this,e.getPackageName(),60).show();
	}

	@Override
	public void onInterrupt()
	{
		
	}

	@Override
	public void onDestroy()
	{
		l.r_as=false;
		super.onDestroy();
	}

	
	void sb(CharSequence s)
	{
		Intent i = new Intent();
		i.setAction(l.a);
		i.putExtra("s",s);
		sendBroadcast(i);
	}
}
