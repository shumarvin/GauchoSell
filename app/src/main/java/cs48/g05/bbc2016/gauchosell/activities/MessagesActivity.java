package cs48.g05.bbc2016.gauchosell.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;

import cs48.g05.bbc2016.gauchosell.R;
import cs48.g05.bbc2016.gauchosell.item.Message;
import cs48.g05.bbc2016.gauchosell.util.Constants;
import cs48.g05.bbc2016.gauchosell.util.GauchoSell;

public class MessagesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        ListView list = (ListView) findViewById(R.id.my_messages_listview);

        //FirebaseListAdapter used to create a feed
        String email = GauchoSell.user.getAccount().getEmail().replace(".", ",");
        Firebase firebaseRef = new Firebase(Constants.FIREBASE_URL + "/users/" + email + "/messages");
        Query queryRef = firebaseRef.orderByChild("priority");
        FirebaseListAdapter<Message> adapter = new FirebaseListAdapter<Message>(this, Message.class, R.layout.message_layout, queryRef) {
            @Override
            protected void populateView(View v, Message message, int i) {
                TextView messageItem = (TextView) v.findViewById(R.id.messages_itemName);
                messageItem.setText(message.getItemName());

                String price = "" + message.getPrice();
                TextView messagePrice = (TextView) v.findViewById(R.id.messages_amount);
                messagePrice.setText(price);

                TextView messageUsername = (TextView) v.findViewById(R.id.messages_username);
                messageUsername.setText(message.getUsername());

                TextView messageFullMessage = (TextView) v.findViewById(R.id.message_fullmessage);
                messageFullMessage.setText(message.getMessageDetails());

                final Message message1=message;
                ImageButton deleteMessage = (ImageButton) v.findViewById(R.id.deleteMessage);
                deleteMessage.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        GauchoSell.user.deleteMessage(message1);
                    }
                });
            }
        };
        list.setAdapter(adapter);
    }
}
