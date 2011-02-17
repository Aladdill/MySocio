/*
 * Copyright 2008 Troy Bourdon
 * 
 * This file is part of LJToolkit.
 *
 * LJToolkit is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * LJToolkit is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with LJToolkit.  If not, see <http://www.gnu.org/licenses/>.    
 */

package org.ljtoolkit.data;

import java.util.BitSet;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ljtoolkit.enums.AccountStatus;
import org.ljtoolkit.enums.AccountType;

/**
 * Class that represents a Live Journal Friend.
 * 
 * @author Troy Bourdon
 *
 */
public class Friend {
	private String user;
	private String name;
	private Date birthday;
	private String bgColor;
	private String fgColor;
	private BitSet groupMask;
	private AccountType accountType;
	private AccountStatus accountStatus;

	private Log logger = LogFactory.getLog(this.getClass());

	/**
	 * Gets the user name for this friend
	 * @return usr
	 */
	public String getUser() {
		return user;
	}
	
	/**
	 * Sets the user name for this friend
	 * @param user
	 */
	public void setUser(String user) {
		this.user = user;
	}
	
	/**
	 * Gets the full name for this friend
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the full name for this friend
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * The friend's birthday. Note that this is only returned if the user has set their info to visible and if they
	 * have set a birthday, otherwise this key is skipped. You will also need to set includebdays to 1 when you make
	 * the request in order to receive this field
	 * @return birthday
	 */
	public Date getBirthday() {
		return birthday;
	}
	
	/**
	 * Sets the birthday of this friend
	 * @param birthday
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	/**
	 * Gets the background color for this friend
	 * @return The background color for this friend
	 */
	public String getBgColor() {
		return bgColor;
	}
	
	/**
	 * Sets the background color for this friend
	 * @param bgColor
	 */
	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}
	
	/**
	 * Gets the foreground color for this friend
	 * @return fgColor
	 */
	public String getFgColor() {
		return fgColor;
	}
	
	/**
	 * Sets the foreground color for this friend
	 * @param fgColor
	 */
	public void setFgColor(String fgColor) {
		this.fgColor = fgColor;
	}
	
	/**
	 * If the group mask is not "1" (just bit 0 set), then this variable is returned with an 32-bit unsigned integer with
	 * a bit 0 always set, and bits 1-30 set for each group this friend is a part of. Bit 31 is reserved.
	 * 
	 * @return The group mask BitSet
	 */
	public BitSet getGroupMask() {
		return groupMask;
	}
	
	/**
	 * Sets the group mask for this friend
	 * @param groupMask
	 */
	public void setGroupMask(BitSet groupMask) {
		this.groupMask = groupMask;
	}
	
	/**
	 * Helper setter to convert String of 1's & 0's to BitSet
	 * @param groupMask
	 */
	public void setGroupMask(String groupMask) {
		if(groupMask != null) {
			char[] bitChars = groupMask.toCharArray();
			BitSet bits = new BitSet(32);

			for(int i = 0; i < bitChars.length; i++) {
				if(bitChars[i] == '1') bits.set(i);
			}
			
			setGroupMask(bits);
		}
	}
	
	/**
	 * The account type of this friend, if it's not a personal account. Currently the only possible values are
	 * "community" and "syndicated", which means you're monitoring this community/syndicated feed on your friends list.
	 * (Note that it does not imply that you're a member of the community if the type is community, just that you monitor it.)
	 * @return accountType
	 */
	public AccountType getAccountType() {
		return accountType;
	}
	
	/**
	 * Sets the accountType for this friend
	 * @param accountType
	 */
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}
	
	/**
	 * Helper setter to convert String to AccountType
	 * @param accountType
	 */
	public void setAccountType(String accountType) {
		if(accountType != null) {
			if(accountType.equalsIgnoreCase("community")) {
				setAccountType(AccountType.COMMUNITY);
			} else if(accountType.equalsIgnoreCase("syndicated")) {
				setAccountType(AccountType.SYNDICATED);
			} else {
				// shouldn't be here
				assert(false);
			}			
		}
	}
	
	/**
	 * The status of this user. If this field is absent, the user has a normal active account. Otherwise the currently
	 * possible values for this field are "deleted", "suspended" and "purged"
	 * @return accountStatus
	 */
	public AccountStatus getAccountStatus() {
		return accountStatus;
	}
	
	/**
	 * Sets the accountStatus for this friend
	 * @param accountStatus
	 */
	public void setAccountStatus(AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
	}	
	
	/**
	 * Helper setter to convert from a String value to AccountStatus
	 * @param accountStatus
	 */
	public void setAccountStatus(String accountStatus) {
		if(accountStatus != null) {
			if(accountStatus.equalsIgnoreCase("deleted")) {
				setAccountStatus(AccountStatus.DELETED);
			} else if(accountStatus.equalsIgnoreCase("suspended")) {
				setAccountStatus(AccountStatus.PURGED);
			} else if(accountStatus.equalsIgnoreCase("suspended")) {
				setAccountStatus(AccountStatus.SUSPENDED);
			} else {
				// shouldn't be here
				logger.error("Encountered an invalid account status");
				assert(false);
			}
		}
	}
}
