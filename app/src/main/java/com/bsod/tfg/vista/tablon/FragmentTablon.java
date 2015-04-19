package com.bsod.tfg.vista.tablon;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.bsod.tfg.R;
import com.bsod.tfg.controlador.bbdd.DataBaseHelper;
import com.bsod.tfg.controlador.tablon.AdapterTablon;
import com.bsod.tfg.modelo.otros.Constants;
import com.bsod.tfg.modelo.sesion.Session;
import com.bsod.tfg.modelo.sesion.User;
import com.bsod.tfg.modelo.tablon.MessageBoard;
import com.bsod.tfg.modelo.tablon.MessageBoardUpdate;
import com.bsod.tfg.utils.HttpClient;
import com.bsod.tfg.utils.JsonHttpResponseHandlerCustom;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTablon extends Fragment implements SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener, DialogSendMessageBoard.DialogSendMessageBoardListener, AdapterView.OnItemClickListener {

    private static final String TAG = "FragmentTablon";
    private SwipeRefreshLayout swipeLayout;
    private ListView tablonList;
    private ActionBar aBar;
    private Context thisContext;
    private int mLastFirstVisibleItem;
    private boolean mIsScrollingUp;
    private AdapterTablon aTablon;
    private View rootView;
    private Dao<MessageBoard, Integer> daoMessageBoard;
    private Dao<User, Integer> daoUsers;


    public static Fragment newInstance() {
        return new FragmentTablon();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /* Inflamos la Vista que se debe mostrar en pantalla.
        if (rootView == null) {*/
        rootView = inflater.inflate(R.layout.fragment_tablon, container,
                false);

        swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_tablon);
        swipeLayout.setOnRefreshListener(this);

        aBar = getActivity().getActionBar();

        tablonList = (ListView) rootView.findViewById(R.id.list_tablon);

        final AdapterTablon adapter = (aTablon == null) ? aTablon = new AdapterTablon(getActivity()) : aTablon;
        tablonList.setAdapter(adapter);
        tablonList.setOnItemClickListener(this);
        tablonList.setOnScrollListener(this);

        new Thread() {
            @Override
            public void run() {
                DataBaseHelper db = DataBaseHelper.getInstance();
                try {
                    daoMessageBoard = db.getDAOMessageBoard();
                    daoUsers = db.getDAOUser();
                    final List<MessageBoard> list = daoMessageBoard.query(daoMessageBoard.queryBuilder().orderBy("id", false).prepare());
                    Log.i(TAG, "Existen ".concat(String.valueOf(list.size())).concat(" mensajes en la base de datos local."));
                    for (MessageBoard mb : list) {
                        daoUsers.refresh(mb.getUser());
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ((AdapterTablon) tablonList.getAdapter()).addMessages(list);
                            refreshMessages();
                        }
                    });

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }.start();


        thisContext = getActivity();

        // Devolvemos la vista para que se muestre en pantalla.

        setHasOptionsMenu(true);
        /*} else {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }*/
        return rootView;
    }

    @Override
    public void onRefresh() {
        //refreshMessages();
        updateMessages();
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

    private void updateMessages() {
        RequestParams params = new RequestParams();
        int elementCount = aTablon.getCount();
        params.put("token", Session.getSession().getToken().getToken());
        params.put("idmsgstart", (elementCount == 0) ? 0 : aTablon.getItem(elementCount - 1).getId());
        params.put("idmsgend", ((elementCount == 0) ? 0 : aTablon.getItem(0).getId()));
        params.put("idfaculty", Session.getSession().getFacultad().getId());

        HttpClient.get(Constants.HTTP_UPDATE_MESSAGES, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                int error;
                try {
                    error = Integer.parseInt(response.get("error").toString());
                    if (error == 200) {
                        ObjectMapper mapper = new ObjectMapper();
                        //mapper.registerModule(new JsonOrgModule());
                        final List<MessageBoardUpdate> listOfMessagesUpdated = mapper.readValue(
                                response.get("data").toString(), new TypeReference<List<MessageBoardUpdate>>() {
                                });
                        aTablon.updateMessages(listOfMessagesUpdated);

                        new Thread() {
                            @Override
                            public void run() {

                                for (MessageBoardUpdate mbu : listOfMessagesUpdated) {
                                    if (mbu.isBorrado()) {
                                        try {
                                            DeleteBuilder<MessageBoard, Integer> db = daoMessageBoard.deleteBuilder();
                                            db.where().eq("id", mbu.getId());
                                            db.delete();
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }.start();

                        refreshMessages();

                    }
                } catch (Exception e) {
                    swipeLayout.setRefreshing(false);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(thisContext, thisContext.getString(R.string.error_update_messages), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void refreshMessages() {


        RequestParams params = new RequestParams();
        params.put("token", Session.getSession().getToken().getToken());
        params.put("idmessage", (aTablon.getCount() == 0) ? 0 : ((AdapterTablon) tablonList.getAdapter()).getItem(0).getId());
        params.put("idfaculty", Session.getSession().getFacultad().getId());

        HttpClient.get(Constants.HTTP_GET_MESSAGES_BOARD, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                int error;
                try {
                    error = Integer.parseInt(response.get("error").toString());
                    if (error == 200) {
                        ObjectMapper mapper = new ObjectMapper();
                        //mapper.registerModule(new JsonOrgModule());
                        mapper.configure(SerializationFeature.USE_EQUALITY_FOR_OBJECT_ID, true);

                        List<MessageBoard> listOfMessagesUpdated = mapper.readValue(
                                response.get("data").toString(), new TypeReference<List<MessageBoard>>() {
                                });
                        //listOfMessages.addAll(0, listOfMessagesUpdated);
                        ((AdapterTablon) tablonList.getAdapter()).addMessages(listOfMessagesUpdated);
                        swipeLayout.setRefreshing(false);


                        for (MessageBoard mb : aTablon.getMessageList()) {
                            daoMessageBoard.createOrUpdate(mb);
                            daoUsers.createIfNotExists(mb.getUser());
                        }


                    } else {
                        if (error == 201) {
                            //Toast.makeText(thisContext, R.string.tablon_no_nuevos_mensajes, Toast.LENGTH_SHORT).show();
                            swipeLayout.setRefreshing(false);
                        }
                    }
                } catch (Exception e) {
                    swipeLayout.setRefreshing(false);
                    Toast.makeText(thisContext, thisContext.getString(R.string.error_update_messages), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(thisContext, thisContext.getString(R.string.error_update_messages), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onSendMessageBoard(String inputText, boolean anonymous) {

        RequestParams params = new RequestParams();
        params.put("token", Session.getSession().getToken().getToken());
        params.put("message", inputText);
        params.put("idfaculty", Session.getSession().getFacultad().getId());
        params.put("anonymous", anonymous);

        HttpClient.get(Constants.HTTP_POST_MESSAGES_BOARD, params, new JsonHttpResponseHandlerCustom(getActivity()) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                int error;
                try {
                    error = Integer.parseInt(response.get("error").toString());
                    if (error == 200) {
                        ObjectMapper mapper = new ObjectMapper();
                        mapper.configure(SerializationFeature.USE_EQUALITY_FOR_OBJECT_ID, true);
                        final List<MessageBoard> listOfMessagesUpdated = mapper.readValue(
                                response.get("data").toString(), new TypeReference<List<MessageBoard>>() {
                                });
                        //listOfMessagesUpdated.addAll(0, listOfMessagesUpdated);
                        aTablon.addMessages(listOfMessagesUpdated);
                        new Thread() {
                            @Override
                            public void run() {
                                try {
                                    for (MessageBoard mb : listOfMessagesUpdated) {
                                        daoMessageBoard.createOrUpdate(mb);
                                        daoUsers.createIfNotExists(mb.getUser());
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(thisContext, thisContext.getString(R.string.error_update_messages), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(thisContext, thisContext.getString(R.string.error_update_messages), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {


        Intent i = new Intent(thisContext, ActivityMessageDetail.class);
        i.putExtra(Constants.INTENT_MESSAGE_DETAIL, aTablon.getItem(position));
        startActivityForResult(i, Constants.INTENT_MESSAGE_DELETED);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "onActivityResult");

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == Constants.INTENT_MESSAGE_DELETED) {
                int id_msg = data.getIntExtra(Constants.INTENT_EXTRA_DELETED_MESSAGE_ID, -1);
                Log.d(TAG, "Deleting message with ID: ".concat(String.valueOf(id_msg)));
                aTablon.deleteMessage(id_msg);
                Toast.makeText(thisContext, "Mensaje borrado correctamente", Toast.LENGTH_SHORT).show();
                try {
                    DeleteBuilder<MessageBoard, Integer> db = daoMessageBoard.deleteBuilder();
                    db.where().eq("id", id_msg);
                    db.delete();
                } catch (SQLException e) {
                    e.printStackTrace();
                }


            }
        }
    }

}
