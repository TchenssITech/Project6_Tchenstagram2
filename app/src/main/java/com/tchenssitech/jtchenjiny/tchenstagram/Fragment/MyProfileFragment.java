package com.tchenssitech.jtchenjiny.tchenstagram.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.tchenssitech.jtchenjiny.tchenstagram.R;
import com.tchenssitech.jtchenjiny.tchenstagram.LoginActivity;
import com.tchenssitech.jtchenjiny.tchenstagram.Models.Post;
import com.tchenssitech.jtchenjiny.tchenstagram.PostAdapter;
import com.tchenssitech.jtchenjiny.tchenstagram.User;

import java.util.ArrayList;
import java.util.List;

public class MyProfileFragment extends Fragment {

    public static final String TAG ="AccountFragment";

    private User user;
    private ImageView ivProfileImage;
    private TextView tvScreenName;
    private TextView tvBio;
    private Button btnLogout;
    private RecyclerView rvFeed;
    protected PostAdapter adapter;
    protected List<Post> postList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyProfileFragment newInstance(String param1, String param2) {
        MyProfileFragment fragment = new MyProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

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
        return inflater.inflate(R.layout.fargment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivProfileImage = view.findViewById(R.id.ivProfile);
        tvScreenName = view.findViewById(R.id.tvScreenName);
       // tvBio = view.findViewById(R.id.tvBio);
        btnLogout = view.findViewById(R.id.btnLogout);
        rvFeed = view.findViewById(R.id.rvFeed);


        tvScreenName.setText(ParseUser.getCurrentUser().getUsername());

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                ParseUser currentUser = ParseUser.getCurrentUser();

                goLoginActivity();
            }
        });

        // Create data source
        postList = new ArrayList<>();

        // Create Adapter
        adapter = new PostAdapter(getContext(), postList);

        // Set the Adapter
        rvFeed.setAdapter(adapter);

        // Set Layout Manager
        rvFeed.setLayoutManager(new LinearLayoutManager(getContext()));

        queryPosts();


    }

    private void goLoginActivity() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
    }

    protected void queryPosts() {
        // Specify which class to query
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        query.setLimit(20);
        query.orderByDescending(Post.KEY_CREATED_KEY);

        // Specify the object id
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e !=null){
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                } for (Post post : posts) {
                    Log.i(TAG, "Post: "+ post.getDescription() + ", username: " + post.getUser().getUsername());
                }
                postList.addAll(posts);
                adapter.notifyDataSetChanged();

            }
        });
    }
}
