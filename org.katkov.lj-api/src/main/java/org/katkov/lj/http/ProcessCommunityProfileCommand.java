package org.katkov.lj.http;

import org.katkov.lj.LJTimeoutException;

public class ProcessCommunityProfileCommand extends ProcessProfileCommand {
    private int timeout;
    private long startTime;

    public UserProfile execute(String name, int timeout) throws Exception {
        this.timeout = timeout;
        String profileURL = "http://community.livejournal.com/" + name + "/profile?mode=full";
        String blogURL = "http://community.livejournal.com/" + name + "/";
        startTime = System.currentTimeMillis();
        return execute(name, profileURL, blogURL, timeout);
    }

    public String[] getFriends(String userName, String content) throws Exception {
        //TODO: implement this
        return new String[]{};
    }


    String[] getFriendOfs(String userName, String content) throws Exception {
        String uri = "http://www.livejournal.com/misc/fdata.bml?user=" + userName + "&comm=1";
        int timeoutValue;
        if (timeout == 0) {
            timeoutValue = 0;
        } else {
            timeoutValue = timeout - (int) (System.currentTimeMillis() - startTime);
        }
        if (timeoutValue < 0) {
            throw new LJTimeoutException("Time has expired");
        }

        String newContent = fetchURLContent(uri, timeoutValue);
        String[] strings = new FriendNamesExtractor(true).extract(newContent);
        newContent = null;
        return strings;
    }
}
