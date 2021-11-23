package com.example.practice_video_cp9;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

public class MainActivity extends AppCompatActivity {

    TextView responseText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        WebView webView=(WebView) findViewById(R.id.web_view);
//        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//        webView.setWebViewClient(new WebViewClient());
//        webView.loadUrl("http://www.baidu.com");
        Button sendRequest=(Button) findViewById(R.id.send_request);
        responseText=(TextView) findViewById(R.id.response_text);
        sendRequest.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.send_request){
                    sendRequestWithOkConnection();
                }
            }
        });
    }

    private void sendRequestWithOkConnection() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client=new OkHttpClient();
                    final ProxySelector proxySelector = new ProxySelector() {
                        @Override
                        public java.util.List<Proxy> select(final URI uri) {
                            final List<Proxy> proxyList = new ArrayList<Proxy>(1);

                            // Host
                            final String host = uri.getHost();

//                            // Is an internal host
//                            if (host.startsWith("127.0.0.1") || StringUtils.contains(nonProxyHostsValue, host)) {
//
//                            } else {
//                                // Add proxy
//                                proxyList.add(new Proxy(Type.HTTP,
//                                        new InetSocketAddress(proxyHostNameValue, Integer.parseInt(proxyPortValue))));
//                            }
                            proxyList.add(Proxy.NO_PROXY);
                            return proxyList;
                        }

                        @Override
                        public void connectFailed(URI arg0, SocketAddress arg1, IOException arg2) {
                            throw new UnsupportedOperationException("Not supported yet.");
                        }
                    };

                    Request request = new Request.Builder()
                        .url("http://10.64.122.72/get_data.json")
                        .build();
                    Response response = client.newCall(request).execute();
                    String responseData=response.body().string();
                    parseJsonWithJsonObject(responseData);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJsonWithJsonObject(String jsonData) {
        try {
            JSONArray jsonArray =new JSONArray(jsonData);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                String id=jsonObject.getString("id");
                String name =jsonObject.getString("name");
                String version=jsonObject.getString("version");
                Log.d("MainActivity","id is "+id);
                Log.d("MainActivity","name is "+name);
                Log.d("MainActivity","version is "+version);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void parseXMLWithPull(String xmlData) {
        try {
            XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));
            int evenType= xmlPullParser.getEventType();
            String id="";
            String name="";
            String version="";
            while(evenType!=XmlPullParser.END_DOCUMENT){
                String nodeName=xmlPullParser.getName();
                switch (evenType){
                    case XmlPullParser.START_TAG:{
                        if("id".equals(nodeName)){
                            id=xmlPullParser.nextText();
                        }else if("name".equals(nodeName)){
                            name=xmlPullParser.nextText();
                        }else if("version".equals(nodeName)){
                            version=xmlPullParser.nextText();
                        }
                        break;
                    }
                    case XmlPullParser.END_TAG:{
                        if("app".equals(nodeName)){
                            Log.d("MainActivity","id is "+id);
                            Log.d("MainActivity","name is "+name);
                            Log.d("MainActivity","version is "+version);
                        }
                        break;
                    }
                    default:
                        break;
                }
                evenType=xmlPullParser.next();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void sendRequestWithHttpURLConnection() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection=null;
                BufferedReader reader=null;
                try {
                    URL url=new URL("http://www.baidu.com");
                    connection=(HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in=connection.getInputStream();
                    reader=new BufferedReader(new InputStreamReader(in));
                    StringBuilder response=new StringBuilder() ;
                    String line;
                    while((line=reader.readLine())!=null){
                        response.append(line);
                    }
                    showResponse(response.toString());
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if(reader!=null){
                        try {
                            reader.close();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                    if(connection!=null){
                        connection.disconnect();
                    }

                }
            }
        }).start();
    }

    private void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                responseText.setText(response);
            }
        });
    }
}