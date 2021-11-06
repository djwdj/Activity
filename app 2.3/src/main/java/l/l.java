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
import android.content.res.*;
import android.view.ViewGroup.*;

public class l extends Activity implements OnClickListener
{
	LinearLayout l,ll;
	LayoutParams lp=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT,1);
	ScrollView sc;
	Animation a1,aa;
	Button b;
	boolean hw;
	TextView t;
	float d,z;
	int w,h,p,m;
	SpannableString ss; 
	String ver="v2.3.2";
	String[] s={"一键激活","强制停止","手动激活","停止使用","开始使用"};

	static String a="djwdj.Activity.Service";
	static boolean r_a,r_as;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			getWindow().setNavigationBarColor(0);
			getWindow().setStatusBarColor(0);
		}else if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT)
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS |
								 WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		//else getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

		
		DisplayMetrics dm = getResources().getDisplayMetrics();
		d = dm.density;
		w = dm.widthPixels;
		h = dm.heightPixels;
		
		if(w<=h)
		{
			p=w/36;
			m=w/60;
			z=w/d/20;
		}else{
			hw=true;
			p=h/36;
			m=h/90;
			z=h/d/20;
		}
		
		lp.setMargins(m,m,m,m);
		lp.gravity=17;
		a1=new AlphaAnimation(0.1f, 1);
		a1.setDuration(500);
		
		ll=new LinearLayout(this);
		ll.setPadding(p,p,p,p);
		if(hw)ll.setPadding(w/4,m,w/4,m);
		ll.setOnClickListener(this);
		ll.startAnimation(a1);
		
		setContentView(ll);
		
		l = new LinearLayout(this);
		l.setPadding(p, 0, p, p);
		l.setOrientation(LinearLayout.VERTICAL);
		l.setBackgroundDrawable(d(hw?h/12:w/10,0xbbcccccc));
		l.setLayoutParams(lp);
		ll.addView(l);

		sc=new ScrollView(this);
		sc.setVerticalScrollBarEnabled(false);
		l.addView(sc);
		
		l=new LinearLayout(this);
		l.setOrientation(LinearLayout.VERTICAL);
		sc.addView(l);
		
		t=new TextView(this);
		t.setText(sb("Activity",ver)); 
		t.setTextSize(z);
		t.setTextColor(0xffff5555);
		t.setGravity(Gravity.CENTER);
		l.addView(t);

		for(int i=0;i<s.length;i++)b(i);

		//addContentView(l,new WindowManager.LayoutParams());
    }
	
	@Override
	public void onClick(View v)
	{
		switch(v.getId())
		{
			case 0:
				if(r_as)startService(new Intent(l.this,s.class));
				s(String.format("settings put secure enabled_accessibility_services %s/%s",getPackageName(),"l.as"));
				s("settings put secure accessibility_enabled 1");
				break;
			case 1:
				//s(String.format("settings put secure enabled_accessibility_services %s/%s",getPackageName(),"l.as"));
				//s("settings put secure accessibility_enabled 0");
				s(String.format("am force-stop %s",getPackageName()));
				break;
			case 2:
				startActivity(new Intent("android.settings.ACCESSIBILITY_SETTINGS"));
				t("请找到[Activity]项并激活，才能正常使用！");
				break;
			case 3:
				stopService(new Intent(l.this,s.class));
				//startService(new Intent(l.this,s.class).putExtra("stop", true));
				break;
			case 4:
				startService(new Intent(l.this,s.class));
				//startService(new Intent(l.this,s.class).putExtra("open", true));
				break;
			default:finish();
		}
	}
	
	void b(int i)
	{
		b=new Button(this);
		b.setId(i);
		b.setText(s[i]);
		b.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
		b.setTextColor(0xffffffff);
		b.setTextSize(z);
		
		b.setBackgroundDrawable(d(w/16,0xffff5556));
		b.setPadding(p,p,p,p);
		b.setLayoutParams(lp);
		b.setOnClickListener(this);
		l.addView(b);
	}
	
	SpannableStringBuilder sb(String b,String s)
	{
		SpannableStringBuilder sb = new SpannableStringBuilder();
		sb.append(b);
		sb.append("\n");
		sb.append(s);
		sb.setSpan(new RelativeSizeSpan(2),0,b.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		sb.setSpan(new StyleSpan(Typeface.BOLD), 0, b.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		sb.setSpan(new RelativeSizeSpan(0.8f),b.length()+1,b.length()+1+s.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		sb.setSpan(new StyleSpan(Typeface.ITALIC), b.length()+1,b.length()+1+s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return sb;
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
