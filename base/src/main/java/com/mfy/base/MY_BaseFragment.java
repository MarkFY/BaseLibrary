package com.mfy.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gyf.immersionbar.ImmersionBar;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class MY_BaseFragment extends Fragment {

	/**
	 * 判断是否需要登录，初始化为false
	 */
	public boolean MY_request_login = false;
	public Activity activity;


	protected ImmersionBar mImmersionBar;

	public Unbinder unbinder;

	protected View mRootView;
	public MY_BaseAppCompatActivity mContext;

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		activity = (Activity) context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

		if (mRootView == null) {
			mRootView = inflater.inflate(getLayoutResId(), container, false);
		} else {
			ViewGroup viewGroup = (ViewGroup) mRootView.getParent();
			if (viewGroup != null) {
				viewGroup.removeView(mRootView);
			}
		}
		return mRootView;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		unbinder = ButterKnife.bind(this, view);
		MY_init(view);
		MY_initLayout(); // 初始化页面布局
		MY_initWidgetAction();// 初始化控件事件

		if (isImmersionBarEnabled())
			initImmersionBar();

	}

	/**
	 * 返回当前Activity布局文件的id
	 *
	 * @return
	 */
	protected abstract int getLayoutResId();



	/**
	 * 初始化控件事件
	 */
	public abstract void MY_initWidgetAction();

	/**
	 * 初始化Activity布局
	 */
	public void MY_initLayout() {
		MY_initLayoutParams();
	}

	/**
	 * 初始化屏幕适配，若无适配，则无需重写
	 */
	public abstract void MY_initLayoutParams();

//	/**
//	 * 判断是否有网
//	 *
//	 * @return
//	 */
//	protected boolean MY_isNetWork() {
//		return Tools.isNetworkAvailable(this.getActivity()
//				.getApplicationContext());
//	}

	/**
	 * 主函数体
	 */
	public void MY_activity_to_do() {

	}

	/**
	 * 初始化页面数据
	 */
	protected abstract void MY_init(View view);

	/**
	 * 判断是否需要登录
	 * 
	 * @return
	 */
	public boolean MY_is_login() {
		return false;
	}

	/**
	 * 如果需要网路，但是却没有网络应该做的操作
	 */
	public void MY_no_network_todo() {
		Toast.makeText(this.getActivity().getApplicationContext(),
				"网络无连接，请检查！", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		unbinder.unbind();
//		if (mImmersionBar != null)
//			mImmersionBar.destroy();
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mContext = (MY_BaseAppCompatActivity) activity;
	}

	public void mStartActivity(Class<?> intentActivity, Bundle bundle) {
		Intent intent = new Intent(activity, intentActivity);
		intent.putExtras(bundle);
		super.startActivity(intent);
	}

	public void mStartActivity(Class<?> intentActivity) {
		Intent intent = new Intent(activity, intentActivity);
		super.startActivity(intent);
	}

	public void mStartActivityForResult(Class<?> intentActivity, int requestCode) {
		Intent intent = new Intent(activity, intentActivity);
		super.startActivityForResult(intent, requestCode);
	}

	public void mStartActivityForResult(Class<?> intentActivity,
			int requestCode, Bundle bundle) {
		Intent intent = new Intent(activity, intentActivity);
		intent.putExtras(bundle);
		super.startActivityForResult(intent, requestCode);
	}



	protected void MY_init() {
		// TODO Auto-generated method stub

	}

	public void onBack(View v) {

	}


	/**
	 * 显示Toast
	 */
	public void showToast(String message) {
		Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
	}

	public void showToast(int strId) {
		Toast.makeText(activity, strId, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 是否在Fragment使用沉浸式
	 *
	 * @return the boolean
	 */
	protected boolean isImmersionBarEnabled() {
		return false;
	}

	/**
	 * 初始化沉浸式
	 */
	protected void initImmersionBar() {
		mImmersionBar = ImmersionBar.with(this);
		mImmersionBar.keyboardEnable(true).navigationBarWithKitkatEnable(false).init();
	}


	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
//		if (!hidden && mImmersionBar != null){}
//			mImmersionBar.init();
	}

}
