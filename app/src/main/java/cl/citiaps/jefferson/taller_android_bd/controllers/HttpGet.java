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

/**
 * @author: Jefferson Morales De la Parra
 * Clase que se utiliza para realizar peticiones HTTP mediante el método GET
 */
public class HttpGet extends AsyncTask<String, Void, String> {

    private Context context;

    /**
     * Constructor
     */
    public HttpGet(Context context) {
        this.context = context;
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
            Toast.makeText(context, "Revise su conexión a internet. 4g", Toast.LENGTH_LONG).show();
            ((Activity)context).finish();
        }
        else{
            Intent intent = new Intent("httpData").putExtra("data", result);
            context.sendBroadcast(intent);
        }

    }// onPostExecute(String result)

}// HttpGet extends AsyncTask<String, Void, String>