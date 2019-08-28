package edu.kit.mensameet.client.view;

import android.content.Context;

import edu.kit.mensameet.client.model.IdEnum;

public class SpinnerItem {

    private String representation;
    private String value;

    public SpinnerItem(String value, String representation) {
        this.value = value;
        this.representation = representation;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return representation;
    }

    public static SpinnerItem[] createSpinnerItemArray(IdEnum[] enumArray, Context context) {

        SpinnerItem[] itemArray = new SpinnerItem[enumArray.length];

        for (int i = 0; i < enumArray.length; i++) {
            itemArray[i] = new SpinnerItem(enumArray[i].toString(), context.getResources().getString((enumArray[i].getId())));
        }

        return itemArray;
    }
}
