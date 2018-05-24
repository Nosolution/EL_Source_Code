package com.hlxx.climber.thirdpage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.hlxx.climber.R;
import com.hlxx.climber.dataTables.Comments;
import com.hlxx.climber.services.AzureServiceAdapter;
import com.hlxx.climber.services.CommentItemAdapter;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.OkHttpClientFactory;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.sync.MobileServiceSyncContext;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.ColumnDataType;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.MobileServiceLocalStoreException;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.SQLiteLocalStore;
import com.microsoft.windowsazure.mobileservices.table.sync.synchandler.SimpleSyncHandler;
import com.squareup.okhttp.OkHttpClient;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static com.hlxx.climber.services.AzureServiceAdapter.Initialize;
import static com.microsoft.windowsazure.mobileservices.table.query.QueryOperations.val;

public class StoneActivity extends Activity {

    private Button upload;
    private Button out;

    private EditText mText;

    MobileServiceClient mClient;

    MobileServiceTable<Comments> mCommentTable ;

    CommentItemAdapter mAdapter;

    Comments mComment = new Comments();

    AzureServiceAdapter mServiceAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 非模态化
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
        setContentView(R.layout.activity_stone);

        setFinishOnTouchOutside(true);//点击Dialog外退出
        stone_buttons();//按钮操作：upload,out
        try{
            Initialize(this);
            mServiceAdapter =AzureServiceAdapter.getInstance();
            mClient = mServiceAdapter.getClient();

            mClient.setAndroidHttpClientFactory(new OkHttpClientFactory() {
                @Override
                public OkHttpClient createOkHttpClient() {
                    OkHttpClient client = new OkHttpClient();
                    client.setReadTimeout(20, TimeUnit.SECONDS);
                    client.setWriteTimeout(20, TimeUnit.SECONDS);
                    return client;
                }
            });
            mCommentTable  = mClient.getTable(Comments.class);

            //Init local storage
            initLocalStore().get();

            mText = (EditText) findViewById(R.id.user_to_talk);

            mAdapter = new CommentItemAdapter(this,R.layout.row_list_comment);
            ListView listViewComment = (ListView) findViewById(R.id.listViewComment);
            listViewComment.setAdapter(mAdapter);

            // Load the items from the Mobile Service
            refreshItemsFromTable();

        }
        catch (Exception e){
            createAndShowDialog(e, "Error");
        }




    }


    public void checkItemInTable(Comments item) throws ExecutionException, InterruptedException {
        mCommentTable.update(item).get();
    }


    public void checkItem(final Comments item) {
        if (mClient == null) {
            return;
        }

        // Set the item as thumbed and update it in the table
        item.setThumb(true);

        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                try {

                    checkItemInTable(item);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (item.isThumb()) {
                                //mAdapter.remove(item);
                            }
                        }
                    });
                } catch (final Exception e) {
                    createAndShowDialogFromTask(e, "Error");
                }

                return null;
            }
        };

        runAsyncTask(task);

    }

    /**
     * Add an item to the Mobile Service Table
     *
     * @param item
     *            The item to Add
     */
    public Comments addItemInTable(Comments item) throws ExecutionException, InterruptedException {
        Comments entity = mCommentTable.insert(item).get();
        return entity;
    }

    /**
     * Add a new item
     *
     * @param view
     *            The view that originated the call
     */
    public void addItem(View view) {
        if (mClient == null) {
            return;
        }

        // Create a new item
        final Comments item = new Comments();
        String text = mText.getText().toString();
        if (text == null || text.isEmpty()||text.trim() == null||text.trim().isEmpty()){
            Toast.makeText(this, "Sorry, we can't send blank.", Toast.LENGTH_SHORT).show();
        }
        else {
            item.setText(text);
            item.setThumb(false);

            // Insert the new item
            AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        final Comments entity = addItemInTable(item);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!entity.isThumb()) {
                                    mAdapter.add(entity);
                                }
                            }
                        });
                    } catch (final Exception e) {
                        createAndShowDialogFromTask(e, "Error");
                    }
                    return null;
                }
            };

            runAsyncTask(task);

            mText.setText("");
        }
    }

    private void refreshItemsFromTable() {

        // Get the items that weren't marked as completed and add them in the
        // adapter

        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {

                try {
                    final List<Comments> results = refreshItemsFromMobileServiceTable();

                    //Offline Sync
                    //final List<ToDoItem> results = refreshItemsFromMobileServiceTableSyncTable();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.clear();

                            for (Comments item : results) {
                                mAdapter.add(item);
                            }
                        }
                    });
                } catch (final Exception e){
                    createAndShowDialogFromTask(e, "Error");
                }

                return null;
            }
        };

        runAsyncTask(task);
    }

    /**
     * Refresh the list with the items in the Mobile Service Table
     */

    private List<Comments> refreshItemsFromMobileServiceTable() throws ExecutionException, InterruptedException {
        return mCommentTable.where().execute().get();
    }

    //按钮
    protected void stone_buttons() {
        Button upload = (Button) findViewById(R.id.upload);
        Button out = (Button) findViewById(R.id.out);
        //upload.setOnClickListener(new View.OnClickListener() {

            //public void onClick(View view) {

             //   mComment.setText(mText.toString());
             //   Toast.makeText(StoneActivity.this, "信息已经发出去啦！", Toast.LENGTH_SHORT).show();
             //   finish();
            //}
        //});
        out.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                finish();
            }
        });
    }




    private void createAndShowDialogFromTask(final Exception exception, String title) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                createAndShowDialog(exception, "Error");
            }
        });
    }

    private void createAndShowDialog(Exception exception, String title) {
        Throwable ex = exception;
        if(exception.getCause() != null){
            ex = exception.getCause();
        }
        createAndShowDialog(ex.getMessage(), title);
    }

    /**
     * Creates a dialog and shows it
     *
     * @param message
     *            The dialog message
     * @param title
     *            The dialog title
     */
    private void createAndShowDialog(final String message, final String title) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);


        builder.setMessage(message);
        builder.setTitle(title);
        builder.create().show();
    }

    /**
     * Run an ASync task on the corresponding executor
     * @param task
     * @return
     */
    private AsyncTask<Void, Void, Void> runAsyncTask(AsyncTask<Void, Void, Void> task) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            return task.execute();
        }
    }

    /**
     * Initialize local storage
     * @return
     * @throws MobileServiceLocalStoreException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private AsyncTask<Void, Void, Void> initLocalStore() throws MobileServiceLocalStoreException, ExecutionException, InterruptedException {

        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {

                    MobileServiceSyncContext syncContext = mClient.getSyncContext();

                    if (syncContext.isInitialized())
                        return null;

                    SQLiteLocalStore localStore = new SQLiteLocalStore(mClient.getContext(), "OfflineStore", null, 1);

                    Map<String, ColumnDataType> tableDefinition = new HashMap<String, ColumnDataType>();
                    tableDefinition.put("id", ColumnDataType.String);
                    tableDefinition.put("text", ColumnDataType.String);
                    tableDefinition.put("isThumb", ColumnDataType.Boolean);

                    localStore.defineTable("Comments", tableDefinition);

                    SimpleSyncHandler handler = new SimpleSyncHandler();

                    syncContext.initialize(localStore, handler).get();

                } catch (final Exception e) {
                    createAndShowDialogFromTask(e, "ErrorInit");
                }

                return null;
            }
        };

        return runAsyncTask(task);
    }
}