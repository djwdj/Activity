package l;
import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.util.*;
import android.graphics.*;
import android.provider.*;
import android.net.*;
import android.view.View.*;
import l.Activity.*;
import java.lang.reflect.*;

public class s extends Service implements OnTouchListener
{
	boolean rr=false;
	
	WindowManager wm;
	WindowManager.LayoutParams wp;
	float x0,y0,x1,y1,x2,y2;
	TextView t;
	String s;
	float d;
	int w,h;
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		if (intent != null) {
            if (intent.getBooleanExtra("stop",false)) {
                stopSelf();
            }
			if (intent.getBooleanExtra("open",false)) {
				n();
				v();
			}
        }
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onCreate()
	{
		if(!rr)rr();
		l.r_a=true;
		
		super.onCreate();
		
		if (Build.VERSION.SDK_INT >= 23)if (!Settings.canDrawOverlays(this))
			{
				Toast.makeText(this, "请手动开启浮窗权限！", Toast.LENGTH_SHORT).show();
				startActivity(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
										 Uri.parse(String.format("package:%s", getPackageName()))));
			}

		DisplayMetrics dm = getResources().getDisplayMetrics();
		d = dm.density;
		if (dm.widthPixels < dm.heightPixels)
		{
			w = dm.widthPixels;
			h = dm.heightPixels;
		}
		else
		{
			h = dm.widthPixels;
			w = dm.heightPixels;
		}

		n();
		v();
	}

	void v()
	{
		s="[Activity]激活中。。。";
		t = new TextView(this);
		t.setText(s);
		t.setTextSize(w / d / 26);
		t.setPadding(18, 0, 18, 2);
		t.setBackgroundDrawable(l.d(20, 0xaa555555));
		t.setTextColor(Color.WHITE);
		t.setGravity(Gravity.CENTER);
		t.setOnTouchListener(this);

		wm = (WindowManager) getApplicationContext().getSystemService(WINDOW_SERVICE);
		wp = new WindowManager.LayoutParams();
		wp.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            wp.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }
		wp.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;	
		wp.gravity = Gravity.LEFT | Gravity.TOP; 
		wp.x = 0;
		wp.y = h / 30;
		wp.width = WindowManager.LayoutParams.WRAP_CONTENT;
		wp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		wp.format = PixelFormat.RGBA_8888;

		wm.addView(t, wp);
	}

	@Override
	public boolean onTouch(View v, MotionEvent e) {
		x1 = e.getRawX();
		y1 = e.getRawY() ; 

		switch (e.getAction()) {
			case MotionEvent.ACTION_DOWN:
				x2=x1;
				y2=y1;
				x0 = e.getX();
				y0 = e.getY();

				break;
			case MotionEvent.ACTION_MOVE:
				wp.x = (int) (x1 - x0);
				wp.y = (int) (y1 - y0);
				wm.updateViewLayout(t, wp);
				break;
			case MotionEvent.ACTION_UP:
				wp.x = (int) (x1 - x0);
				wp.y = (int) (y1 - y0);
				wm.updateViewLayout(t, wp);

				if(x2-x1==0&&x2-x1==0)
				{
					t(s);
					c(s);
				}
				x0 = y0 = 0;
				break;
		}
		return false;
	}

	@Override
	public void onDestroy()
	{
		l.r_a=false;
		((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).cancelAll();
		wm.removeView(t);
		super.onDestroy();
	}
	
	@Override
	public IBinder onBind(Intent p1)
	{
		return null;
	}
	
	void n()
	{
		PendingIntent pi=PendingIntent.getService(this, 0, new Intent(this, getClass()).putExtra("stop", true), 0);

		NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		if(Build.VERSION.SDK_INT>=26)nm.createNotificationChannel(new NotificationChannel("l", "a", NotificationManager.IMPORTANCE_LOW));
		
		Notification n = new Notification();
		if(Build.VERSION.SDK_INT>=11)
		{
			Notification.Builder nb = (Build.VERSION.SDK_INT >= 26 ? new Notification.Builder(this, "l"): new Notification.Builder(this))
				.setContentTitle("Activity")
				.setContentText("点击关闭")
				.setSmallIcon(R.drawable.l)
				.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.l))
				.setContentIntent(pi);

			n=nb.build();
		}else
		{
			n.icon = R.drawable.l;
			n.tickerText="正在运行Activity";
			
			try
			{
				Method m = n.getClass().getMethod("setLatestEventInfo", Context.class, CharSequence.class,CharSequence.class, PendingIntent.class);
				m.invoke(n, this, "Activity","点击关闭", pi);
			}
			catch (Exception e)
			{}
		}
		
		n.flags = n.FLAG_ONGOING_EVENT;
		nm.notify(0, n);
	}
	
	int sdk=Build.VERSION.SDK_INT;
	boolean sdk(int i)
	{
		return i<0?i>-sdk:i>=sdk;
	}
	
	void t(String s)
	{
		Toast.makeText(this,s,50).show();
	}

	void c(String s)
	{
		//((ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE)).setText(s);
		if(Build.VERSION.SDK_INT<11)
		((android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE)).setText(s);
		else
		((ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("",s));
	}
	
	void r(CharSequence s)
	{
		this.s=(String) s;
		t.setText(s);
	}
	
	void rr()
	{
		rr=true;
		registerReceiver(new br(),new IntentFilter(l.a));
	}

	
	class br extends BroadcastReceiver
	{

		@Override
		public void onReceive(Context c, Intent i)
		{
			//Toast.makeText(c,"收到",60).show();
			r(i.getStringExtra("s"));
		}
	}
}
