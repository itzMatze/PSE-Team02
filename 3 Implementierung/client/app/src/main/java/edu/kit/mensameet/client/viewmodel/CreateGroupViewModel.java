package edu.kit.mensameet.client.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Pair;

import java.sql.Time;

import edu.kit.mensameet.client.model.Group;
import edu.kit.mensameet.client.model.MensaMeetTime;

/**
 * Class {@code CreateGroupViewModel} is responsible for preparing and managing the data for {@code CreateGroupView} Activity.
 * It also handles the communication of the Activity with the rest of the application
 */
public class CreateGroupViewModel extends MensaMeetViewModel {
    //todo: move this later to MensaMeetViewModel
    public static final String CREATE_GROUP_ID = "createGroup";
    public static final String CREATE_GROUP_ADD_TIME_ID = "createGroupAddTime";
    public static final String CREATE_GROUP_TO_SELECT_GROUP_ID = "createGroupToSelectGroup";

    private MutableLiveData<Group> group;
    private MutableLiveData<String> groupName;
    private MutableLiveData<String> motto;
    public MutableLiveData<MensaMeetTime> time;
    private MutableLiveData<String> timeString;
    private MutableLiveData<Integer> number;
    private SingleLiveEvent<Pair<CreateGroupViewModel, String>> uiEventLiveData;//A lifecycle-aware observable that sends only new updates after subscription

    /*
    TODO: edit in order to save group
     */
    public void saveGroups(LiveData<Group> group) {

    }

    /**
     * onClick method for create Group Button
     * @param item CreateGroupViewModel
     */
    public void onCreateGroupClick(CreateGroupViewModel item) {
        uiEventLiveData.setValue(new Pair<>(item, CREATE_GROUP_ID));
    }

    /**
     * onClick method for add time Button
     * @param item CreateGroupViewModel
     */
    public void onAddTimeClick(CreateGroupViewModel item) {
        uiEventLiveData.setValue(new Pair<>(item, CREATE_GROUP_ADD_TIME_ID));
    }

    /**
     * onClick method for select Group Button
     * @param item CreateGroupViewModel
     */

    public void onSelectGroupClick(CreateGroupViewModel item) {
        uiEventLiveData.setValue(new Pair<>(item, CREATE_GROUP_TO_SELECT_GROUP_ID));
    }

    /**
     * set time and timeString from a given String
     * @param time a string in JDBC time in format "hh:mm:ss"
     */
    public void addTime(String time){
        if (this.time == null){
            this.time = new MutableLiveData<MensaMeetTime>();
        }
        this.time.setValue(new MensaMeetTime(Time.valueOf(time)));
        timeString.setValue("gewählte Zeit:" + "\n" + this.time.getValue().getStartTime());
    }


    /*
    following getter are used for data-binding
     */

    /**
     * @return  MensaMeet group name
     */
    public MutableLiveData<String> getGroupName() {
        if (groupName == null){
            groupName = new MutableLiveData<>();
            groupName.setValue("");
        }
        return groupName;
    }

    /**
     * @return MensaMeet group motto
     */
    public MutableLiveData<String> getMotto() {
        if (motto == null){
            motto = new MutableLiveData<>();
            motto.setValue("");
        }
        return motto;
    }

    /**
     * @return start time of MensaMeet group.
     * if it's not set, return "Zeitpunkt noch nicht gewählt"
     */
    public MutableLiveData<MensaMeetTime> getTime() {
        return time;
    }

    public MutableLiveData<String> getTimeString(){
        if (timeString == null){
            timeString = new MutableLiveData<>();
            timeString.setValue("Zeitpunkt noch nicht gewählt");
            return timeString;
        }
        else {
            return timeString;
        }
    }

    /**
     * @return maximal number of group members
     */
    public MutableLiveData<Integer> getNumber() {
        if(number == null){
            number = new MutableLiveData<>();
            number.setValue(0);
        }
        return number;
    }

    /**
     * @return A lifecycle-aware observable that sends only new updates after subscription
     */
    public SingleLiveEvent<Pair<CreateGroupViewModel, String>> getUiEventLiveData() {
        if(uiEventLiveData == null){
            uiEventLiveData = new SingleLiveEvent<>();
            uiEventLiveData.setValue(new Pair<CreateGroupViewModel, String>(null, "default"));
        }
        return uiEventLiveData;
    }
}
