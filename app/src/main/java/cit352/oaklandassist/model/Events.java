package cit352.oaklandassist.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class Events {


    long id;
    String buildingEventName;
    String buildingEventTitle;
    String eventTime;
    String eventSpecificLocation;
    String eventDetails;

    public Events() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBuildingEventName() {
        return buildingEventName;
    }

    public void setBuildingEventName(String buildingEventName) {
        this.buildingEventName = buildingEventName;
    }

    public String getBuildingEventTitle() {
        return buildingEventTitle;
    }

    public void setBuildingEventTitle(String buildingEventTitle) {
        this.buildingEventTitle = buildingEventTitle;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventSpecificLocation() {
        return eventSpecificLocation;
    }

    public void setEventSpecificLocation(String eventSpecificLocation) {
        this.eventSpecificLocation = eventSpecificLocation;
    }

    public String getEventDetails() {
        return eventDetails;
    }

    public void setEventDetails(String eventDetails) {
        this.eventDetails = eventDetails;
    }
}
