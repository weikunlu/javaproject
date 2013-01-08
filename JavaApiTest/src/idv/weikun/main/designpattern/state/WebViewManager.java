package idv.weikun.main.designpattern.state;

public abstract class WebViewManager implements State {

	protected static WebView WEB_VIEW;

	public WebViewManager() {
		if (WEB_VIEW == null) {
			WEB_VIEW = new WebView();
			
			//balabala here
			
			System.out.println("init webview");
		}
	}
	
	protected void setClient() {
		System.out.println("set client success");
	}

	public abstract void change();

	protected void login() {
		System.out.println("start login");
	}

	protected void privacy() {
		System.out.println("go privacy page");
	}

	protected void scan() {
		System.out.println("trigger scan");
	}
}
