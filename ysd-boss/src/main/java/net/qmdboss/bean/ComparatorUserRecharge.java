package net.qmdboss.bean;

import net.qmdboss.entity.UserAccountRecharge;

import java.util.Comparator;

public class ComparatorUserRecharge implements Comparator<UserAccountRecharge> {
	public int compare(UserAccountRecharge user0, UserAccountRecharge user1) {
		int flag = user0.getCompareStatus().compareTo(user1.getCompareStatus());
		return flag;
	}
}
