package edu.kit.mensameet.client.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import java.sql.Time;
import edu.kit.mensameet.client.model.MensaMeetTime;

public class SetTimeViewModel extends MensaMeetViewModel {

    private MutableLiveData<Integer> hour = new MutableLiveData<Integer>();
    private MutableLiveData<Integer> minute = new MutableLiveData<Integer>();
    private MutableLiveData<MensaMeetTime> time;

    /*
     this method is for testing saveTime method
     todo: delete if it's no longer needed or added to changes
     */
    public LiveData<MensaMeetTime> getTime() {
        if(time == null){
            time = new MutableLiveData<>();
            time.setValue(new MensaMeetTime(new Time(0)));
        }
        return time;
    }

    public void setTime(LiveData<MensaMeetTime> time) {
        this.time = (MutableLiveData)time;
    }

    /*
     because android:hour and android:minute returns integer values,
     it's inconvenient to use other constructor.
     */
    public void saveTime() {
        time.setValue(new MensaMeetTime(new Time(hour.getValue(), minute.getValue(),0)));
    }

    //to help data-binding
    public MutableLiveData<Integer>  getHour(){
        return hour;
    }

    public MutableLiveData<Integer>  getMinute() {
        return minute;
    }
}
