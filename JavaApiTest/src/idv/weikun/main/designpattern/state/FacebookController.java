package idv.weikun.main.designpattern.state;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.trendmicro.socialprivacyscanner.R;
import com.trendmicro.socialprivacyscanner.facebook.Login;
import com.trendmicro.socialprivacyscanner.facebook.State;
import com.trendmicro.socialprivacyscanner.facebook.WebViewManager;
import com.trendmicro.socialprivacyscanner.facebook.FacebookScanController.Builder;
import com.trendmicro.socialprivacyscanner.facebook.FacebookScanController.FacebookState;
import com.trendmicro.socialprivacyscanner.facebook.FacebookScanController.PrivacyScan;
import com.trendmicro.socialprivacyscanner.facebook.FacebookScanController.Recommend;

public class FacebookScanController {

	protected static final String FB_URL_LOGIN_HTTPS = "https://www.facebook.com/";
	protected static final String FB_URL_LOGIN = "http://www.facebook.com/";
	protected static final String FB_URL_HOMEPAGE_HTTPS = "https://www.facebook.com/?sk=welcome";
	protected static final String FB_URL_HOMEPAGE = "http://www.facebook.com/?sk=welcome";
	protected static final String FB_URL_PRIVACY_SETTINGS = "https://www.facebook.com/settings/?tab=privacy&privacy_source=settings_menu";
	protected static final String FB_URL_LOGIN_FAILED = "https://www.facebook.com/login.php?login_attempt=1";
	protected static final String FB_URL_MESSAGE = "http://www.facebook.com/?sk=nf";
	protected static final String FB_URL_MESSAGE_RESPONSE = "http://www.facebook.com/ajax/updatestatus.php";
	protected static final String BLANK_URL = "about:blank";
	
	enum FacebookState{
		NONE(new Recommend()), LOGIN(new Recommend()), PRIVACY(new Recommend()), SCAN(new Recommend()),
		LOGOUT(new Recommend()), LOGGED(new Recommend()), POSTED(new Recommend()), UNKNOWN(new Recommend());
		
		private FacebookState(State state){
			
		}
		
	}
	
	private State state;
	
	private Context mContext;
	
	private Map<String, Object> bundle = new HashMap<String, Object>();
	
	public FacebookScanController(Context context) {
		mContext = context;
	}
	
	public void putParameter(String key, Object value){
		bundle.put(key, value);
	}
	
	public void action(String url){
		
		if(FB_URL_LOGIN_HTTPS.equals(url)){
			state = new Login();
		}else if(FB_URL_LOGIN_FAILED.equals(url)){
			
		}else if(FB_URL_HOMEPAGE.equals(url) || FB_URL_HOMEPAGE_HTTPS.equals(url) || FB_URL_LOGIN.equals(url)){
			//state = new Privacy();
		}else if(FB_URL_PRIVACY_SETTINGS.equals(url)){
			state = new PrivacyScan();
		}else if(url.indexOf("stype=lo")!=-1){
			
		}else{
			
		}
		
		
		
		
	}
	
	public Builder getBuilder(){
		return new Builder();
	}
	
	private class Builder {
		
		FacebookState fbState;
		
		public Builder() {
		}
		
		public Builder setState(FacebookState state){
			fbState = state;
			return this;
		}
		
		public Builder putParameter(String key, Object value){
			bundle.put(key, value);
			return this;
		}
		
		public Builder addPageStartedListener(){
			return this;
		}
		
		public Builder addPageFinishedListener(){
			return this;
		}
		
		public Builder addReceivedErrorListener(){
			return this;
		}
		
		public void launch(){
			
			
			
			state.change();
			
			bundle.clear();
		}
	}
	
	class None extends WebViewManager{

		public None() {
			super(mContext);
			
			
		}
		
		@Override
		public void change() {
			//do nothing
		}

		@Override
		public void process() {
			
		}

		@Override
		public void start() {
			
		}

		@Override
		public void error() {
			
		}
	}
	
	class Homepage extends WebViewManager{

		@Override
		public void start() {
			WEB_VIEW.loadUrl(FB_URL_LOGIN_HTTPS);
		}
		
		@Override
		public void process() {
			
		}
		
		@Override
		public void change() {
			WEB_VIEW.loadUrl(jsLib);
			WEB_VIEW.loadUrl(String.format(
					"javascript:LogonFB(\'%s\', \'%s\');",
					bundle.get("account"), bundle.get("password")));
			
			//nextState = new PrivacyScan();
		}

		@Override
		public void error() {
			
		}
	}
	
	class PrivacyScan extends WebViewManager{

		@Override
		public void start() {
			WEB_VIEW.loadUrl(FB_URL_PRIVACY_SETTINGS);
		}
		
		@Override
		public void process() {
			
		}
		
		@Override
		public void change() {
			WEB_VIEW.loadUrl(jsLib);
			WEB_VIEW.loadUrl("javascript:TriggerScan();");
		}

		@Override
		public void error() {
			
		}
	}
	
	class Recommend extends WebViewManager{

		@Override
		public void start() {
			WEB_VIEW.loadUrl(FB_URL_MESSAGE);
		}

		@Override
		public void process() {
			
		}

		@Override
		public void change() {
			WEB_VIEW.loadUrl(jsLib);
			WEB_VIEW.loadUrl("javascript:postonFBWall('"+mContext.getString(R.string.recommendWithFriendMsg)+"');");
		}

		@Override
		public void error() {
			
		}
		
	}
	
	class Loginout extends WebViewManager{

		@Override
		public void start() {
			WEB_VIEW.clearHistory();
			WEB_VIEW.clearCache(true);
			WEB_VIEW.loadUrl(jsLib);
			WEB_VIEW.loadUrl("javascript:logout();");
		}
		
		@Override
		public void process() {
			
		}
		
		@Override
		public void change() {

		}

		@Override
		public void error() {
			
		}
	}
}
