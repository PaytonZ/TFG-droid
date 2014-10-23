package com.bsod.tfg.vista;


import android.app.ActionBar;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.bsod.tfg.R;
import com.bsod.tfg.controlador.AdapterTablon;
import com.bsod.tfg.modelo.MessageBoard;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTablon extends Fragment implements SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener {

    private static final String TAG = "FragmentTablon";
    private SwipeRefreshLayout swipeLayout;
    private ListView tablonList;
    private ActionBar aBar;

    private int mLastFirstVisibleItem;
    private boolean mIsScrollingUp;

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

        final ArrayList<MessageBoard> list = new ArrayList<MessageBoard>();

        MessageBoard mb = new MessageBoard();
        mb.setMessage("Holi :)");
        list.add(mb);
        mb = new MessageBoard();
        mb.setMessage("Adiosi :)");
        list.add(mb);
        mb = new MessageBoard();
        mb.setMessage("Wut :)");
        list.add(mb);
        mb = new MessageBoard();
        mb.setMessage("whatdafaq :)");
        list.add(mb);
        mb = new MessageBoard();
        mb.setMessage("lol nubs :)");
        list.add(mb);
        mb = new MessageBoard();
        mb.setMessage("aksjdkasjdkajskdjaskd :)");
        list.add(mb);
        mb = new MessageBoard();
        mb.setMessage("keybaoardhacker :)");
        list.add(mb);


        final AdapterTablon adapter = new AdapterTablon(getActivity());
        tablonList.setAdapter(adapter);
        adapter.updateMessages(list);
        tablonList.setOnItemClickListener(adapter);


        tablonList.setOnScrollListener(this);

        // Devolvemos la vista para que se muestre en pantalla.
        return rootView;
    }


    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeLayout.setRefreshing(false);
            }
        }, 5000);

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
}
