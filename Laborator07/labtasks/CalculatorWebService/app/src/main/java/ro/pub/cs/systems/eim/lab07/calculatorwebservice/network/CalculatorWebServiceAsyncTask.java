package ro.pub.cs.systems.eim.lab07.calculatorwebservice.network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.protocol.HTTP;
import cz.msebera.android.httpclient.util.EntityUtils;
import ro.pub.cs.systems.eim.lab07.calculatorwebservice.general.Constants;

public class CalculatorWebServiceAsyncTask extends AsyncTask<String, Void, String> {

    private TextView resultTextView;

    public CalculatorWebServiceAsyncTask(TextView resultTextView) {
        this.resultTextView = resultTextView;
    }

    @Override
    protected String doInBackground(String... params) {
        String operator1 = params[0];
        String operator2 = params[1];
        String operation = params[2];
        int method = Integer.parseInt(params[3]);
        float f1, f2;
        // TODO exercise 4
        // signal missing values through error messages
        try {
            f1 = Float.parseFloat(operator1);
            f2 = Float.parseFloat(operator2);
        } catch (Exception e){
            Log.e(Constants.TAG, "Missing values");
            return null;
        }
        // create an instance of a HttpClient object
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpPostResponse;

            // get method used for sending request from methodsSpinner
            if (method == Constants.GET_OPERATION) {
                // 1. GET
                // a) build the URL into a HttpGet object (append the operators / operations to the Internet address)
                // b) create an instance of a ResultHandler object
                // c) execute the request, thus generating the result
                HttpGet httpGet = new HttpGet(Constants.GET_WEB_SERVICE_ADDRESS
                        + "?" + Constants.OPERATION_ATTRIBUTE + "=" + operation
                        + "&" + Constants.OPERATOR1_ATTRIBUTE + "=" + operator1
                        + "&" + Constants.OPERATOR2_ATTRIBUTE + "=" + operator2);
                try {
                    httpPostResponse = httpClient.execute(httpGet);
                    HttpEntity httpPostEntity = httpPostResponse.getEntity();
                    if (httpPostEntity != null) {
                        // do something with the response
                        String s = EntityUtils.toString(httpPostEntity);
                        Log.i(Constants.TAG, s);
                        return s;
                    }
                } catch (Exception exception) {
                    Log.e(Constants.TAG, exception.getMessage());
                    if (Constants.DEBUG) {
                        exception.printStackTrace();
                    }
                }
            } else if (method == Constants.POST_OPERATION) {
                // 2. POST
                // a) build the URL into a HttpPost object
                // b) create a list of NameValuePair objects containing the attributes and their values (operators, operation)
                // c) create an instance of a UrlEncodedFormEntity object using the list and UTF-8 encoding and attach it to the post request
                // d) create an instance of a ResultHandler object
                // e) execute the request, thus generating the result
                HttpPost httpPost = new HttpPost(Constants.POST_WEB_SERVICE_ADDRESS);
                List<NameValuePair> param = new ArrayList<NameValuePair>();
                param.add(new BasicNameValuePair(Constants.OPERATION_ATTRIBUTE, operation));
                param.add(new BasicNameValuePair(Constants.OPERATOR1_ATTRIBUTE, operator1));
                param.add(new BasicNameValuePair(Constants.OPERATOR2_ATTRIBUTE, operator2));
                try {
                    UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(param, HTTP.UTF_8);
                    httpPost.setEntity(urlEncodedFormEntity);
                } catch (UnsupportedEncodingException unsupportedEncodingException) {
                    Log.e(Constants.TAG, unsupportedEncodingException.getMessage());
                    if (Constants.DEBUG) {
                        unsupportedEncodingException.printStackTrace();
                    }
                }

                try {
                    httpPostResponse = httpClient.execute(httpPost);
                    HttpEntity httpPostEntity = httpPostResponse.getEntity();
                    if (httpPostEntity != null) {
                        // do something with the response
                        String s = EntityUtils.toString(httpPostEntity);
                        Log.i(Constants.TAG, s);
                        return s;
                    }
                } catch (Exception exception) {
                    Log.e(Constants.TAG, exception.getMessage());
                    if (Constants.DEBUG) {
                        exception.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            Log.e(Constants.TAG, "Http error " + e.getMessage());
            return null;
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        // display the result in resultTextView
        resultTextView.setText(result);
    }

}
