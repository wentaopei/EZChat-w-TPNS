package com.example.ezchat.ui.me;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import android.widget.Button;
import android.content.Intent;

import com.example.ezchat.R;
import com.example.ezchat.ui.login.LoginActivity;

/**
 * The page to show the use about the account information such as ID, username, email, gender and more. Also user can logout in this page.
 * @author Wentao Pei
 */

public class MeFragment extends Fragment {

    private MeViewModel mViewModel;

    public static MeFragment newInstance() {
        return new MeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        mViewModel =
                ViewModelProviders.of(this).get(MeViewModel.class);

        final View root = inflater.inflate(R.layout.fragment_me, container, false);
        //final TextView textView = root.findViewById(R.id.text_me);
        mViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });

        TextView userName = (TextView) root.findViewById(R.id.userName_show);
        TextView id = (TextView) root.findViewById(R.id.iD_show);
        TextView email = (TextView)root.findViewById(R.id.email_show);
        TextView gender = (TextView) root.findViewById(R.id.gender_show);

        userName.setText("Name: " + LoginActivity.user_login.getUsername());
        String id_str = String.valueOf(LoginActivity.user_login.getUerID());
        id.setText("ID: " + id_str);
        email.setText(LoginActivity.user_login.getEmail());
        gender.setText(LoginActivity.user_login.gender);

        Button lg = (Button) root.findViewById(R.id.buttonLogout);
        lg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(root.getContext(), LoginActivity.class);
                startActivity(i);
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MeViewModel.class);

    }

}
