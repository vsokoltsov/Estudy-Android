package com.example.vsokoltsov.estudy.views.navigation;

import com.example.vsokoltsov.estudy.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.example.vsokoltsov.estudy.adapters.NavigationListAdapter;
import com.example.vsokoltsov.estudy.models.NavigationItem;
import com.example.vsokoltsov.estudy.views.UsersListActivity;
import com.example.vsokoltsov.estudy.views.authorization.AuthorizationActivity;
import com.example.vsokoltsov.estudy.views.chats.ChatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vsokoltsov on 13.03.16.
 */
public class NavigationDrawer extends Fragment {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerListView;
    private View mFragmentContainerView;
    private NavigationDrawerCallbacks mCallbacks;
    public NavigationListAdapter adapter;
    private List<NavigationItem> navigationItems = new ArrayList<NavigationItem>();
    private int mCurrentSelectedPosition = 0;
    private boolean mFromSavedInstanceState;
    private ActionBarDrawerToggle mDrawerToggle;
    private FrameLayout rootView;
    private Resources resources;

    public NavigationDrawer() {
        // Required empty public constructor
    }

    public void setDrawerLayout(DrawerLayout layout) {
        this.mDrawerLayout = layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resources = getResources();

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt("selected_navigation_drawer_position");
            mFromSavedInstanceState = true;
        }

        // Select either the default item (0) or the last selected item.
//        selectItem(mCurrentSelectedPosition);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Indicate that this fragment would like to influence the set of actions in the action bar.
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = (FrameLayout) inflater.inflate(R.layout.navigation_drawer_fragment, container, false);
        mDrawerListView = (ListView) rootView.findViewById(R.id.navigation_list);
        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                navigationItemActions(position);
            }
        });
//        mDrawerListView = (ListView) inflater.inflate(
//                R.layout.fragment_navigation, container, false);
        setupElementsList();
        adapter.notifyDataSetChanged();
        mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);
        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        try {
//            mCallbacks = (NavigationDrawerCallbacks) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void selectItem(int position) {
        mCurrentSelectedPosition = position;
        if (mDrawerListView != null) {
            mDrawerListView.setItemChecked(position, true);
        }
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        navigationItemActions(position);
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    private ActionBar getActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
//        public void onFragmentInteraction(Uri uri);
    }

//    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        /** Invokes the implementation of the method onListFragmentItemClick in the hosting activity */
        selectItem(position);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle != null && mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

//        if (item.getItemId() == R.id.sign_in) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    public static interface NavigationDrawerCallbacks {
        /**
         * Called when an item in the navigation drawer is selected.
         */
        void onNavigationDrawerItemSelected(int position);
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        // set a custom shadow that overlays the main content when the drawer opens
        // set up the drawer's list view with items and click listener

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(R.color.highlighted_text_material_light));
        actionBar.setHomeAsUpIndicator(R.drawable.menu);
        actionBar.setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),                    /* host Activity */
                mDrawerLayout,                    /* DrawerLayout object */
                R.drawable.menu,             /* nav drawer image to replace 'Up' caret */
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }

                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }

                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };

        // If the user hasn't 'learned' about the drawer, open it to introduce them to the drawer,
        // per the navigation drawer design guidelines.
//        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
//            mDrawerLayout.openDrawer(mFragmentContainerView);
//        }

        // Defer code dependent on restoration of previous instance state.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }


    //NAvigation item actions
    private void navigationItemActions(int position) {
        NavigationItem navItem = navigationItems.get(position);

        String signIn = resources.getString(R.string.nav_sign_in);
        String signUp = resources.getString(R.string.nav_sign_up);
        String users = resources.getString(R.string.nav_users);
        String messages = resources.getString(R.string.nav_chats);

        if (navItem.getTitle().equals(signIn)) {
            Intent authActivity = new Intent(getActivity(), AuthorizationActivity.class);
            startActivity(authActivity);
            getActivity().overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            return;
        } else if (navItem.getTitle().equals(signUp)) {
            Intent regActivity = new Intent(getActivity(), AuthorizationActivity.class);
            startActivity(regActivity);
            getActivity().overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            return;
        } else if (navItem.getTitle().equals(users)) {
            Intent usersActivity = new Intent(getActivity(), UsersListActivity.class);
            startActivity(usersActivity);
            getActivity().overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            return;
        } else if (navItem.getTitle().equals(messages)) {
            Intent chatsActivity = new Intent(getActivity(), ChatActivity.class);
            startActivity(chatsActivity);
            getActivity().overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            return;
        }
        mDrawerLayout.closeDrawer(rootView);
    }


    public void setupElementsList(){
//        Resources res = getResources();
//        if(navigationItems != null) navigationItems = new ArrayList<NavigationItem>();
//        if (authManager.getCurrentUser() != null) {
//            navigationItems.add(new NavigationItem(authManager.getCurrentUser()));
//        }
//        else {

            navigationItems.add(new NavigationItem(R.drawable.sign_in, resources.getString(R.string.nav_sign_in)));
            navigationItems.add(new NavigationItem(R.drawable.sign_up, resources.getString(R.string.nav_sign_up)));
//        }
        navigationItems.add(new NavigationItem(R.drawable.contacts, resources.getString(R.string.nav_users)));
        navigationItems.add(new NavigationItem(R.drawable.chats, resources.getString(R.string.nav_chats)));
        setSignOutButton();
        adapter = new NavigationListAdapter(getActivity(), navigationItems);
        mDrawerListView.setAdapter(adapter);
    }

    public void setSignOutButton() {
        Button signOutButton = (Button) rootView.findViewById(R.id.sign_out_button);
//        if (authManager.getCurrentUser() != null) {
//            signOutButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    signOut();
//                }
//            });
//            signOutButton.setVisibility(View.VISIBLE);
//        }
//        else {
            signOutButton.setVisibility(View.INVISIBLE);
//        }
    }

    public void signOut() {
//        authManager.setCurrentUser(null);
//        SharedPreferences.Editor editor = (SharedPreferences.Editor)
//                getActivity().getSharedPreferences("stackqa", Context.MODE_PRIVATE).edit();
//        editor.putString("stackqaemail", null);
//        editor.putString("stackqapassword", null);
//        editor.commit();
//        authManager.signOut();
    }
}