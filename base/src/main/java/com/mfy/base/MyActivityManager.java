package com.mfy.base;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * 管理activity栈
 */
public class MyActivityManager {

	private List<Activity> activityList = new LinkedList<Activity>();
	private static MyActivityManager instance;

	private MyActivityManager() {
	}

	/**
	 * 单例模式中获取唯一的AbActivityManager实例.
	 * 
	 * @return
	 */
	public static MyActivityManager getInstance() {
		if (null == instance) {
			instance = new MyActivityManager();
		}
		return instance;
	}

	/**
	 * 添加Activity到容器中.
	 * 
	 * @param activity
	 */
	public void addActivity(Activity activity) {
		if (activity != null) {
			activityList.add(activity);
		}
	}

	/**
	 * 移除Activity从容器中.
	 * 
	 * @param activity
	 */
	public void removeActivity(Activity activity) {
		if (activity != null) {
			activityList.remove(activity);
		}
	}

	/**
	 * 遍历所有Activity并finish.
	 */
	public void clearAllActivity() {
		// 避免在使用过程中用到类似锁屏页面直接退出时候，导致迭代器遍历的时候数据结构错误报错
		activityList.clear();
	}
}