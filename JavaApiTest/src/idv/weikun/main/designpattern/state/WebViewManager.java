package idv.weikun.main.designpattern.state;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.trendmicro.socialprivacyscanner.JScriptNotifyHandler;
import com.trendmicro.socialprivacyscanner.facebook.State;
import com.trendmicro.socialprivacyscanner.facebook.WebViewManager;
import com.trendmicro.socialprivacyscanner.facebook.WebViewManager.TMWebViewClient;
import com.trendmicro.socialprivacyscanner.update.UpdateManager;

public abstract class WebViewManager implements State{

	public static final String TAG = WebViewManager.class.getSimpleName();
	
	protected static WebView WEB_VIEW;
	
	protected static String jsLib = "";
	
	private Handler mHandler;
	
	public WebViewManager() {
	}
	
	public WebViewManager(Context context) {
		if(WEB_VIEW==null){
			initJSLib();
			
			WEB_VIEW = new WebView(context);
			
			WebSettings webSettings = WEB_VIEW.getSettings();
			
			webSettings.setBuiltInZoomControls(true);
			
			// enable javascript
			webSettings.setJavaScriptEnabled(true);
			
			// emulate IE
			//webSettings.setUserAgentString("Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0; BOIE9;ENUS)");
			webSettings.setUserAgentString("Mozilla/5.0 (Windows NT 6.2; Win64; x64; rv:16.0.1) Gecko/20121011 Firefox/16.0.1");
			
			webSettings.setAppCacheEnabled(false);
			
			webSettings.setDatabaseEnabled(false);
			
			// disable saving prompt dialog
			webSettings.setSaveFormData(false);
			
			webSettings.setSavePassword(false);
			
			WEB_VIEW.setWebViewClient(new TMWebViewClient());
			
			WEB_VIEW.addJavascriptInterface(new JScriptNotifyHandler(), "external");
		}
	}
	
	public void setWebViewClient(WebViewClient webViewClient){
		WEB_VIEW.setWebViewClient(webViewClient);
	}
	
	public void setHandler(Handler h){
		this.mHandler = h;
		
		JScriptNotifyHandler.setCallbackHandler(mHandler);
	}
	
	private void initJSLib() {
		jsLib = "javascript:" + getScript(
				UpdateManager.getJQueryPath(),
				UpdateManager.getFPSUtilLibPath(),
				UpdateManager.getFPSScanLibPath(),
				UpdateManager.getFPSFixLibPath(),
				UpdateManager.getFPSAJavaScriptPath()
				);
	}
	
	private String getScript(String ... pathes) {
		StringBuilder sb = new StringBuilder();
		try {
			for(String path : pathes){
				File scriptFile = new File(path);
				Log.i(TAG, "[Script] loading:" + scriptFile.getPath());
				FileInputStream fis;
				fis = new FileInputStream(scriptFile);
				@SuppressWarnings("resource")
				BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
				String line = null;
	
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return sb.toString();
	}
	
	public abstract void start();
	
	public abstract void process();
	
	public abstract void change();
	
	public abstract void error();
	
	private class TMWebViewClient extends WebViewClient {
		
    	boolean timeout = true;
    	Runnable run;
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
        	Log.i(TAG, "'"+url+"' loading");
        	
        	timeout = true;
        	if (run != null)
        	{
        		mHandler.removeCallbacks(run);
        	}
        	
        	run = new Runnable() {
                public void run() {

                    if(timeout) {
                    	
                   }
                }
            };
            mHandler.postDelayed(run, 60000);
            
        }

		public void onPageFinished(WebView view, String url) {
			Log.i(TAG, "'"+url + "' loaded");
			
			process();
			
			change();
		}
		
		public void onReceivedError (WebView view, int errorCode, String description, String failingUrl) {
			Log.e(TAG, "Url: " + failingUrl + "  errorCode:" + errorCode + "  " + description);
			
			error();
			
			super.onReceivedError(view, errorCode, description, failingUrl);
		}
	}
	
}
