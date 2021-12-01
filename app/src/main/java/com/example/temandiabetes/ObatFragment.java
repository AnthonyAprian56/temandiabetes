package com.example.temandiabetes;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ObatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ObatFragment extends Fragment implements View.OnClickListener {

    private CardView obat1, obat2;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ObatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ObatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ObatFragment newInstance() {
        ObatFragment fragment = new ObatFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_obat, container, false);
        CardView obat1 = view.findViewById(R.id.card_obat1);
        CardView obat2 = view.findViewById(R.id.card_obat2);

        obat1.setOnClickListener(this);
        obat2.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()) {
            case R.id.card_obat1 : i = new Intent(getActivity(), Obat1Activity.class); startActivity(i);break;
            case R.id.card_obat2 : i = new Intent(getActivity(), Obat2Activity.class); startActivity(i);break;
            default:break;
        }

    }
}