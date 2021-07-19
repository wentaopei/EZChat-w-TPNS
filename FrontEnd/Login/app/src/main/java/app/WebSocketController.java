package app;

//import android.util.Log;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import com.example.ezchat.ui.Activity.GroupChatActivity;
//import com.example.ezchat.ui.login.LoginActivity;
//
//import org.java_websocket.client.WebSocketClient;
//import org.java_websocket.drafts.Draft;
//import org.java_websocket.drafts.Draft_6455;
//import org.java_websocket.handshake.ServerHandshake;
//
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.security.acl.Group;
//
public class WebSocketController
{
////    private static String ss = "";
////    private static TextView History;
//    public static void WebSocketConnect(String uri, WebSocketClient cc, EditText gr)
//    {
////        String w = "ws://coms-309-ss-3.misc.iastate.edu:8080/websocket/" + gr.getText().toString() + "-" + String.valueOf(LoginActivity.user_login.Id);
//        Draft[] drafts = {new Draft_6455()};
//        try {
//            Log.d("Socket:", "Trying socket");
////            final String ss = s;
////            final TextView tx = history;
//            cc = new WebSocketClient(new URI(uri), (Draft) drafts[0])
//            {
//                @Override
//                public void onMessage(String message)
//                {
//                    Log.d("", "run() returned: " + message);
////                    ss = History.getText().toString();
//                    GroupChatActivity.s = GroupChatActivity.history.getText().toString();
//                    GroupChatActivity.history.setText(GroupChatActivity.s + "\n" + message);
//                    int scroll_amount = GroupChatActivity.history.getLayout().getLineBottom(GroupChatActivity.history.getLineCount() - 1) - GroupChatActivity.history.getHeight();
//                            if (scroll_amount > 0)
//                                GroupChatActivity.history.scrollTo(0, scroll_amount);
//                            else
//                                GroupChatActivity.history.scrollTo(0, 0);
//                }
//
//                @Override
//                public void onOpen(ServerHandshake handshake) {
//                    Log.d("OPEN", "run() returned: " + "is connecting");
//                }
//
//
//                @Override
//                public void onClose(int code, String reason, boolean remote) {
//                    Log.d("CLOSE", "onClose() returned: " + reason);
//
//                }
//
//                @Override
//                public void onError(Exception e) {
//                    Log.d("Exception:", e.toString());
//                }
//            };
//        } catch (URISyntaxException e) {
//            Log.d("Exception:", e.getMessage().toString());
//            e.printStackTrace();
//        }
//    }
//
//    public static void WebSocketSend(WebSocketClient cc, EditText et)
//    {
//        try
//        {
//            cc.send(et.getText().toString());
//        }
//        catch (Exception e)
//        {
//            Log.d("ExceptionSendMessage:", e.getMessage().toString());
//        }
//        et.setText("");
//    }
//
}
