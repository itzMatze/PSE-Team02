package edu.kit.mensameet.client.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Pair;

import edu.kit.mensameet.client.model.Group;
import edu.kit.mensameet.client.model.MensaMeetTime;


public class CreateGroupViewModel extends MensaMeetViewModel {
    private MutableLiveData<Group> group;
    private MutableLiveData<String> groupName;
    private MutableLiveData<String> Motto;
    //todo: test
    private MutableLiveData<String> time;
    private MutableLiveData<Integer> number;
    private SingleLiveEvent<Pair<CreateGroupViewModel, String>> uiEventLiveData;


    //todo: how to save creater to User List of Group?
    public void saveGroups(LiveData<Group> group) {

    }
    public void onCreateGroupClick(CreateGroupViewModel item) {
        uiEventLiveData.setValue(new Pair<CreateGroupViewModel, String>(item, "createGroup"));
    }

    public void gotoSelectGroup() {
        //todo
    }

    /*
    following getter are used for data-binding
     */
    public MutableLiveData<String> getGroupName() {
        return groupName;
    }

    public MutableLiveData<String> getMotto() {
        return Motto;
    }

    public MutableLiveData<String> getTime() {
        if(time == null){
            time = new MutableLiveData<String>();
            time.setValue("");
        }
        return time;
    }

    public MutableLiveData<Integer> getNumber() {
        if(number == null){
            number = new MutableLiveData<>();
            number.setValue(0);
        }
        return number;
    }

    public SingleLiveEvent<Pair<CreateGroupViewModel, String>> getUiEventLiveData() {
        if(uiEventLiveData == null){
            uiEventLiveData = new SingleLiveEvent<>();
            uiEventLiveData.setValue(new Pair<CreateGroupViewModel, String>(null, "default"));
        }
        return uiEventLiveData;
    }
}
