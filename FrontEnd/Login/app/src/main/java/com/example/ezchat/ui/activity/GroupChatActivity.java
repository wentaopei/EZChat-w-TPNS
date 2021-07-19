package com.example.ezchat.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ezchat.R;
import com.example.ezchat.ui.login.LoginActivity;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * This is the class for group chat.
 * @author Wentao Pei
 *
 */

public class GroupChatActivity extends AppCompatActivity {

    private WebSocketClient cc;
    private WebSocketClient test;
    Button b, c;
    EditText et, gr;
    String s = "";
    TextView history;
    boolean test_open = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);
        b = (Button)findViewById(R.id.button_sendTextGroup);
        c = (Button)findViewById(R.id.buttonGo);
        et = (EditText)findViewById(R.id.editText_TextGroup);
        gr = (EditText)findViewById(R.id.editText_ToGroup);
        history = (TextView)findViewById(R.id.textView11);
        history.setMovementMethod(ScrollingMovementMethod.getInstance());
        history.setText("Start chat.");
        history.setFocusable(true);
        c.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
//                WebSocketController.WebSocketConnect(w, cc,gr);
//                history.setText("");
//                cc.connect();
                String w = "ws://coms-309-ss-3.misc.iastate.edu:8080/websocket/" + gr.getText().toString() + "-" + String.valueOf(LoginActivity.user_login.Id);
                Draft[] drafts = {new Draft_6455()};
                try {
                    Log.d("Socket:", "Trying socket");
                    cc = new WebSocketClient(new URI(w),(Draft) drafts[0])
                    {
                        @Override
                        public void onMessage(String message) {
                            Log.d("", "run() returned: " + message);
                            s = history.getText().toString();
                            history.setText(s + "\n" + message);
                            int scroll_amount = history.getLayout().getLineBottom(history.getLineCount() - 1) - history.getHeight();
                            if (scroll_amount > 0)
                                history.scrollTo(0, scroll_amount);
                            else
                                history.scrollTo(0, 0);

                        }

                        @Override
                        public void onOpen(ServerHandshake handshake) {
                            Log.d("OPEN", "run() returned: " + "is connecting");
                        }


                        @Override
                        public void onClose(int code, String reason, boolean remote) {
                            Log.d("CLOSE", "onClose() returned: " + reason);

                        }

                        @Override
                        public void onError(Exception e)
                        {
                            Log.d("Exception:", e.toString());
                        }
                    };
                }
                catch (URISyntaxException e) {
                    Log.d("Exception:", e.getMessage().toString());
                    e.printStackTrace();
                }

                history.setText("");
                cc.connect();


            }
        });


        b.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//                WebSocketController.WebSocketSend(cc,et);
                try
                {
                    cc.send(et.getText().toString());
                }
                catch (Exception e)
                {
                    Log.d("ExceptionSendMessage:", e.getMessage().toString());
                }
                et.setText("");
            }

        });
    }

    public boolean test()
    {
        Draft[] drafts = {new Draft_6455()};
        String w = "ws://coms-309-ss-3.misc.iastate.edu:8080/websocket/2-4";
        try {
            test = new WebSocketClient(new URI(w), (Draft) drafts[0]) {
                @Override
                public void onMessage(String message) {
                    test_open = true;
                }

                @Override
                public void onOpen(ServerHandshake handshake) {
                    test_open = true;
                }


                @Override
                public void onClose(int code, String reason, boolean remote) {

                }

                @Override
                public void onError(Exception e) {
                }
            };
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        test.connect();
        return test_open;
    }
}
