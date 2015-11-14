package cl.citiaps.jefferson.taller_android_bd.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

import cl.citiaps.jefferson.taller_android_bd.views.ItemList;

/**
 * @author: Jefferson Morales De la Parra
 * Clase que se utiliza para realizar peticiones HTTP mediante el método GET
 */
public class HttpGet extends AsyncTask<String, Void, String> {

    private Context context;
    private ItemList lista = null;

    /**
     * Constructor
     */
    public HttpGet(Context context) {
        this.context = context;
    }// HttpGet(Context context)
    public HttpGet(Context context, ItemList l) {
        this.context = context;
        this.lista = l;
    }// HttpGet(Context context)

    /**
     * Método que realiza la petición al servidor
     */
    @Override
    protected String doInBackground(String... urls) {
        try {
            URL url = new URL(urls[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(10000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();
            return new Scanner(connection.getInputStream(), "UTF-8").useDelimiter("\\A").next();
        } catch (MalformedURLException e) {
            Log.e("ERROR", this.getClass().toString() + " " + e.toString());
        } catch (ProtocolException e) {
            Log.e("ERROR", this.getClass().toString() + " " + e.toString());
        } catch (IOException e) {
            Log.e("ERROR", this.getClass().toString() + " " + e.toString());
        } catch (Exception e){
            return null;
        }
        return null;
    }// doInBackground(String... urls)

    /**
     * Método que manipula la respuesta del servidor
     */
    @Override
    protected void onPostExecute(String result) {
        if(result==null){
            Intent intent = new Intent("failed").putExtra("data", result);
            Log.e("ERROR", "Conexion fallida, recuerde colocar la ip correcta en res/values/ip.xml");
            context.sendBroadcast(intent);
        }
        else{
            Intent intent = new Intent("httpData").putExtra("data", result);
            context.sendBroadcast(intent);
            if (lista !=  null)
            lista.setString(result);
        }

    }// onPostExecute(String result)

}// HttpGet extends AsyncTask<String, Void, String>