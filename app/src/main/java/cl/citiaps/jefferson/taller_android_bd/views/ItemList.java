package cl.citiaps.jefferson.taller_android_bd.views;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.jar.JarEntry;

import cl.citiaps.jefferson.taller_android_bd.R;
import cl.citiaps.jefferson.taller_android_bd.controllers.HttpGet;
import cl.citiaps.jefferson.taller_android_bd.utilities.JsonHandler;
import cl.citiaps.jefferson.taller_android_bd.utilities.SystemUtilities;

/**
 * @author: Jefferson Morales De la Parra
 * Clase Fragmento (Lista) que se utiliza para mostrar una lista de items
 */
public class ItemList extends ListFragment {

    private BroadcastReceiver br = null;
    private String URL_GET;
    private String result;
    private BroadcastReceiver br2;
    private BroadcastReceiver failed;

    /**
     * Constructor. Obligatorio para Fragmentos!
     */
    public ItemList() {
    }// ItemList()

    /**
     * Método que se llama una vez que se ha creado la actividad que contiene al fragmento
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        URL_GET = getString(R.string.ip)+"actors";
    }// onActivityCreated(Bundle savedInstanceState)

    /**
     * Método que escucha las pulsaciones en los items de la lista
     */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        String item = l.getItemAtPosition(position).toString();
        Fragment itemDetail = new ItemDetail();
        Bundle arguments = new Bundle();
        JsonHandler jh = new JsonHandler();
        String[] r = jh.getActorsDetail(this.result, getListAdapter().getCount()-position-1);

        arguments.putString("id", r[1]);
        arguments.putString("nombre", r[0]);
        arguments.putString("lu", r[2]);
        itemDetail.setArguments(arguments);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, itemDetail);
        transaction.addToBackStack(null);
        transaction.commit();
    }// onListItemClick(ListView l, View v, int position, long id)

    /**
     * Método que se ejecuta luego que el fragmento es creado o restaurado
     */
    @Override
    public void onResume() {
        IntentFilter intentFilter = new IntentFilter("httpData");
        IntentFilter intentFilterFailed = new IntentFilter("failed");
        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                JsonHandler jh = new JsonHandler();
                String[] actorsList = jh.getActors(intent.getStringExtra("data"));
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity()
                        , android.R.layout.simple_list_item_1, actorsList);
                setListAdapter(adapter);
            }
        };
        failed = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(context, "Error de conexion revise su conexión a internet.", Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "Recuerde colocar la ip correcta en res/values/ip.xml.", Toast.LENGTH_SHORT).show();

                try {
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.remove(ItemList.this);
                    transaction.replace(R.id.fragment_container, new ReloadItemList());
                    transaction.commit();
                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        };
        getActivity().registerReceiver(br, intentFilter);
        getActivity().registerReceiver(failed, intentFilterFailed);

        SystemUtilities su = new SystemUtilities(getActivity().getApplicationContext());
        if (su.isNetworkAvailable()) {
            new HttpGet(getActivity(), this).execute(URL_GET);

        }
        super.onResume();
    }// onResume()

    /**
     * Método que se ejecuta luego que el fragmento se detiene
     */
    @Override
    public void onPause() {
        if (br != null) {
            getActivity().unregisterReceiver(br);
        }
        if( failed != null){
            getActivity().unregisterReceiver(failed);
        }
        super.onPause();
    }// onPause()

    public void setString(String result){
        this.result = result;
    }

}// ItemList extends ListFragment