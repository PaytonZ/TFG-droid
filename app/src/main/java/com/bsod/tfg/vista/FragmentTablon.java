package com.bsod.tfg.vista;


import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.bsod.tfg.R;
import com.bsod.tfg.controlador.AdapterTablon;
import com.bsod.tfg.modelo.Constants;
import com.bsod.tfg.modelo.MessageBoard;
import com.bsod.tfg.modelo.Session;
import com.bsod.tfg.utils.HttpClient;
import com.bsod.tfg.utils.JsonHttpResponseHandlerCustom;
import com.fasterxml.jackson.module.jsonorg.JsonOrgModule;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTablon extends Fragment implements SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener, DialogSendMessageBoard.DialogSendMessageBoardListener {

    private static final String TAG = "FragmentTablon";
    private SwipeRefreshLayout swipeLayout;
    private ListView tablonList;
    private ActionBar aBar;
    private List<MessageBoard> listOfMessages = new ArrayList<MessageBoard>();
    private Context thisContext;
    private int mLastFirstVisibleItem;
    private boolean mIsScrollingUp;
    private AdapterTablon aTablon;

    public FragmentTablon() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return new FragmentTablon();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflamos la Vista que se debe mostrar en pantalla.
        View rootView = inflater.inflate(R.layout.fragment_tablon, container,
                false);
        swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_tablon);
        swipeLayout.setOnRefreshListener(this);

        aBar = getActivity().getActionBar();

        tablonList = (ListView) rootView.findViewById(R.id.list_tablon);

        final AdapterTablon adapter = (aTablon == null) ? aTablon = new AdapterTablon(getActivity()) : aTablon;
        tablonList.setAdapter(adapter);
        tablonList.setOnItemClickListener(adapter);
        tablonList.setOnScrollListener(this);
        refreshMessages();

        thisContext = getActivity();

        // Devolvemos la vista para que se muestre en pantalla.

        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onRefresh() {
        refreshMessages();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int i) {
        final ListView lw = tablonList;

        if (view.getId() == lw.getId()) {
            final int currentFirstVisibleItem = lw.getFirstVisiblePosition();

            if (currentFirstVisibleItem > mLastFirstVisibleItem && aBar.isShowing()) {
                mIsScrollingUp = false;
                //aBar.hide();
            } else if (currentFirstVisibleItem < mLastFirstVisibleItem && !aBar.isShowing()) {
                mIsScrollingUp = true;
                //aBar.show();
            }

            mLastFirstVisibleItem = currentFirstVisibleItem;
        }
    }

    /* Hack taken from
    http://nlopez.io/swiperefreshlayout-with-listview-done-right/
    */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int topRowVerticalPosition =
                (tablonList == null || tablonList.getChildCount() == 0) ?
                        0 : tablonList.getChildAt(0).getTop();
        swipeLayout.setEnabled(topRowVerticalPosition >= 0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh_messages:
                //Toast.makeText(getActivity(),"REFRESHING",Toast.LENGTH_SHORT).show();
                swipeLayout.setRefreshing(true);
                onRefresh();
                return true;
            case R.id.menu_send_message:
                //Toast.makeText(getActivity(),"SENDING",Toast.LENGTH_SHORT).show();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DialogSendMessageBoard sendMessageBoard = new DialogSendMessageBoard();
                sendMessageBoard.show(fm, "sendMessageBoard");
                return true;
            default:
                break;
        }

        return false;
    }

    private void refreshMessages() {

        RequestParams params = new RequestParams();
        params.put("token", Session.getSession().getToken());
        params.put("idmessage", ((tablonList.getAdapter()).getCount() == 0) ? 0 : ((AdapterTablon) tablonList.getAdapter()).getItem(0).getId());
        params.put("idfaculty", Session.getSession().getFacultadRegistro().getId());

        HttpClient.get(Constants.HTTP_GET_MESSAGES_BOARD, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                int error;
                try {
                    error = Integer.parseInt(response.get("error").toString());
                    if (error == 200) {
                        //swipeLayout.setRefreshing(true);
                        ObjectMapper mapper = new ObjectMapper();
                        mapper.registerModule(new JsonOrgModule());
                        List<MessageBoard> listOfMessagesUpdated = mapper.readValue(
                                response.get("data").toString(),
                                TypeFactory.collectionType(
                                        List.class, MessageBoard.class));
                        listOfMessages.addAll(0, listOfMessagesUpdated);
                        ((AdapterTablon) tablonList.getAdapter()).updateMessages(listOfMessages);
                        swipeLayout.setRefreshing(false);

                    } else {
                        if (error == 201) {
                            //Toast.makeText(thisContext, R.string.tablon_no_nuevos_mensajes, Toast.LENGTH_SHORT).show();
                            swipeLayout.setRefreshing(false);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void onSendMessageBoard(String inputText) {

        RequestParams params = new RequestParams();
        params.put("token", Session.getSession().getToken());
        params.put("message", inputText);
        params.put("idfaculty", Session.getSession().getFacultadRegistro().getId());

        //refreshMessages();

        HttpClient.get(Constants.HTTP_POST_MESSAGES_BOARD, params, new JsonHttpResponseHandlerCustom(getActivity()) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                int error;
                try {
                    error = Integer.parseInt(response.get("error").toString());
                    if (error == 200) {
                        ObjectMapper mapper = new ObjectMapper();
                        List<MessageBoard> listOfMessagesUpdated = mapper.readValue(
                                response.get("data").toString(),
                                TypeFactory.collectionType(
                                        List.class, MessageBoard.class));
                        listOfMessages.addAll(0, listOfMessagesUpdated);
                        ((AdapterTablon) tablonList.getAdapter()).updateMessages(listOfMessages);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        });
    }
}
