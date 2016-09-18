package cn.bvin.lib.module.net.watch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.LocalBroadcastManager;

import java.util.ArrayList;
import java.util.List;

public class NetworkHelp {

	private IntentFilter filter = null;//监听网络广播的过滤action
	private List<NetworkWatcher> watchers = null;
	
	static NetworkWatcher watch;
	
	static NetworkHelp helper;
	
	
	public static NetworkHelp getNetworkHelper(){
		if(helper==null){
			helper =  new NetworkHelp();
		}
		return helper;
	}
	
	private NetworkHelp() {
		super();
		// TODO Auto-generated constructor stub
		watchers = new ArrayList<NetworkWatcher>();
		filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
	}

	
	/**
	 * Register watcher.
	 *
	 * @param watch the watch
	 */
	public void registerWatcher(NetworkWatcher watch){//添加网络监听
		watchers.add(watch);
	}
	
	/**
	 * Removes the watcher.
	 *
	 * @param watch the watch
	 */
	public void removeWatcher(NetworkWatcher watch){//去除网络监听
		if(watchers.contains(watch)){
			watchers.remove(watchers.indexOf(watch));
		}
		
	}
	
	/**
	 * Removes the all.
	 */
	public void removeAll(){//去除所有网络监听
		for (NetworkWatcher watch: watchers) {
			watchers.remove(watch);
		}
	}
	
	/**
	 * Notifaction
	 * 当网络发生变化就通知观察者
	 */
	private void notif(int NetworkType){
		for (NetworkWatcher watch: watchers) {
			watch.change(NetworkType);
		}
	}
	
	/*广播*/
	
	private BroadcastReceiver listner = new BroadcastReceiver(){
		@Override
		public void onReceive(Context context, Intent intent) {
			notif(getNetworkType(context));
		}
	};
	
	private int getNetworkType(Context context){
		ConnectivityManager connm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connm.getActiveNetworkInfo();
		if(info!=null){
			if(info.getType()== ConnectivityManager.TYPE_WIFI){
				return NetworkWatcher.NETWORK_TYPE_WIFICONN;
			}else if(info.getType()== ConnectivityManager.TYPE_MOBILE){
				return NetworkWatcher.NETWORK_TYPE_MOBLECONN;
			}else{
				return NetworkWatcher.NETWORK_TYPE_OTHERCONN;
			}
		}else{//断网了
			return NetworkWatcher.NETWORK_TYPE_DISCONN;
		}
		
	}
	
	/**判断有没有网络*/
	public boolean isNetworkAvailable(Context context) {
		ConnectivityManager connm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connm.getActiveNetworkInfo();
		return info!=null&&info.isAvailable();
	}
	
	
	
	public void registerReceiver(Context ctx){//注册广播接收器
		LocalBroadcastManager.getInstance(ctx).registerReceiver(listner,filter);
	}
	
	public void unRegisterReceiver(Context ctx){//注销广播接收器
		LocalBroadcastManager.getInstance(ctx).unregisterReceiver(listner);
	}
}
