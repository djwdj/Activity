package l.Activity;

import android.app.*;
import android.os.*;
import android.content.*;
import android.widget.*;

public class l extends Activity 
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        startActivity(new Intent("android.settings.ACCESSIBILITY_SETTINGS"));
		Toast.makeText(this,"找到Activity,打开即可用\n关闭也是在这里操作！",100).show();
		finish();
		
    }
}
