//package com.hundsun.quote.widget;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Timer;
//
//import android.app.Activity;
//import android.content.Context;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.View;
//import android.view.Window;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.AdapterView.OnItemSelectedListener;
//import android.widget.ArrayAdapter;
//import android.widget.Spinner;
//
//public class CategoryRankingActivity extends Activity implements
//		AdapterView.OnItemSelectedListener, BaseRelativeLayout.OnClickListener {
//	public static Timer timer;
//	private QwScrollTableListView mCatRankListView;
//	private Spinner mCatRankSpinner;
//	public List<User> mCategoryRankingList;
//	Context context;
//	
//	protected void onCreate(Bundle paramBundle) {
//		super.onCreate(paramBundle);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		setContentView(R.layout.market_category_ranking);
//		context = CategoryRankingActivity.this;
//		initUI();
//		fillMarketListView(this.mCategoryRankingList);
//	}
//
//	private void fillMarketListView(List<User> userList) {
//		if((this.mCategoryRankingList == null)|| (this.mCategoryRankingList.size() <= 0)){
//			return;
//		}
//		this.mCatRankListView.setModel(userList);
//	}
//	
//	private void initUI() {
//		this.mCatRankSpinner = ((Spinner) findViewById(R.id.category_ranking_spinner));
//		this.mCatRankSpinner.setOnItemSelectedListener(this);
//		ArrayAdapter<String> localArrayAdapter = new ArrayAdapter<String>(this,R.layout.my_spinner_category, this.getResources()
//						.getStringArray(R.array.market_category_ranking));
//		localArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		this.mCatRankSpinner.setAdapter(localArrayAdapter);
//		mCatRankSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){
//
//			@Override
//			public void onItemSelected(AdapterView<?> arg0, View arg1,
//					int arg2, long arg3) {
//				if((mCategoryRankingList == null)|| (mCategoryRankingList.size() <= 0)){
//					return;
//				}
//				if(arg2 == 1){
//					mCatRankListView.setModel(getData2());
//				}else{
//					mCatRankListView.setModel(getData());
//				}
//				
//				
//			}
//
//			@Override
//			public void onNothingSelected(AdapterView<?> arg0) {
//				// TODO Auto-generated method stub
//				
//			}});
////		mCatRankSpinner.setOnItemClickListener(new OnItemClickListener(){
////
////			@Override
////			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
////				if((mCategoryRankingList == null)|| (mCategoryRankingList.size() <= 0)){
////					return;
////				}
////				mCatRankListView.setModel(getData2());
////				
////			}});
//		this.mCatRankListView = ((QwScrollTableListView) findViewById(R.id.market_list_view));
//		this.mCatRankListView.setOnClickListener(this);
//		this.mCategoryRankingList=getData();
//		String[] titles={"我的名称","我的性别","我的年龄","我的角色","我的地区","我的班级","我的老师","我的得分"};
//		this.mCatRankListView.setTitle(titles);
//	}
//	
//	public void OnContentItemClickListener(View paramView) {
//	}
//
//	public void OnTitleClickListener(View paramView) {
//	}
//
//	public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
//		return super.onKeyDown(paramInt, paramKeyEvent);
//	}
//
//	protected void onResume() {
//		super.onResume();
//		//startService(new Intent(getApplicationContext(),StockMarqueeService.class));
//	}
//
//	protected void onStop() {
//		super.onStop();
//	}
//
//	@Override
//	public void onClick(View paramView) {
//		Log.i("test", "clicked="+paramView);
//	}
//	
//	/**
//	 * 头部下拉列表选择
//	 */
//	public void onItemSelected(AdapterView<?> paramAdapterView, View paramView,
//			int paramInt, long paramLong) {
//	}
//	@Override
//	public void onNothingSelected(AdapterView<?> view) {
//	}
//	
//	private List<User> getData(){
//		List<User> userList=new ArrayList<User>();
//		User user=null;
//		for(int i=0;i<60;i++){
//			user=new User();
//			user.setId(i);
//			user.setName(""+i);
//			user.setArea("北京");
//			user.setClassName(""+i%2+"班");
//			user.setGrade(""+i%2+"分");
//			user.setAge(""+i%2+"谁");
//			user.setRole(""+(i%2==0));
//			user.setSex(i%2==0?"汉子":"抠脚");
//			user.setTeacher("轰炸");
//			userList.add(user);
//		}
//		return userList;
//	}
//	
//	private List<User> getData2(){
//		List<User> userList=new ArrayList<User>();
//		User user=null;
//		for(int i=0;i<60;i++){
//			user=new User();
//			user.setId(i);
//			user.setName(""+i);
//			user.setArea("梁浩");
//			user.setClassName(""+i%2+"AAAAA");
//			user.setGrade(""+i%2+"BBBBB");
//			user.setAge(""+i%2+"CCCCC");
//			user.setRole(""+(i%2==0));
//			user.setSex(i%2==0?"SFEFE":"EEEE");
//			user.setTeacher("AAAAAAAA");
//			userList.add(user);
//		}
//		return userList;
//	}
//}