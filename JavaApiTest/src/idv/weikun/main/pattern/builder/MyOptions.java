package idv.weikun.main.pattern.builder;

public class MyOptions {

	private final String userName;
	private final boolean isSingle;
	private final int age;
	
	private MyOptions(Builder builder) {
		this.userName = builder.userName;
		this.isSingle = builder.isSingle;
		this.age = builder.age;
	}
	
	public String getUserName(){
		return userName;
	}
	
	public boolean isSingle(){
		return isSingle;
	}
	
	public int getAge(){
		return age;
	}
	
	/**
	 * Builder for {@link MyOptions}
	 *
	 */
	public static class Builder{
		
		private String userName;
		private boolean isSingle = false;
		private int age;
		
		public Builder() {
			
		}
		
		public Builder setUserName(String userName){
			this.userName = userName;
			return this;
		}
		
		public Builder single(){
			this.isSingle = true;
			return this;
		}
		
		public void setAge(int age){
			this.age = age;
		}
		
		public MyOptions build(){
			return new MyOptions(this);
		}
	}
	
	public MyOptions createSimple(){
		return new Builder().build();
	}
	
}
