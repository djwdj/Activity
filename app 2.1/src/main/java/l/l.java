package l;

import android.app.*;
import android.content.*;
import android.graphics.drawable.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.view.animation.*;
import android.widget.*;
import android.widget.LinearLayout.*;
import java.io.*;
import android.text.*;
import android.text.style.*;
import android.graphics.*;

public class l extends Activity 
{
	LinearLayout l,l1;
	LayoutParams lp=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT,1);
	OnClickListener o;
	Animation a1,aa;
	Button b;
	TextView t;
	float d,z;
	int w,h,p,m;
	SpannableString ss; 

	String[] s={"ROOT开启","ROOT关闭","手动开关","快速关闭"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
		{
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			getWindow().setStatusBarColor(0);
		}else if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT)
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

		DisplayMetrics dm = new DisplayMetrics();
		dm = getResources().getDisplayMetrics();
		d = dm.density;
		if(dm.widthPixels<dm.heightPixels)
		{
			w = dm.widthPixels;
			h = dm.heightPixels;
		}else{
			h = dm.widthPixels;
			w = dm.heightPixels;
		}
		p=w/36;
		m=w/60;
		z=w/d/20;
		
		lp.setMargins(m,m,m,m);

		l=new LinearLayout(this);
		l.setPadding(p,p,p,p);
		l.setGravity(Gravity.CENTER);

		a1=new TranslateAnimation(0, 0, h, 0);
		a1.setDuration(500);
		l.startAnimation(a1);

		l1=new LinearLayout(this);
		l1.setPadding(p,0,p,p);
		l1.setOrientation(LinearLayout.VERTICAL);
		l1.setBackgroundDrawable(d(w/10,0xbbcccccc));
		l1.setLayoutParams(lp);
		l.addView(l1);

		ss = new SpannableString("Activity\nv2.1"); 
		ss.setSpan(new RelativeSizeSpan(2f), 0, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss.setSpan(new RelativeSizeSpan(0.8f), 9, 13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss.setSpan(new StyleSpan(android.graphics.Typeface.ITALIC), 9, 13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		t=new TextView(this);
		t.setText(ss); 
		t.setTextSize(z);
		t.setTextColor(0xffff5555);
		t.setGravity(Gravity.CENTER);
		l1.addView(t);

		o=new OnClickListener(){

			@Override
			public void onClick(View v)
			{
				switch(v.getId())
				{
					case 0:
						s(String.format("settings put secure enabled_accessibility_services %s/%s",getPackageName(),"l.a"));
						s("settings put secure accessibility_enabled 1");
						break;
					case 1:
						s("am force-stop l.Activity");
						//s(String.format("settings put secure enabled_accessibility_services %s/%s",getPackageName(),"l.a"));
						//s("settings put secure accessibility_enabled 0");
						break;
					case 2:
						startActivity(new Intent("android.settings.ACCESSIBILITY_SETTINGS"));
						t("找到Activity，打开开关以授权本程序！ \n也可在这里关闭");
						break;
					case 3:
						startService(new Intent(l.this,a.class).putExtra("stop", true));
						break;
				}
			}
		};

		for(int i=0;i<s.length;i++)b(i);

		addContentView(l,new WindowManager.LayoutParams());
    }

	void b(int i)
	{
		b=new Button(this);
		b.setId(i);
		b.setText(s[i]);
		b.setTextColor(0xffffffff);
		b.setTextSize(z);
		b.setBackgroundDrawable(d(w/16,0xffff5556));
		b.setPadding(p,p,p,p);
		b.setLayoutParams(lp);
		b.setOnClickListener(o);

		l1.addView(b);

	}
	
	void t(String s)
	{
		Toast.makeText(this,s,50).show();
	}
	
	static Drawable d(int r,int c)
	{
		GradientDrawable d=new GradientDrawable();
		d.setColor(c);
		d.setCornerRadius(r);
		d.setStroke(2, 0xffeeeeee);
		return d;
	}

	static java.lang.Process su;
	static void s(String s)
	{
		try
		{
			if (su == null)su = Runtime.getRuntime().exec("su");
			OutputStream o=su.getOutputStream();
			o.write((s + "\n").getBytes());
			o.flush();
		}
		catch (IOException e)
		{}
	}
}
