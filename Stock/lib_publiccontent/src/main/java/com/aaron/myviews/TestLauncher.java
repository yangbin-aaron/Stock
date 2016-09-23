package com.aaron.myviews;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * 快捷测试启动类（暂时没有发现有启动）
 * @author bvin
 *
 */
public class TestLauncher {

	private Context context;

	public TestLauncher(Context context) {
		super();
		this.context = context;
	}
	
	public void launch(LaunchMeta launchMeta) {
		Intent intent = new Intent(context, launchMeta.clazz);
		quickLaunch(intent,launchMeta.clazz,launchMeta.extras);
	}
	
	public void quickLaunch(Intent intent,Class<?> clazz,Bundle extras) {
		if (intent==null||clazz==null) {
			throw new IllegalArgumentException("intent and clazz must not null");
		}
		if (extras!=null&&!extras.isEmpty()) {
			intent.putExtras(extras);
		}
		if (Activity.class.isAssignableFrom(clazz)) {
			context.startActivity(intent);
		} else if (Service.class.isAssignableFrom(clazz)) {
			context.startService(intent);
		} else if (BroadcastReceiver.class.isAssignableFrom(clazz)) {
			context.sendBroadcast(intent);
		}
	}
	
	public static class LaunchMeta{
		Class<?> clazz;
		Bundle extras;
		public LaunchMeta(Class<?> clazz, Bundle extras) {
			super();
			this.clazz = clazz;
			this.extras = extras;
		}
		
	}
}
