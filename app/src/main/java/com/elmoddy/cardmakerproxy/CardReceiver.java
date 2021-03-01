package com.elmoddy.cardmakerproxy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


public class CardReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            AnkiDroidHelper mAnkiDroid = new AnkiDroidHelper(context);


            String deckName = extras.getString("deck");
            String front = extras.getString("front");
            String back = extras.getString("back");

            Long deckId = mAnkiDroid.findDeckIdByName(deckName);
            if (deckId == null) {
                deckId = mAnkiDroid.getApi().addNewDeck(deckName);
                mAnkiDroid.storeDeckReference(deckName, deckId);
            }

            Long modelId = mAnkiDroid.findModelIdByName("Standard", 2);
            if (modelId == null) {
                mAnkiDroid.getApi().addNewBasicModel("Basic");
                mAnkiDroid.storeModelReference("Basic", modelId);
            }

            mAnkiDroid.getApi().addNote(modelId, deckId, new String[] {front, back}, null);

            Log.v("BHO", "model: " + modelId);
            Log.v("BHO", "deck: " + deckName);
            Log.v("BHO", "front: " + front);
            Log.v("BHO", "back: " + back);
        }
    }
}