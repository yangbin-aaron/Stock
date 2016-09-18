package cn.bvin.lib.module.net.watch;

public interface NetworkWatcher {

	int NETWORK_TYPE_DISCONN = -1;
	int NETWORK_TYPE_OTHERCONN = 0;
	int NETWORK_TYPE_MOBLECONN = 1;
	int NETWORK_TYPE_WIFICONN = 2;
	
	void change(int type);
}
