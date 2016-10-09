package company.drink.com.drinkingsveeg.helpers;

import android.os.AsyncTask;
import android.util.Pair;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by petar on 9.10.16..
 */

public class Constants {
    private static Constants instance;
    public static Constants getInstance()
    {
        if(instance==null)
            instance=new Constants();
        return instance;
    }
    private Constants()
    {

    }



    public interface IGetInterface
    {
        void onStart();
        void onFinish(String result);
    }
    public void GET(String url,IGetInterface iGetInterface)
    {
        new DownloadFilesTask(url,iGetInterface).execute();
    }
    public void POST(String url, List<Pair<String,String>> params, IGetInterface iGetInterface)
    {
        new DownloadPOSTFilesTask(url,params,iGetInterface).execute();
    }
    private class DownloadPOSTFilesTask extends AsyncTask<Void, Void, Void> {

        String url;
        IGetInterface iGetInterface;
        String result;
        List<Pair<String,String>> params;
        public DownloadPOSTFilesTask(String url,List<Pair<String,String>> params,IGetInterface iGetInterface)
        {
            this.url=url;
            this.iGetInterface=iGetInterface;
            this.params=params;
        }

        @Override
        protected void onPreExecute() {
            if(iGetInterface!=null)
                iGetInterface.onStart();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(iGetInterface!=null)
            {
                iGetInterface.onFinish(result);
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            URL url = null;
            try {
                url = new URL(this.url);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);


                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getQuery(this.params));
                writer.flush();
                writer.close();
                os.close();

                conn.connect();
                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    while ((line=br.readLine()) != null) {
                        result+=line;
                    }
                }
                else {
                    result="";

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
        private String getQuery(List<Pair<String,String>> params) throws UnsupportedEncodingException
        {
            StringBuilder result = new StringBuilder();
            boolean first = true;

            for (Pair<String,String> pair : params)
            {
                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(pair.first, "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(pair.second, "UTF-8"));
            }

            return result.toString();
        }
    }
    private class DownloadFilesTask extends AsyncTask<Void, Void, Void> {

        String url;
        IGetInterface iGetInterface;
        String result;
        public DownloadFilesTask(String url,IGetInterface iGetInterface)
        {
            this.url=url;
            this.iGetInterface=iGetInterface;
        }

        @Override
        protected void onPreExecute() {
            if(iGetInterface!=null)
                iGetInterface.onStart();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(iGetInterface!=null)
            {
                iGetInterface.onFinish(result);
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            HttpURLConnection urlConnection=null;
            try {
                URL mUrl = new URL(url);
                urlConnection= (HttpURLConnection) mUrl.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                StringBuilder total = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    total.append(line).append('\n');
                }
                result=total.toString();
            }catch (Exception ex)
            {

            }finally {
                if(urlConnection!=null)
                    urlConnection.disconnect();
            }
            return null;
        }
    }
}
