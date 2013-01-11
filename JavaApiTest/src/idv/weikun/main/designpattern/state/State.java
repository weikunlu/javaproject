package idv.weikun.main.designpattern.state;

public interface State {
	
	public void start();
	
	public void process();
	
	public void change();
	
	public void error();
}
