package idv.weikun.main.designpattern.state;

public class FacebookController {

	enum FacebookState{
		NONE, LOGIN, PRIVACY, SCAN, LOGOUT, LOGGED, POSTED, UNKNOWN
	}
	
	FacebookState mState = FacebookState.NONE;
	
	State state;
	
	public void checkStatus(String url) {
		if("0".equals(url)){
			mState = FacebookState.LOGIN;
			state = new Login();
		}else if("1".equals(url)){
			mState = FacebookState.PRIVACY;
			state = new Privacy();
		}else if("2".equals(url)){
			mState = FacebookState.SCAN;
			state = new Scan();
		}else if("3".equals(url)){
			mState = FacebookState.LOGGED;
		}else if("4".equals(url)){
			mState = FacebookState.POSTED;
		}else if("5".equals(url)){
			mState = FacebookState.LOGOUT;
		}else{
			mState = FacebookState.UNKNOWN;
		}
		
	}

	public void action() {
		state.change();
	}

	public void error() {
		// TODO Auto-generated method stub
		
	}

	public void reset() {
		// TODO Auto-generated method stub
		
	}

	class None extends WebViewManager{

		public None(Main main) {
			setClient();
		}
		
		@Override
		public void change() {
			//do nothing
		}
	}
	
	class Login extends WebViewManager{

		@Override
		public void change() {
			login();
		}
	}
	
	class Privacy extends WebViewManager{

		@Override
		public void change() {
			privacy();
		}
	}
	
	class Scan extends WebViewManager{

		@Override
		public void change() {
			scan();
		}
	}
	
}
