package cl.citiaps.jefferson.taller_android_bd.views;

import android.app.Fragment;
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
                NewItem.this.getFragmentManager().popBackStackImmediate();
            }

        });
        return view;
    }// onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)



}// NewItem extends Fragment