package cl.citiaps.jefferson.taller_android_bd.views;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import cl.citiaps.jefferson.taller_android_bd.R;
import cl.citiaps.jefferson.taller_android_bd.controllers.HttpGet;
import cl.citiaps.jefferson.taller_android_bd.utilities.JsonHandler;
import cl.citiaps.jefferson.taller_android_bd.utilities.SystemUtilities;

/**
 * @author: Jefferson Morales De la Parra
 * Clase Fragmento que se utiliza para mostrar el detalle de los items de la lista
 */
public class ItemDetail extends Fragment {

    private BroadcastReceiver br = null;
    private final String URL_GET = "http://192.168.1.146:8080/sakila-backend-master/actors";
    /**
     * Constructor. Obligatorio para Fragmentos!
     */
    public ItemDetail() {
    }// ItemDetail()

    /**
     * Método que crea la vista del fragmento
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.item_detail, container, false);
    }// onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)

    /**
     * Método que se llama una vez que se ha restaurado el estado del fragmento
     */
    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        ((TextView) getView().findViewById(R.id.text_nombre)).setText(bundle.getString("nombre"));
        ((TextView) getView().findViewById(R.id.text_id)).setText(bundle.getString("id"));
        ((TextView) getView().findViewById(R.id.text_detail)).setText(bundle.getString("lu"));

        /*IntentFilter intentFilter = new IntentFilter("httpData");
        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                JsonHandler jh = new JsonHandler();
                String[] actorsList = jh.getActorsDetail(intent.getStringExtra("data"), nombre, apellido);
                ((TextView)getView().findViewById(R.id.item_id)).setText(actorsList[0]);

            }
        };
        getActivity().registerReceiver(br, intentFilter);
        SystemUtilities su = new SystemUtilities(getActivity().getApplicationContext());
        if (su.isNetworkAvailable()) {
            new HttpGet(getActivity().getApplicationContext()).execute(URL_GET);
        }
        */

        super.onViewStateRestored(savedInstanceState);
    }// onViewStateRestored(Bundle savedInstanceState)

}// ItemDetail extends Fragment