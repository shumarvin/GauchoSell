package cs48.g05.bbc2016.gauchosell.feeds;

import java.util.ArrayList;

import cs48.g05.bbc2016.gauchosell.item.Item;

/**
 * Created by icema_000 on 4/29/2016.
 */
public abstract class Feed {
    private ArrayList<Item> items;

    public ArrayList<Item> retrieveFeed(){
        return items;
    }
    public abstract boolean callQuery();
}