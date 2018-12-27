package com.example.words;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.words.wordcontract.Words;

public class WordDetailFragment extends Fragment {
    private static final String TAG="myTag";
    public static final String ARG_ID = "id";

    private String mID;
    private OnFragmentInteractionListener mListener;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param wordID Parameter 1.
     * @return A new instance of fragment WordDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WordDetailFragment newInstance(String wordID) {
        WordDetailFragment fragment = new WordDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ID, wordID);
        fragment.setArguments(args);
        return fragment;
    }

    public WordDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mID = getArguments().getString(ARG_ID);

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(com.example.words.R.layout.fragment_word_detail, container, false);
        Log.v(TAG,mID);

        WordsDB wordsDB=WordsDB.getWordsDB();

        if(wordsDB!=null && mID!=null){
            TextView textViewWord=(TextView)view.findViewById(com.example.words.R.id.word);
            TextView textViewWordMeaning=(TextView)view.findViewById(com.example.words.R.id.wordmeaning);
            TextView textViewWordSample=(TextView)view.findViewById(com.example.words.R.id.wordsample);

            Words.WordDescription item=wordsDB.getSingleWord(mID);
            if(item!=null){
                textViewWord.setText(item.word);
                textViewWordMeaning.setText(item.meaning);
                textViewWordSample.setText(item.sample);
            }
            else{
                textViewWord.setText("");
                textViewWordMeaning.setText("");
                textViewWordSample.setText("");
            }

        }
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onWordDetailClick(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (OnFragmentInteractionListener) getActivity();

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onWordDetailClick(Uri uri);

    }

}
