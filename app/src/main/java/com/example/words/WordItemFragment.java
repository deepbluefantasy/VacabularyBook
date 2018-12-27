package com.example.words;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.words.wordcontract.Words;

import java.util.ArrayList;
import java.util.Map;


public class WordItemFragment extends ListFragment {
    private static final String TAG = "myTag";
    public static String translateWord;
    private boolean currentIsLand;

    private OnFragmentInteractionListener mListener;

    // TODO: Rename and change types of parameters
    public static WordItemFragment newInstance() {
        WordItemFragment fragment = new WordItemFragment();
        Bundle args = new Bundle();
        ;
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public WordItemFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);


        ListView mListView = (ListView) view.findViewById(android.R.id.list);
        registerForContextMenu(mListView);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        Log.v(TAG, "WordItemFragment::onAttach");
        super.onAttach(context);
        mListener = (OnFragmentInteractionListener) getActivity();

    }

    public void refreshWordsList() {
        WordsDB wordsDB=WordsDB.getWordsDB();
        if (wordsDB != null) {
            ArrayList<Map<String, String>> items = wordsDB.getAllWords();

            SimpleAdapter adapter = new SimpleAdapter(getActivity(), items, com.example.words.R.layout.item,
                    new String[]{Words.Word._ID, Words.Word.COLUMN_NAME_WORD},
                    new int[]{com.example.words.R.id.textId, com.example.words.R.id.textViewWord});

            setListAdapter(adapter);
        }
    }

    public void refreshWordsList(String strWord) {
        WordsDB wordsDB=WordsDB.getWordsDB();
        if (wordsDB != null) {
            ArrayList<Map<String, String>> items = wordsDB.SearchUseSql(strWord);
            if(items.size()>0){

                SimpleAdapter adapter = new SimpleAdapter(getActivity(), items, com.example.words.R.layout.item,
                        new String[]{Words.Word._ID, Words.Word.COLUMN_NAME_WORD},
                        new int[]{com.example.words.R.id.textId, com.example.words.R.id.textViewWord});

                setListAdapter(adapter);
            }else{
                Toast.makeText(getActivity(),"Not found",Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        refreshWordsList();
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        TextView textId = null;
        TextView textWord = null;
        TextView textMeaning = null;
        TextView textSample = null;
        TextView textTranslateWord = null;
        AdapterView.AdapterContextMenuInfo info = null;
        View itemView = null;

        switch (item.getItemId()) {
            case R.id.action_delete:
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                itemView = info.targetView;
                textId = (TextView) itemView.findViewById(R.id.textId);
                if (textId != null) {
                    String strId = textId.getText().toString();
                    mListener.onDeleteDialog(strId);
                }
                break;
            case R.id.action_update:
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                itemView = info.targetView;
                textId = (TextView) itemView.findViewById(R.id.textId);

                if (textId != null) {
                    String strId = textId.getText().toString();

                    mListener.onUpdateDialog(strId);
                }
                break;
//            case R.id.action_translate:
//                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//                itemView = info.targetView;
//                textTranslateWord = (TextView) itemView.findViewById(com.example.mywordsapp.R.id.textViewWord);
//                translateWord=textTranslateWord.getText().toString();
//
//                break;
        }
        return true;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        Log.v(TAG, "WordItemFragment::onCreateContextMenu()");
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(com.example.words.R.menu.contextmenu_wordslistview, menu);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (null != mListener) {
            //通知Fragment所在的Activity，用户单击了列表的position项
            TextView textView = (TextView) v.findViewById(com.example.words.R.id.textId);
            if (textView != null) {
                //将单词ID传过去
                mListener.onWordItemClick(textView.getText().toString());
            }
        }
    }

    public interface OnFragmentInteractionListener {
        public void onWordItemClick(String id);

        public void onDeleteDialog(String strId);

        public void onUpdateDialog(String strId);
    }

}
