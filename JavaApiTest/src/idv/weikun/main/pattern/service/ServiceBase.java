package idv.weikun.main.pattern.service;

import java.util.HashMap;
import java.util.Map;

public class ServiceBase implements IService {

	public void this_interface_is_not_really_important() {
        System.out.println("Not important at all!");

    }
    
    protected ServiceBase() {
        registerSelf();
    }
    
    private void registerSelf() {
        ServiceBase.register(this);
    }
    
    //Static method and fields
    
    private static Map<Class<? extends IService>, IService> serviceMap = new HashMap<Class<? extends IService>, IService>();
    
    static private void register(IService service) {
        serviceMap.put( (Class<? extends IService>) service.getClass(), service);
    }
    
    @SuppressWarnings("unchecked")
    static public <T extends IService> T getService(Class<T> clazz) {
        return (T)serviceMap.get(clazz);
    }

}
