package com.mfy.base;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gyf.immersionbar.ImmersionBar;
import com.shenghui.base.R;
import com.yanzhenjie.permission.AndPermission;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.ButterKnife;


public abstract class MY_BaseAppCompatActivity extends AppCompatActivity {
	private static final int REQUEST_CODE_SETTING = 1;
	/**
	 * 初始化布局id
	 */
	public int R_layout_id;
	/**
	 * 判断是否需要登录，初始化为false
	 */
	public boolean Request_login = false;
	private int totalCount = 0;

	private View mDecorView;

	protected ImmersionBar mImmersionBar;

	public MY_BaseAppCompatActivity() {
		super();
	}


	@SuppressLint("SourceLockedOrientationActivity")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(getLayoutResId());
		ButterKnife.bind(this);
		if (regiestEventBus()){
			EventBus.getDefault().register(this);
		}

		if (isImmersionBarEnabled())
			initImmersionBar();

		MY_initLayoutParams(); // 初始化页面布局,适配页面
		MY_initTooBar();
		MY_init(savedInstanceState);// 初始化数据
		MY_initWidgetAction();// 初始化控件事件

		MyActivityManager.getInstance().addActivity(this);
	}

	protected boolean regiestEventBus() {
		return false;
	}

	/**
	 * 返回当前Activity布局文件的id
	 *
	 * @return
	 *
	 */
	protected abstract int getLayoutResId();

	/**
	 * 初始化控件事件
	 */
	public abstract void MY_initWidgetAction();

	/**
	 * 初始化屏幕适配，若无适配，则无需重写
	 */
	public abstract void MY_initLayoutParams();

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

//	/**
//	 * 判断是否有网
//	 *
//	 * @return
//	 */
//	protected boolean MY_isNetWork() {
//		return Tools.isNetworkAvailable(getApplicationContext());
//	}

	/**
	 * 主函数体
	 */
	public void MY_activity_to_do() {
		Log.v("MY_activity_to_do", "this activity is in the main todo");
	}

	protected abstract void MY_initTooBar();

	/**
	 * 初始化页面数据
	 * @param savedInstanceState
	 */
	protected abstract void MY_init(Bundle savedInstanceState);

	/**
	 * /** 判断是否需要登录
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
		Toast.makeText(getApplicationContext(), "网络无连接，请检查！",
				Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onDestroy() {
		// 当当前activity被回收时，将activity从activityManager移除
		if (regiestEventBus()){
			EventBus.getDefault().unregister(this);
		}
		MyActivityManager.getInstance().removeActivity(this);

		super.onDestroy();
//		if (mImmersionBar != null)
//			mImmersionBar.destroy();  //在BaseActivity里销毁
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	public void onResume() {
		super.onResume();
	}

	public void onPause() {
		super.onPause();
	}

	/**
	 * activity 出栈
	 */
	@Override
	public void finish() {
		MyActivityManager.getInstance().removeActivity(this);
		super.finish();
	}

	public void mStartActivity(Class<?> intentActivity, Bundle bundle) {
		Intent intent = new Intent(this, intentActivity);
		intent.putExtras(bundle);
		super.startActivity(intent);
	}

	public void mStartActivity(Class<?> intentActivity) {

		Intent intent = new Intent(this, intentActivity);
		super.startActivity(intent);
	}

	public void mStartActivityForResult(Class<?> intentActivity, int requestCode) {
		Intent intent = new Intent(this, intentActivity);
		super.startActivityForResult(intent, requestCode);
	}

	public void mStartActivityForResult(Class<?> intentActivity,
										int requestCode, Bundle bundle) {
		Intent intent = new Intent(this, intentActivity);
		intent.putExtras(bundle);
		super.startActivityForResult(intent, requestCode);
	}



	/**
	 * 返回
	 */
	public void onClickBack(View view) {
		finish();
	}



	/**
	 * 显示Toast
	 */
	public void showToast(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}

	public void showToast(int strId) {
		Toast.makeText(this, strId, Toast.LENGTH_SHORT).show();
	}

	protected void initImmersionBar() {
		//在BaseActivity里初始化
		mImmersionBar = ImmersionBar.with(this)
				.statusBarColor(getStatusColor())
				.fitsSystemWindows(true).keyboardEnable(true);

		mImmersionBar.init();
	}

	protected abstract int getStatusColor();


	/**
	 * 是否可以使用沉浸式
	 * Is immersion bar enabled boolean.
	 *
	 * @return the boolean
	 */
	protected boolean isImmersionBarEnabled() {
		return true;
	}



	public void requestPermission(String permission,PermissionDeniedCallBack callBack){
		AndPermission.with(this)
				.runtime()
				.permission(permission)
				.rationale((context, permissions, executor) -> {
					new AlertDialog.Builder(context)
							.setCancelable(false)
							.setTitle("提示")
							.setMessage("请授予权限")
							.setPositiveButton("确定", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									executor.execute();
								}
							}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							executor.cancel();
						}
					}).show();
				}).onGranted(permissions -> {
			if (AndPermission.hasPermissions(MY_BaseAppCompatActivity.this,permission)){
				callBack.callBack();
			}
		}).onDenied(permissions ->{
			if (AndPermission.hasAlwaysDeniedPermission(MY_BaseAppCompatActivity.this, permissions)) {
				if (AndPermission.hasAlwaysDeniedPermission(MY_BaseAppCompatActivity.this, permissions)) {
					//true，弹窗再次向用户索取权限
					showSettingDialog(MY_BaseAppCompatActivity.this, permissions);
				}
			} }).start();

	}

	public PermissionDeniedCallBack callBack;

	public PermissionDeniedCallBack setPermissionDeniedCallBack(PermissionDeniedCallBack callBack){
		this.callBack = callBack;
		return callBack;
	}

	public interface PermissionDeniedCallBack{
		void callBack();
	}


	/**
	 * Display setting dialog.
	 */
	public void showSettingDialog(Context context, final List<String> permissions) {

		new AlertDialog.Builder(context).setCancelable(false)
				.setTitle("请授与权限")
				.setMessage("请在设置中授予权限")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						setPermission();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				}).show();
	}

	/**
	 * Set permissions.
	 */
	private void setPermission() {
		AndPermission.with(this).runtime().setting().start(REQUEST_CODE_SETTING);
	}



	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case REQUEST_CODE_SETTING: {
				break;
			}
		}
	}


}
