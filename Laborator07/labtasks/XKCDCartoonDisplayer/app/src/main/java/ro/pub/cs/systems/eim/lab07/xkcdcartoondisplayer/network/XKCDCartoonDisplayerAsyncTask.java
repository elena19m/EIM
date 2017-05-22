package ro.pub.cs.systems.eim.lab07.xkcdcartoondisplayer.network;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.ResponseHandler;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.BasicResponseHandler;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import ro.pub.cs.systems.eim.lab07.xkcdcartoondisplayer.R;
import ro.pub.cs.systems.eim.lab07.xkcdcartoondisplayer.entities.XKCDCartoonInformation;
import ro.pub.cs.systems.eim.lab07.xkcdcartoondisplayer.general.Constants;

public class XKCDCartoonDisplayerAsyncTask extends AsyncTask<String, Void, XKCDCartoonInformation> {

    private TextView xkcdCartoonTitleTextView;
    private ImageView xkcdCartoonImageView;
    private TextView xkcdCartoonUrlTextView;
    private Button previousButton, nextButton;

    private class XKCDCartoonButtonClickListener implements Button.OnClickListener {

        private String xkcdComicUrl;

        public XKCDCartoonButtonClickListener(String xkcdComicUrl) {
            this.xkcdComicUrl = xkcdComicUrl;
        }

        @Override
        public void onClick(View view) {
            new XKCDCartoonDisplayerAsyncTask(xkcdCartoonTitleTextView, xkcdCartoonImageView, xkcdCartoonUrlTextView, previousButton, nextButton).execute(xkcdComicUrl);
        }

    }

    public XKCDCartoonDisplayerAsyncTask(TextView xkcdCartoonTitleTextView, ImageView xkcdCartoonImageView, TextView xkcdCartoonUrlTextView, Button previousButton, Button nextButton) {
        this.xkcdCartoonTitleTextView = xkcdCartoonTitleTextView;
        this.xkcdCartoonImageView = xkcdCartoonImageView;
        this.xkcdCartoonUrlTextView = xkcdCartoonUrlTextView;
        this.previousButton = previousButton;
        this.nextButton = nextButton;
    }

    @Override
    public XKCDCartoonInformation doInBackground(String... urls) {
        XKCDCartoonInformation xkcdCartoonInformation = new XKCDCartoonInformation();

        // TODO exercise 5a)
        // 1. obtain the content of the web page (whose Internet address is stored in urls[0])
        // - create an instance of a HttpClient object
        // - create an instance of a HttpGet object
        // - create an instance of a ResponseHandler object
        // - execute the request, thus obtaining the web page source code

        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(urls[0]);
        String content = "";
        try {
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            content = httpClient.execute(httpGet, responseHandler);
        } catch (Exception e) {}

        Document document = Jsoup.parse(content);
        Element htmlTag = document.child(0);
        // 2. parse the web page source code
        // - cartoon title: get the tag whose id equals "ctitle"
        Element ctitle = htmlTag.getElementsByAttributeValue(Constants.ID_ATTRIBUTE,
                Constants.CTITLE_VALUE).first();
        xkcdCartoonInformation.setCartoonTitle(ctitle.ownText());
        // - cartoon url
        //   * get the first tag whose id equals "comic"
        //   * get the embedded <img> tag
        //   * get the value of the attribute "src"
        //   * prepend the protocol: "http:"
        Element comicElement = htmlTag.getElementsByAttributeValue(Constants.ID_ATTRIBUTE,
                Constants.COMIC_VALUE).first();
        String cartoonUrl = comicElement.getElementsByTag(Constants.IMG_TAG).attr(Constants.SRC_ATTRIBUTE);
        xkcdCartoonInformation.setCartoonUrl("http:" + cartoonUrl);
        // - cartoon bitmap (only if using Apache HTTP Components)
        //   * create the HttpGet object
        //   * execute the request and obtain the HttpResponse object
        //   * get the HttpEntity object from the response
        //   * get the bitmap from the HttpEntity stream (obtained by getContent()) using Bitmap.decodeStream() method

        // - previous cartoon address
        //   * get the first tag whole rel attribute equals "prev"
        //   * get the href attribute of the tag
        //   * prepend the value with the base url: http://www.xkcd.com
        //   * attach the previous button a click listener with the address attached
        Element prevElement = htmlTag.getElementsByAttributeValue(Constants.REL_ATTRIBUTE,
                Constants.PREVIOUS_VALUE).first();
        String prevAddr = Constants.XKCD_INTERNET_ADDRESS + prevElement.attr(Constants.HREF_ATTRIBUTE);
        xkcdCartoonInformation.setPreviousCartoonUrl(prevAddr);

        // - next cartoon address
        //   * get the first tag whole rel attribute equals "next"
        //   * get the href attribute of the tag
        //   * prepend the value with the base url: http://www.xkcd.com
        //   * attach the next button a click listener with the address attached
        Element nextElement = htmlTag.getElementsByAttributeValue(Constants.REL_ATTRIBUTE,
                Constants.NEXT_VALUE).first();
        String nextAddr = Constants.XKCD_INTERNET_ADDRESS + nextElement.attr(Constants.HREF_ATTRIBUTE);
        xkcdCartoonInformation.setNextCartoonUrl(nextAddr);

        return  xkcdCartoonInformation;
    }

    @Override
    protected void onPostExecute(final XKCDCartoonInformation xkcdCartoonInformation) {

        // TODO exercise 5b)
        // map each member of xkcdCartoonInformation object to the corresponding widget
        // cartoonTitle -> xkcdCartoonTitleTextView
        // cartoonBitmap -> xkcdCartoonImageView (only if using Apache HTTP Components)
        // cartoonUrl -> xkcdCartoonUrlTextView
        // based on cartoonUrl fetch the bitmap using Volley (using an ImageRequest object added to the queue)
        // and put it into xkcdCartoonImageView
        // previousCartoonUrl, nextCartoonUrl -> set the XKCDCartoonUrlButtonClickListener for previousButton, nextButton
        xkcdCartoonTitleTextView.setText(xkcdCartoonInformation.getCartoonTitle());
        ImageRequest cartoonRequest = new ImageRequest(
                xkcdCartoonInformation.getCartoonUrl(),
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        xkcdCartoonImageView.setImageBitmap(bitmap);
                    }
                },
                0,
                0,
                null,
                Bitmap.Config.RGB_565,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d(Constants.TAG, volleyError.toString());
                        if (Constants.DEBUG) {
                            Toast.makeText(xkcdCartoonTitleTextView.getContext(),
                                    xkcdCartoonTitleTextView.getResources().getString(R.string.an_error_has_occurred),
                                    Toast.LENGTH_LONG)
                                        .show();
                        }
                    }
                }
        );
        VolleyController.getInstance(xkcdCartoonImageView.getContext()).addToRequestQueue(cartoonRequest);
        xkcdCartoonUrlTextView.setText(xkcdCartoonInformation.getCartoonUrl());
        previousButton.setOnClickListener(new XKCDCartoonButtonClickListener(xkcdCartoonInformation.getPreviousCartoonUrl()));
        nextButton.setOnClickListener(new XKCDCartoonButtonClickListener(xkcdCartoonInformation.getNextCartoonUrl()));
    }

}
