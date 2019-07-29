package edu.kit.mensameet.client.viewmodel;

/**
 * Handler parent class for a MensaMeetList
 */
public class MensaMeetListHandler {
    /**
     * Selects a list item.
     *
     * @param itemId
     * @param selected
     */
    public void select(int itemId, boolean selected) { }

    /**
     * Returns whether an item is selected.
     *
     * @param itemId
     * @return
     */
    public boolean isSelected(int itemId) {
        return true;
    }
}
