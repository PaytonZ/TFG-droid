package com.bsod.tfg.vista;


import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bsod.tfg.R;
import com.bsod.tfg.controlador.AdapterTablon;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTablon extends Fragment implements SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener {

    private SwipeRefreshLayout swipeLayout;
    private ListView tablonList;

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


        tablonList = (ListView) rootView.findViewById(R.id.list_tablon);
        String[] values = new String[]{"Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
                "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
                "Android", "iPhone", "WindowsMobile"};

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }
        final AdapterTablon adapter = new AdapterTablon(getActivity(),
                android.R.layout.simple_list_item_1, list);
        tablonList.setAdapter(adapter);

        tablonList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //Animacion al hacer click de eliminar
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                view.animate().setDuration(2000).alpha(0)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                list.remove(item);
                                adapter.notifyDataSetChanged();
                                view.setAlpha(1);
                            }
                        });
            }

        });

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
/* Hack taken from
http://nlopez.io/swiperefreshlayout-with-listview-done-right/
*/

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int topRowVerticalPosition =
                (tablonList == null || tablonList.getChildCount() == 0) ?
                        0 : tablonList.getChildAt(0).getTop();
        swipeLayout.setEnabled(topRowVerticalPosition >= 0);
    }
}
