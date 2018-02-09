package com.iter.ivory;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;

public class VaccineFragment extends Fragment {
    private static final String ARG_VIEW_PERSONAL = "arg_view_personal";
    private Boolean mViewPersonal = true;
    private User user = new User("", new ArrayList<Vaccines>());
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView recyclerView;
    private OnListFragmentInteractionListener mListener;

    public VaccineFragment() {
    }

    // create a new VaccineFragment and send if view is personal or suggested
    public static VaccineFragment newInstance(boolean personal) {
        VaccineFragment fragment = new VaccineFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_VIEW_PERSONAL, personal);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mViewPersonal = getArguments().getBoolean(ARG_VIEW_PERSONAL);
        }
        FirebaseAuth authuser = FirebaseAuth.getInstance();
        setUser(authuser);
    }

    public void setUser(final FirebaseAuth authuser){
        DocumentReference userinfo;
        userinfo = db.collection("users").document(authuser.getUid());
        userinfo.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                try{
                    user = documentSnapshot.toObject(User.class);
                    if (recyclerView!=null) {
                        recyclerView.setAdapter(new MyVaccineRecyclerViewAdapter(user.getVaccinations(), mListener));
                    }
                }catch (IllegalStateException e){
                    db.collection("users").document(authuser.getUid()).set(new User(authuser.getCurrentUser().getDisplayName(), new ArrayList<Vaccines>()));
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vaccine_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
            recyclerView.setAdapter(new MyVaccineRecyclerViewAdapter(user.getVaccinations() ,mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(String genName, String vaccine, Date date, Date remDate, Integer position);
    }
}
