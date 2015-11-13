package cl.citiaps.jefferson.taller_android_bd.controllers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import cl.citiaps.jefferson.taller_android_bd.R;

/**
 * @author:
 */

public class HttpPostActor extends AsyncTask<String,Integer,Boolean> {

    private Context context;
    private String URL_POST;

    public HttpPostActor(Context c){
        context = c;
        URL_POST = c.getString(R.string.ip)+"actors";
    }

    protected Boolean doInBackground(String... params) {

        boolean resul = true;

        HttpClient httpClient = new DefaultHttpClient();

        HttpPost post = new
                HttpPost(URL_POST);

        post.setHeader("content-type", "application/json");

        try
        {
            //Construimos el objeto cliente en formato JSON
            JSONObject dato = new JSONObject();

            dato.put("firstName", params[0]);
            dato.put("lastName", params[1]);

            StringEntity entity = new StringEntity(dato.toString());
            post.setEntity(entity);

            HttpResponse resp = httpClient.execute(post);
            Log.e("POST", "Code"+resp.getStatusLine().getStatusCode());
        }
        catch(Exception ex)
        {
            Log.e("ServicioRest", "Error!", ex);
            resul = false;
        }

        return resul;
    }

    protected void onPostExecute(Boolean result) {


        if (result){
            Toast.makeText(context, "Se ha agregado Correctamente", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "No se ha podido agregar el Actor, compruebe su coneción", Toast.LENGTH_SHORT).show();
        }
    }
}