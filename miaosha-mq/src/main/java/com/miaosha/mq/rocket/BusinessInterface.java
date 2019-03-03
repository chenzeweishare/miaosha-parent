package com.miaosha.mq.rocket;

public interface BusinessInterface<V> {
	
	
	   /**
     * 业务处理
     * @param args
     * @return
     */
    void doBusiness(V args);

}


