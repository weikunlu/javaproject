package idv.weikun.main.designpattern.state;

public class Main {

	FacebookController facebookController;
	
	public Main(){
		facebookController = new FacebookController();
		
		facebookController.state = facebookController.new None(this);
	}

	/****/
	String url = "";
	public void pageStart(String url){
		
		this.url=url;
		
		//if need?
		facebookController.reset();
	}
	
	public void pageFinish(){
		
		facebookController.checkStatus(url);
		
		facebookController.action();
	}
	
	public void pageError(){
		facebookController.error();
	}
	/****/
	
	public static void main(String[] args) {
		Main m = new Main();
		System.out.println("----");
		m.pageStart("0");
		m.pageFinish();
		
		m.pageStart("1");
		m.pageFinish();
		
		m.pageStart("2");
		m.pageFinish();
		
		System.out.println("---finish");
		
		Main m2 = new Main();
		System.out.println("---");
		m2.pageStart("0");
		m2.pageFinish();
		
		System.out.println("---finish");
	}
	
}
