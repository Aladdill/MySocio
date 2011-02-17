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

package org.ljtoolkit.params;

import java.util.Date;

import org.ljtoolkit.enums.SelectType;

/**
 * This class is used to hold the parameters for getting a list of events
 * from Live Journal. An instance of this class needs to be created and
 * the state set before calling the LiveJournalService getEvents() method.
 * 
 * For example, the following code snippet instantiates a GetEventParams
 * object and sets its state so the last 50 events will be returned with
 * each event's content truncated to 10 characters.
 * 
 * GetEventParams params = new GetEventParams();
 * params.setSelectType(SelectType.LAST_N);
 * parmas.setHowMany(50);
 * params.setTruncate(10);
 * params.setLineEndings(BaseEventParams.PC_LINE_ENDING);
 * 
 *   
 * @author Troy Bourdon
 *
 */
public class GetEventParams extends BaseEventParams {

	private int truncate = 0;
	private boolean preferSubject;
	private boolean noMetadataProperties;
	private SelectType selectType;
	private Date lastSynchDate;
	private Date dayDate;
	private int howMany = 20;
	private Date beforeDate;
	private int itemId;
	
	public int getTruncate() {
		return truncate;
	}

    /**
     * 
     * An optional value that if greater than or equal to 4, truncates the length of the
     * returned events (after being decoded) to the value specified. Entries less than or
     * equal to this length are left untouched. Values greater than this length are truncated
     * to the specified length minus 3, and then have "..." appended to them, bringing the
     * total length back up to what you specified. This is good for populating list boxes
     * where only the beginning of the entry is important, and you'll double-click it to
     * bring up the full entry.
     * 
     * @param truncate
     */
	public void setTruncate(int truncate) {
		this.truncate = truncate;
	}


	/**
	 * Returns whether or not the preferSubject flag is set.
	 * 
	 * @return preferSubject
	 */
	public boolean isPreferSubject() {
		return preferSubject;
	}

	/**
	 * If this setting is set to true, then no subjects are returned, and the events
	 * are actually subjects if they exist, or if not, then they're the real events. This
	 * is useful when clients display history and need to give the user something to double-click.
	 * The subject is shorter and often more informative, so it'd be best to download only this.
	 * 
	 * @param preferSubject
	 */
	public void setPreferSubject(boolean preferSubject) {
		this.preferSubject = preferSubject;
	}

	/**
	 * Returns whether or not the noMetadataProperties flag is set.
	 * 
	 * @return noMetadataProperties
	 */
	public boolean isNoMetadataProperties() {
		return noMetadataProperties;
	}

	/**
	 * If this setting is set to true, then no meta-data properties are returned.
	 * 
	 * @param noMetadataProperties
	 */
	public void setNoMetadataProperties(boolean noMetadataProperties) {
		this.noMetadataProperties = noMetadataProperties;
	}

	/**
	 * Returns the SelectType for this object
	 * 
	 * @return selectType
	 */
	public SelectType getSelectType() {
		return selectType;
	}

	/**
	 * Determines how you want to specify what part of the journal to download. Valid values
	 * are day to download one entire day, lastn to get the most recent n entries
	 * (where n is specified in the howmany field), one to download just one specific entry,
	 * or syncitems to get some number of items (which the server decides) that have changed
	 * since a given time (specified in the lastsync parameter>). Not that because the server
	 * decides what items to send, you may or may not be getting everything that's changed.
	 * You should use the syncitems selecttype in conjunction with the syncitems protocol mode.
	 * 
	 * @param selectType
	 */
	public void setSelectType(SelectType selectType) {
		this.selectType = selectType;
	}

	/**
	 * Get the last synch date for this object
	 * 
	 * @return lastSynchDate
	 */
	public Date getLastSynchDate() {
		return lastSynchDate;
	}

	/**
	 * For a selecttype of syncitems, the date that you want to get updates since.
	 * 
	 * @param lastSynchDate
	 */
	public void setLastSynchDate(Date lastSynchDate) {
		this.lastSynchDate = lastSynchDate;
	}

	/**
	 * Get this object's dayDate setting.
	 * 
	 * @return dayDate
	 */
	public Date getDayDate() {
		return dayDate;
	}

	/**
	 * For a selectType of day, set this field with the date you want events for.
	 * 
	 * @param dayDate
	 */
	public void setDayDate(Date dayDate) {
		this.dayDate = dayDate;
	}

	/**
	 * Returns the homMany value for this object.
	 * 
	 * @return howMany
	 */
	public int getHowMany() {
		return howMany;
	}

	/**
	 * For a selecttype of lastn, how many entries to get. Defaults to 20. Maximum is 50.
	 * 
	 * @param howMany
	 */
	public void setHowMany(int howMany) {
		if(howMany > 50)
			this.howMany = 50;
		else
			this.howMany = howMany;
	}

	public Date getBeforeDate() {
		return beforeDate;
	}

	/**
	 * For a selecttype of lastn, you can optionally include this variable and restrict all
	 * entries returned to be before the date you specify.
	 * 
	 * @param beforeDate
	 */
	public void setBeforeDate(Date beforeDate) {
		this.beforeDate = beforeDate;
	}

	/**
	 * Return's the item id for this object.
	 * 
	 * @return itemId
	 */
	public int getItemId() {
		return itemId;
	}

	/**
	 * For a selecttype of one, the journal entry's unique ItemId for which you want to retrieve.
	 * Or, to retrieve the most recent entry, use the value -1. Using -1 has the added effect that
	 * the data is retrieved from the master database instead of a replicated slave. Clients with
	 * an "Edit last entry" feature might want to send -1, to make sure the data that comes back up
	 * is accurate, in case a slave database is a few seconds behind in replication.
	 * 
	 * @param itemId
	 */
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

}
