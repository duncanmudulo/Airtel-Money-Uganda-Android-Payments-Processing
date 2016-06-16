package com.electron.apps;

import android.app.Activity;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Date;

public class StoreActivity extends AppCompatActivity {

    public WebView store;
    Toolbar toolbar;
    ActionBar ab;
    ProgressBar loading;
    Button reload;
    TextView info;
    String query, q;
    static String application_id;
    static WebView sstore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clearCache(this, 1);

        setContentView(R.layout.activity_store);



        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        ab.setTitle("256 play");

        ab.setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

        BroadcastReceiver onComplete = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(getApplicationContext(), "Download complete", //To notify the Client that the file is being downloaded
                        Toast.LENGTH_LONG).show();
            }
        };



        registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));


        handleIntent(getIntent());




        store = (WebView)findViewById(R.id.store);
        sstore = store;

        store.getSettings().setJavaScriptEnabled(true);


        store.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (!TextUtils.isEmpty(title) && !TextUtils.equals(title, "Web page not available") && !TextUtils.equals(title, "500 Internal Server Error") && !TextUtils.equals(title, "Webpage not available")) {


                    getSupportActionBar().setTitle(title);

                } else {
                    getSupportActionBar().setTitle("256 Play");
                }


            }

        });

        store.addJavascriptInterface(new WebViewJavaScriptInterface(this), "app");

        WebViewClientImpl client = new WebViewClientImpl();

        store.setWebViewClient(client);

        store.setDownloadListener(new storeDownloader());

        setUpWebViewforPopular();

    }

    //Incase we are browsing popular

    public void setUpWebViewforPopular() {
        store = (WebView) findViewById(R.id.store);

        //  unregisterForContextMenu(browser);

        enableDebug();

        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Activity.CONNECTIVITY_SERVICE);

        if (cm != null && cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected()) {
            store.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        } else {
            store.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        }

        WebSettings settings = store.getSettings();
        settings.setJavaScriptEnabled(true);
        //  settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //  reload.setVisibility(View.INVISIBLE);

        store.loadUrl("http://192.168.206.1/256Play/index.php");
        store.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (!TextUtils.isEmpty(title) && !TextUtils.equals(title, "Web page not available") && !TextUtils.equals(title, "500 Internal Server Error") && !TextUtils.equals(title, "Webpage not available")) {


                    getSupportActionBar().setTitle(title);

                } else {
                    getSupportActionBar().setTitle("256 Play");
                }


            }

        });

        WebViewClientImpl webViewClient = new WebViewClientImpl();
        store.setWebViewClient(webViewClient);

        store.setDownloadListener(new storeDownloader());

    }
    //End

    //Incase we are browsing Social Apps

    public void setUpWebViewforSocial() {
        store = (WebView) findViewById(R.id.store);

        //  unregisterForContextMenu(browser);

        enableDebug();

        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Activity.CONNECTIVITY_SERVICE);

        if (cm != null && cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected()) {
            store.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        } else {
            store.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        }

        WebSettings settings = store.getSettings();
        settings.setJavaScriptEnabled(true);
        //  settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //  reload.setVisibility(View.INVISIBLE);

        store.loadUrl("http://192.168.206.1/256Play/social.php");
        store.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (!TextUtils.isEmpty(title) && !TextUtils.equals(title, "Web page not available") && !TextUtils.equals(title, "500 Internal Server Error") && !TextUtils.equals(title, "Webpage not available")) {


                    getSupportActionBar().setTitle(title);

                } else {
                    getSupportActionBar().setTitle("256 Play");
                }


            }

        });

        WebViewClientImpl webViewClient = new WebViewClientImpl();
        store.setWebViewClient(webViewClient);

        storeDownloader downloader = new storeDownloader();

        store.setDownloadListener(downloader);




    }




    //End


    //Incase we are browsing games / entertainment

    public void setUpWebViewforGames() {
        store = (WebView) findViewById(R.id.store);

        enableDebug();

        //  unregisterForContextMenu(browser);

        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Activity.CONNECTIVITY_SERVICE);

        if (cm != null && cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected()) {
            store.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        } else {
            store.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        }

        WebSettings settings = store.getSettings();
        settings.setJavaScriptEnabled(true);
        //  settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //  reload.setVisibility(View.INVISIBLE);

        store.loadUrl("http://192.168.206.1/256Play/games.php");
        store.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (!TextUtils.isEmpty(title) && !TextUtils.equals(title, "Web page not available") && !TextUtils.equals(title, "500 Internal Server Error") && !TextUtils.equals(title, "Webpage not available")) {


                    getSupportActionBar().setTitle(title);

                } else {
                    getSupportActionBar().setTitle("256 Play");
                }


            }

        });

        WebViewClientImpl webViewClient = new WebViewClientImpl();
        store.setWebViewClient(webViewClient);

        storeDownloader downloader = new storeDownloader();

        store.setDownloadListener(downloader);




    }

    public void enableDebug(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            store.setWebContentsDebuggingEnabled(true);
        }

    }




    //End


    //Incase we are browsing popular

    public void setUpWebViewforBusiness() {
        store = (WebView) findViewById(R.id.store);

        enableDebug();
        //  unregisterForContextMenu(browser);

        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Activity.CONNECTIVITY_SERVICE);

        if (cm != null && cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected()) {
            store.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        } else {
            store.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        }

        WebSettings settings = store.getSettings();
        settings.setJavaScriptEnabled(true);
        //  settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //  reload.setVisibility(View.INVISIBLE);

        store.loadUrl("http://192.168.206.1/256Play/business.php");
        store.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (!TextUtils.isEmpty(title) && !TextUtils.equals(title, "Web page not available") && !TextUtils.equals(title, "500 Internal Server Error") && !TextUtils.equals(title, "Webpage not available")) {


                    getSupportActionBar().setTitle(title);

                } else {
                    getSupportActionBar().setTitle("256 Play");
                }


            }

        });

        WebViewClientImpl webViewClient = new WebViewClientImpl();
        store.setWebViewClient(webViewClient);

        storeDownloader downloader = new storeDownloader();

        store.setDownloadListener(downloader);




    }




    //End


    //Incase we are browsing utilities

    public void setUpWebViewforUtilities() {
        store = (WebView) findViewById(R.id.store);

        //  unregisterForContextMenu(browser);
        enableDebug();

        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Activity.CONNECTIVITY_SERVICE);

        if (cm != null && cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected()) {
            store.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        } else {
            store.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        }

        WebSettings settings = store.getSettings();
        settings.setJavaScriptEnabled(true);
        //  settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //  reload.setVisibility(View.INVISIBLE);

        store.loadUrl("http://192.168.206.1/256Play/utilities.php");
        store.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (!TextUtils.isEmpty(title) && !TextUtils.equals(title, "Web page not available") && !TextUtils.equals(title, "500 Internal Server Error") && !TextUtils.equals(title, "Webpage not available")) {


                    getSupportActionBar().setTitle(title);

                } else {
                    getSupportActionBar().setTitle("256 Play");
                }


            }

        });

        WebViewClientImpl webViewClient = new WebViewClientImpl();
        store.setWebViewClient(webViewClient);

        storeDownloader downloader = new storeDownloader();

        store.setDownloadListener(downloader);




    }




    //End

    //In Case we are searching for an app

    public void setUpWebViewforSearch() {
        store = (WebView) findViewById(R.id.store);

        //  unregisterForContextMenu(browser);
        enableDebug();

        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Activity.CONNECTIVITY_SERVICE);

        if (cm != null && cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected()) {
            store.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        } else {
            store.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        }

        WebSettings settings = store.getSettings();
        settings.setJavaScriptEnabled(true);
        //  settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //  reload.setVisibility(View.INVISIBLE);

        store.loadUrl("http://192.168.206.1/256Play/search.php?q="+q);
                store.setWebChromeClient(new WebChromeClient() {
                    @Override
                    public void onReceivedTitle(WebView view, String title) {
                        super.onReceivedTitle(view, title);
                        if (!TextUtils.isEmpty(title) && !TextUtils.equals(title, "Web page not available") && !TextUtils.equals(title, "500 Internal Server Error") && !TextUtils.equals(title, "Webpage not available")) {


                            getSupportActionBar().setTitle(title);

                        } else {
                            getSupportActionBar().setTitle("256 Play");
                        }


                    }

                });

        WebViewClientImpl webViewClient = new WebViewClientImpl();
        store.setWebViewClient(webViewClient);

        storeDownloader downloader = new storeDownloader();

        store.setDownloadListener(downloader);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_store, menu);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Associate searchable configuration with the SearchView
            SearchManager searchManager =
                    (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            final SearchView searchView =
                    (SearchView) menu.findItem(R.id.action_search).getActionView();
            searchView.setSearchableInfo(
                    searchManager.getSearchableInfo(getComponentName()));




            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {

                    q = query;

                    setUpWebViewforSearch();


                    searchView.setQuery("", false);
                    searchView.setIconified(true);

                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });

        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.menu_exit) {

            finish();
            return true;
        }

        else if(id == R.id.menu_about)
        {
            Intent about = new Intent(StoreActivity.this, About.class);
            startActivity(about);

            return true;
        }

        else if(id == R.id.menu_popular){

            setUpWebViewforPopular();
            return true;
        }

        else if(id == R.id.menu_games){

            setUpWebViewforGames();
            return true;
        }

        else if(id == R.id.menu_business){

            setUpWebViewforBusiness();
            return true;
        }

        else if(id == R.id.menu_utilities){

            setUpWebViewforUtilities();
            return true;
        }

        else if(id == R.id.menu_social){

            setUpWebViewforSocial();
            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    public class WebViewJavaScriptInterface{

        private Context context;

        /*
         * Need a reference to the context in order to sent a post message
         */
        public WebViewJavaScriptInterface(Context context){
            this.context = context;
        }

        /*
         * This method can be called from Android. @JavascriptInterface
         * required after SDK version 17.
         */
        @JavascriptInterface
        public void setapplicationId(String application_id_js){
            Toast.makeText(context, application_id_js,Toast.LENGTH_LONG).show();
            application_id = application_id_js;

        }

        @JavascriptInterface
        public void copyAlert(){
            ClipboardManager clipboard = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("number","0758455142");
            clipboard.setPrimaryClip(clip);

            Toast.makeText(context, "Copied", Toast.LENGTH_LONG).show();
        }

        @JavascriptInterface
        public void wrongTransactionId(){

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Server Response");
            builder.setMessage(R.string.wrong_trans_id);
            builder.setPositiveButton("OK", null);

            builder.show();

        }

        @JavascriptInterface
        public void usedTransactionID(){

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Server Response");
            builder.setMessage(R.string.used_trans_id);
            builder.setPositiveButton("OK", null);

            builder.show();

        }



        @JavascriptInterface
        public void underPayment(){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Server Response");
            builder.setMessage(R.string.under_payment);
            builder.setPositiveButton("OK", null);

            builder.show();
        }

        @JavascriptInterface
        public void overPayment(){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Server Response");
            builder.setMessage(R.string.over_payment);
            builder.setPositiveButton("OK", null);

            builder.show();
        }

        @JavascriptInterface
        public void okPayment(){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Server Response");
            builder.setMessage(R.string.success);
            builder.setPositiveButton("OK", null);

            builder.show();
        }

        @JavascriptInterface
        public void wrongNumber(){

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Server Response");
            builder.setMessage(R.string.wrong_number);
            builder.setPositiveButton("OK", null);
            builder.show();

        }

        @JavascriptInterface
        public void noSearchResults(){
            Toast.makeText(context, "No Results", Toast.LENGTH_LONG).show();
        }



        @JavascriptInterface
        
        public void pay(String application_id_js) {

            application_id = application_id_js;

            Intent callIntent = new Intent(Intent.ACTION_CALL, makeUssdCall("*185#"));

            startActivity(callIntent);

        }

        private Uri makeUssdCall(String s) {

            String uriString = "";

            if(!s.startsWith("tel:"))
                uriString += "tel:";

            for(char c : s.toCharArray()) {

                if(c == '#')
                    uriString += Uri.encode("#");
                else
                    uriString += c;
            }

            return Uri.parse(uriString);
        }
    }

    public  static void confirmId(){

        sstore.loadUrl("http://192.168.206.1/256Play/confirmid.php?app_id="+application_id);

    }

    public class WebViewClientImpl extends WebViewClient {


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains("subscription")) {
              //  setUpWebViewforPopular();
            }

            else {
                view.loadUrl(url);
            }

            return true;
        }



        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            loading = (ProgressBar) findViewById(R.id.progressBar);
            loading.setVisibility(View.VISIBLE);
            // WebViewActivity.this.progress.setProgress(0);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            loading.setVisibility(View.GONE);
            // v.this.progress.setProgress(100);
            super.onPageFinished(view, url);
        }

        /**
         * @Override public void onBackPressed(){
         * <p/>
         * finish();
         * }
         **/


        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            reload = (Button) findViewById(R.id.reload);
            info = (TextView) findViewById(R.id.info);
            info.setVisibility(View.VISIBLE);
            reload.setVisibility(View.VISIBLE);
            store.loadUrl("about:blank");
            ab.setTitle("256 Play");





            unregisterForContextMenu(reload);
            //
            // onKeyDown();

            reload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ab.setTitle("256 Play");
                    store.clearHistory();
                    ab.setTitle("Reloading...");

                   setUpWebViewforPopular();
                    // onCreate(Bundle, saved);
                    info.setVisibility(View.INVISIBLE);
                    reload.setVisibility(View.INVISIBLE);


                }
            });

            store.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((keyCode == KeyEvent.KEYCODE_BACK)) {

                        if (store.canGoBack()) {

                            store.goBack();
                        } else {
                            finish();
                        }
                    }


                    return false;
                }
            });


        }




    }

    static int clearCacheFolder(final File dir, final int numDays) {

        int deletedFiles = 0;
        if (dir!= null && dir.isDirectory()) {
            try {
                for (File child:dir.listFiles()) {

                    //first delete subdirectories recursively
                    if (child.isDirectory()) {
                        deletedFiles += clearCacheFolder(child, numDays);
                    }

                    //then delete the files and subdirectories in this dir
                    //only empty directories can be deleted, so subdirs have been done first
                    if (child.lastModified() < new Date().getTime() - numDays * DateUtils.DAY_IN_MILLIS) {
                        if (child.delete()) {
                            deletedFiles++;
                        }
                    }
                }
            }
            catch(Exception e) {
                // Log.e(TAG, String.format("Failed to clean the cache, error %s", e.getMessage()));
            }
        }
        return deletedFiles;
    }


    /*
     * Delete the files older than numDays days from the application cache
     * 0 means all files.
     */
    public static void clearCache(final Context context, final int numDays) {
        // Log.i(TAG, String.format("Starting cache prune, deleting files older than %d days", numDays));
        clearCacheFolder(context.getCacheDir(), numDays);
        // Log.i(TAG, String.format("Cache pruning completed, %d files deleted", numDeletedFiles));
    }

    public class storeDownloader implements DownloadListener {



        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {

            File direct = new File(Environment.getExternalStorageDirectory()
                    + "/Electron");

            if (!direct.exists()) {
                direct.mkdirs();
            }

            // File file = new File(direct, url.substring(url.lastIndexOf("/")));




            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

            File file = new File(direct, Uri.parse(url).getLastPathSegment());
            request.setDestinationUri(Uri.fromFile(file));

            byte[] x = url.getBytes();

            int j = x.length;



            request.setMimeType("application/vnd.android.package-archive");
            request.setTitle(Uri.parse(url).getLastPathSegment());

            request.setDescription("Click to Install when complete");
            if (android.os.Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.HONEYCOMB) {

                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            }
            // System.out.println(file.getName());
            // request.setDestinationInExternalPublicDir("/Electron",file.getName());
            DownloadManager dm = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);


            dm.enqueue(request);
            Intent intent = new Intent(Intent.ACTION_INSTALL_PACKAGE); //This is important!
            intent.addCategory(Intent.CATEGORY_OPENABLE); //CATEGORY.OPENABLE



            Toast.makeText(getApplicationContext(), "Downloading App...", //To notify the Client that the file is being downloaded
                    Toast.LENGTH_LONG).show();




        }


    }

    @Override
    public void onBackPressed()
    {
        if (store.canGoBack())
        {
            store.goBack();
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {

        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow

            //Toast.makeText(getApplicationContext(), query, Toast.LENGTH_LONG).show();

            setUpWebViewforSearch();
        }
    }


}
