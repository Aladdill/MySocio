package org.katkov.lj.http;

public class ProcessUserProfileCommand extends ProcessProfileCommand {
    Extractor<String[]> friendsExtractor = new LiveJournalProfileFriendsExtractor("Friends");
    Extractor<String[]> friendOfsExtractor = new LiveJournalProfileFriendsExtractor("Friend of");

    public UserProfile execute(String name, int timeout) throws Exception {
        String profileURL = "http://" + name + ".livejournal.com/profile?mode=full";
        String blogURL = "http://" + name + ".livejournal.com";
        return execute(name, profileURL, blogURL, timeout);
    }

    String[] getFriends(String userName, String content) throws Exception {
        return friendsExtractor.extract(content);
    }

    String[] getFriendOfs(String userName, String content) throws Exception {
        return friendOfsExtractor.extract(content);
    }
}
