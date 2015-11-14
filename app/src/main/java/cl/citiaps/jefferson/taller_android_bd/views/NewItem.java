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
import android.widget.Button;
import android.widget.EditText;

import cl.citiaps.jefferson.taller_android_bd.controllers.HttpPostActor;

/**
 * @author:
 */
import cl.citiaps.jefferson.taller_android_bd.R;

public class NewItem extends Fragment {

    private EditText nombre;
    private EditText apellido;
    private final String URL_POST = "http://192.168.1.146:8080/sakila-backend-master/actors";
    private BroadcastReceiver br;

    /**
     * Constructor. Obligatorio para Fragmentos!
     */
    public NewItem() {
    }// NewItem()

    /**
     * Método que crea la vista del fragmento
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_item, container, false);
        nombre = (EditText) view.findViewById(R.id.nombreText);
        apellido = (EditText) view.findViewById(R.id.apellidoText);
        final Button button = (Button) view.findViewById(R.id.newActorButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                HttpPostActor post = new HttpPostActor(getActivity());
                post.execute(
                        nombre.getText().toString(),
                        apellido.getText().toString());
            }
        });

        IntentFilter intentFilter = new IntentFilter("httpPost");
        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                NewItem.this.getFragmentManager().popBackStackImmediate();
            }
        };
        getActivity().registerReceiver(br, intentFilter);

        return view;
    }// onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)

    /**
     * Método que se ejecuta luego que el fragmento se detiene
     */
    @Override
    public void onPause() {
        if (br != null) {
            getActivity().unregisterReceiver(br);
        }
        super.onPause();
    }// onPause()

}// NewItem extends Fragment