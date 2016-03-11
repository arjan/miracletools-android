package nl.miraclethings.tools.ui;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;

/**
 * Created by arjan on 9-3-16.
 */
public class SearchUtil {
    public interface SearchListener {
        void onSearchOpen();
        void onSearchClose();
        void onSearchTextChange(String text);
        void onSearchTextSubmit(String text);
    }

    public static void hookupSearchView(Activity context, MenuItem item, final SearchListener listener) {
        // Retrieve the SearchView and plug it into SearchManager
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);

        SearchManager searchManager = (SearchManager) context.getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(context.getComponentName()));

        MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                listener.onSearchOpen();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                listener.onSearchClose();
                return true;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                listener.onSearchTextSubmit(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                listener.onSearchTextChange(query);
                return false;
            }
        });

    }
}
