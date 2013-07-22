package idv.weikun.main.pattern.service;

public class Example {
	
	AService a = new AService();
    BService b = new BService();
    CService c = new CService();
	
	void main(String[] args) {
        //ServiceBase.getService(AService.class).service_A_only_method();
        //ServiceBase.getService(BService.class).service_B_only_method();
        //大家可以試試看 在下面打入
        //ServiceBase.getService(CService.class).
        //eclipse的自動完成會幫你完成什麼？
    
        IService f = ServiceBase.getService(AService.class); //OK!
        AService as = ServiceBase.getService(AService.class); //OK! 不用幫他轉型!
        //BService bs = ServiceBase.getService(AService.class); //Compile time error! 幫你檢查好型別了!

    }
}
