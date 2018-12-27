package com.example.words;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;

import com.example.words.wordcontract.Words;

public class MainActivity extends AppCompatActivity implements WordItemFragment.OnFragmentInteractionListener, WordDetailFragment.OnFragmentInteractionListener {
    private static final String TAG = "myTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(com.example.words.R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(com.example.words.R.id.toolbar);

        //
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(com.example.words.R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsertDialog();
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WordsDB wordsDB=WordsDB.getWordsDB();
        if (wordsDB != null)
            wordsDB.close();

    }

 @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.example.words.R.menu.menu_main, menu);
        return true;
    }

    //menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case com.example.words.R.id.action_search:
                SearchDialog();
                return true;
            case com.example.words.R.id.action_insert:
                InsertDialog();
                return true;
            case R.id.action_translate1:
                Intent intent = new Intent(MainActivity.this,TranslateActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_news:
                Intent intentNews = new Intent(MainActivity.this,EnglishWeb.class);
                startActivity(intentNews);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void RefreshWordItemFragment() {
        WordItemFragment wordItemFragment = (WordItemFragment) getFragmentManager().findFragmentById(com.example.words.R.id.wordslist);
        wordItemFragment.refreshWordsList();
    }

    private void RefreshWordItemFragment(String strWord) {
        WordItemFragment wordItemFragment = (WordItemFragment) getFragmentManager().findFragmentById(com.example.words.R.id.wordslist);
        wordItemFragment.refreshWordsList(strWord);
    }

    private void InsertDialog() {
        final TableLayout tableLayout = (TableLayout) getLayoutInflater().inflate(com.example.words.R.layout.insert,       null);
        new AlertDialog.Builder(this)
                .setTitle("新增单词")
                .setView(tableLayout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //new word info
                        String strWord = ((EditText) tableLayout.findViewById(com.example.words.R.id.txtWord)).getText().toString();
                        String strMeaning = ((EditText) tableLayout.findViewById(com.example.words.R.id.txtMeaning)).getText().toString();
                        String strSample = ((EditText) tableLayout.findViewById(com.example.words.R.id.txtSample)).getText().toString();
                        WordsDB wordsDB=WordsDB.getWordsDB();

                        wordsDB.Insert(strWord, strMeaning, strSample);

                        RefreshWordItemFragment();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create()
                .show();
    }


    private void DeleteDialog(final String strId) {
        new AlertDialog.Builder(this).setTitle("删除单词").setMessage("是否真的删除单词?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                WordsDB wordsDB=WordsDB.getWordsDB();
                wordsDB.DeleteUseSql(strId);


                RefreshWordItemFragment();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).create().show();
    }

    private void UpdateDialog(final String strId, final String strWord, final String strMeaning, final String strSample) {
        final TableLayout tableLayout = (TableLayout) getLayoutInflater().inflate(com.example.words.R.layout.insert, null);
        ((EditText) tableLayout.findViewById(com.example.words.R.id.txtWord)).setText(strWord);
        ((EditText) tableLayout.findViewById(com.example.words.R.id.txtMeaning)).setText(strMeaning);
        ((EditText) tableLayout.findViewById(com.example.words.R.id.txtSample)).setText(strSample);
        new AlertDialog.Builder(this)
                .setTitle("修改单词")
                .setView(tableLayout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String strNewWord = ((EditText) tableLayout.findViewById(com.example.words.R.id.txtWord)).getText().toString();
                        String strNewMeaning = ((EditText) tableLayout.findViewById(com.example.words.R.id.txtMeaning)).getText().toString();
                        String strNewSample = ((EditText) tableLayout.findViewById(com.example.words.R.id.txtSample)).getText().toString();

                        WordsDB wordsDB=WordsDB.getWordsDB();
                        wordsDB.UpdateUseSql(strId, strWord, strNewMeaning, strNewSample);

                        RefreshWordItemFragment();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create()
                .show();

    }

    private void SearchDialog() {
        final TableLayout tableLayout = (TableLayout) getLayoutInflater().inflate(com.example.words.R.layout.searchterm, null);
        new AlertDialog.Builder(this)
                .setTitle("查找单词")
                .setView(tableLayout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String txtSearchWord = ((EditText) tableLayout.findViewById(com.example.words.R.id.txtSearchWord)).getText().toString();
                        RefreshWordItemFragment(txtSearchWord);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create()
                .show();

    }

    @Override
    public void onWordDetailClick(Uri uri) {

    }

    @Override
    public void onWordItemClick(String id) {

        if(isLand()) {
            ChangeWordDetailFragment(id);
        }else{
            Intent intent = new Intent(MainActivity.this,WordDetailActivity.class);
            intent.putExtra(WordDetailFragment.ARG_ID, id);
            startActivity(intent);
        }

    }


    private boolean isLand(){

        if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE)
            return true;
        return false;
    }

    private void ChangeWordDetailFragment(String id){
        Bundle arguments = new Bundle();
        arguments.putString(WordDetailFragment.ARG_ID, id);
        Log.v(TAG, id);

        WordDetailFragment fragment = new WordDetailFragment();
        fragment.setArguments(arguments);
        getFragmentManager().beginTransaction().replace(com.example.words.R.id.worddetail, fragment).commit();
    }

    @Override
    public void onDeleteDialog(String strId) {
        DeleteDialog(strId);
    }

    @Override
    public void onUpdateDialog(String strId) {
        WordsDB wordsDB=WordsDB.getWordsDB();
        if (wordsDB != null && strId != null) {

            Words.WordDescription item = wordsDB.getSingleWord(strId);
            if (item != null) {
                UpdateDialog(strId, item.word, item.meaning, item.sample);
            }

        }

    }
}
