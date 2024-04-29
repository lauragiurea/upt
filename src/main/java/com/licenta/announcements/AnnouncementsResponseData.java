package com.licenta.announcements;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class AnnouncementsResponseData {

    @JsonProperty("count")
    public int count;

    @JsonProperty("announcements")
    public List<AnnouncementData> announcements;

    public AnnouncementsResponseData() {
        this.count = 0;
        this.announcements = new ArrayList<>();
    }

    public AnnouncementsResponseData(List<AnnouncementData> announcements) {
        this.count = announcements.size();
        this.announcements = announcements;
    }
}
