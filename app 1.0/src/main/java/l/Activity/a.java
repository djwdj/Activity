package l.Activity;

import android.accessibilityservice.*;
import android.view.accessibility.*;
import android.widget.*;

public class a extends AccessibilityService
{

	@Override
    protected void onServiceConnected() {
        AccessibilityServiceInfo serviceInfo = new AccessibilityServiceInfo();
        serviceInfo.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED;
        serviceInfo.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
		setServiceInfo(serviceInfo);
    }

	@Override
	public void onAccessibilityEvent(AccessibilityEvent event)
	{
		Toast.makeText(this,event.getPackageName() + "\n" + event.getClassName(),0).show();
	}

	@Override
	public void onInterrupt()
	{

	}

}
