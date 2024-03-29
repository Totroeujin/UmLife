package com.example.message;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.model.Chat;
import com.example.model.EventInfo;
import com.example.model.UserInfo;
import com.example.umlife.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private Button btn_send_msg;
    private EditText input_msg;

    private String user_name, room_name, user_profile_image;
    private DatabaseReference root;
    private String temp_key;

    private EventInfo eventInfo;
    private UserInfo userInfo;

    private TextView chatRoomName;
    FirebaseFirestore db;

    /* Important */
    // What to do now is just fetch data and upload data in chat object based to realtime db
    // upload chat with utc date format so it can be swapping if able
    RecyclerView RVChatList;
    LinearLayoutManager VerticalLayout;
    ChatAdapter chatAdapter;
    List<Chat> chatList = new ArrayList<Chat>();
    List<String> chatImageUrls = new ArrayList<>();
    RecyclerView.LayoutManager ChatRVLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_chat, container, false);
        userInfo = (UserInfo) getActivity().getIntent().getSerializableExtra("userInfo");

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        btn_send_msg = view.findViewById(R.id.BtnSendChat);
        input_msg = view.findViewById(R.id.TIChat);

        user_name = userInfo.getUsername();
        db = FirebaseFirestore.getInstance();
        db.collection("users").document(userInfo.getUuid())
            .get()
            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        // Document found in the Firestore database
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            // Convert the document to a User object
                            UserInfo userInfo = document.toObject(UserInfo.class);
                            // Access the additional fields in the 'user' document
                            if (userInfo.getProfileImage() != null)
                                user_profile_image = userInfo.getProfileImage();
                            else
                                user_profile_image = "https://firebasestorage.googleapis.com/v0/b/umlife-41693.appspot.com/o/profiles%2Fempty_photo.png?alt=media&token=4ac40c24-2087-4a32-b818-d334b6b59711";
                        }
                    }
                }
            });

        room_name = eventInfo.getEventName();

        RVChatList = view.findViewById(R.id.RVChatList);
        chatRoomName = view.findViewById(R.id.appBarChatRoomName);
        chatRoomName.setText("Room " + eventInfo.getEventName());

        root = FirebaseDatabase.getInstance("https://umlife-41693-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child(room_name);

        btn_send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (input_msg.getText().toString().trim().equals("")) return;

                Map<String, Object> map = new HashMap<String, Object>();
                temp_key = root.push().getKey();
                root.updateChildren(map);

                //Get current time the message being sent
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeZone(TimeZone.getTimeZone("Asia/Kuala_Lumpur"));
                Date date = calendar.getTime();
                DateFormat formatter = DateFormat.getTimeInstance(DateFormat.LONG, Locale.ROOT);
                String utcTime = formatter.format(date);

                //Upload information to RT db
                DatabaseReference message_root = root.child(temp_key);
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2.put("name", user_name);
                map2.put("msg", input_msg.getText().toString());
                map2.put("utc",utcTime);
                map2.put("userImage", user_profile_image);
                message_root.updateChildren(map2);

                //Clear the text box
                input_msg.setText("");
            }
        });

        ChatRVLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        RVChatList.setLayoutManager(ChatRVLayoutManager);
        chatAdapter = new ChatAdapter(getActivity(), chatList);
        RVChatList.setAdapter(chatAdapter);
        VerticalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        RVChatList.setLayoutManager(VerticalLayout);
        RVChatList.scrollToPosition(chatList.size() - 1);

        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                append_chat_conversation(snapshot);
                chatAdapter.notifyDataSetChanged();
                RVChatList.scrollToPosition(chatList.size() - 1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                append_chat_conversation(snapshot);
                chatAdapter.notifyDataSetChanged();
                RVChatList.scrollToPosition(chatList.size() - 1);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                append_chat_conversation(snapshot);
                chatAdapter.notifyDataSetChanged();
                RVChatList.scrollToPosition(chatList.size() - 1);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return view;
    }

    private String chat_msg, chat_user_name, chat_utc, chat_userImage;

    private void append_chat_conversation(DataSnapshot snapshot) {
        Iterator i = snapshot.getChildren().iterator();
        while (i.hasNext()) {
            chat_msg = (String) ((DataSnapshot)i.next()).getValue();
            chat_user_name = (String) ((DataSnapshot)i.next()).getValue();
            chat_userImage = (String) ((DataSnapshot)i.next()).getValue();
            chat_utc = (String) ((DataSnapshot)i.next()).getValue();

            //Append information into List before put into adapter
            chatList.add(new Chat(chat_user_name, chat_msg, chat_utc, chat_userImage));
        }
    }

    public void setEvent(EventInfo eventInfo){
        this.eventInfo = eventInfo;
    }
}