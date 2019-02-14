package com.example.mostafa.bookgo;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {
    private ListView listView;
    private BookAdapter adapter;
    private String mUrlRequestGoogleBooks = "";
    public static final String BOOK_DETAIL_KEY = "book";
    private static final int BOOK_LOADER_ID = 1;
    private TextView mEmptyStateTextView;

    private SearchView mSearchViewField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchViewField=(SearchView)findViewById(R.id.search_view_field);
        listView = (ListView) findViewById(R.id.list_it);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        listView.setEmptyView(mEmptyStateTextView);
        Button mSearchButton = (Button) findViewById(R.id.search);
        adapter = new BookAdapter(this, new ArrayList<Book>());

        listView.setAdapter(adapter);
        // clicable
        setupBookSelectedListener();
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        final NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(BOOK_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText("no internet connection");
        }
        mSearchButton.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {

                // Check connection status


                if (networkInfo != null && networkInfo.isConnected()) {
                    // Update URL and restart loader to displaying new result of searching
                    updateQueryUrl(mSearchViewField.getQuery().toString());
                    restartLoader();
                } else {
                    // Clear the adapter of previous book data
                    adapter.clear();
                    // Set mEmptyStateTextView visible
                    mEmptyStateTextView.setVisibility(View.VISIBLE);
                    // ...and display message: "No internet connection."
                    mEmptyStateTextView.setText("No internet connection ");
                }

            }

        });
    }
    private String updateQueryUrl(String searchValue) {

        if (searchValue.contains(" ")) {
            searchValue = searchValue.replace(" ", "+");
        }

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String minSearch = sharedPrefs.getString(
                getString(R.string.settings_min_search_key),
                getString(R.string.settings_min_search_default));




        StringBuilder sb = new StringBuilder();
        sb.append("https://www.googleapis.com/books/v1/volumes?q=").append(searchValue).append("&maxResults=").append(minSearch);

        mUrlRequestGoogleBooks = sb.toString();
        return mUrlRequestGoogleBooks;
    }


    public void restartLoader() {
        mEmptyStateTextView.setVisibility(View.GONE);

        getLoaderManager().restartLoader(BOOK_LOADER_ID, null, MainActivity.this);
    }
    // method for open details
    public void setupBookSelectedListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Launch the detail view passing book as an extra
                Intent intent = new Intent(MainActivity.this, BookDetails.class);
                intent.putExtra(BOOK_DETAIL_KEY, adapter.getItem(position));
                startActivity(intent);
            }
        });
    }


    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        return new BookLoader(this, mUrlRequestGoogleBooks);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        // Clear the adapter of previous book
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
        mEmptyStateTextView.setText("no data found");
        adapter.clear();

        // If there is a valid list of {@link BOOK}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (books != null && !books.isEmpty()) {
            adapter.addAll(books);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        adapter.clear();
    }


}