/**
* @Title: ResEntity.java
* @Package com.brt.vehicleManagement.rest.domain 
* @Description: TODO(用一句话描述该文件做什么) 
* @author wangmeilan   
* @date 2016年7月29日 下午5:20:49 
* @version V1.0   
*/
package com.brt.xwl.rest.viewObject;
/** 
 * @ClassName: ResEntity 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author wangmeilan
 * @date 2016年7月29日 下午5:20:49 
 *  
 */

public class ResEntity {

    private static final String OK = "ok";
    private static final String ERROR = "error";

    private Meta meta;
    private Object data;

    public ResEntity()
    {
    	
    }
    public ResEntity(Object data, Boolean success, String message)
    {
    	if(success)
    	{
    		if(data != null)
    		{
    			this.success(data);
    		}
    		else
    		{
    			this.success();
    		}
    	}
    	else
    	{
    		if(message != null)
			{
    			this.data = data;
    			this.failure(message);
			}
    		else
    		{
    			this.data = data;
    			this.failure();
    		}
    	}
    }
    public ResEntity success() {
        this.meta = new Meta(true, OK);
        return this;
    }

    public ResEntity success(Object data) {
        this.meta = new Meta(true, OK);
        this.data = data;
        return this;
    }

    public ResEntity failure() {
        this.meta = new Meta(false, ERROR);
        return this;
    }

    public ResEntity failure(String message) {
        this.meta = new Meta(false, message);
        return this;
    }
    public ResEntity failure(String message,Object data) {
        this.meta = new Meta(false, message);
        this.data =data;
        return this;
    }

    public Meta getMeta() {
        return meta;
    }

    public Object getData() {
        return data;
    }

    public class Meta {

        private boolean success;
        private String message;

        public Meta() 
        {
        	
        }
        public Meta(boolean success) {
            this.success = success;
        }

        public Meta(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }
    }

	public void setMeta(Meta meta) {
		this.meta = meta;
	}
	public void setData(Object data) {
		this.data = data;
	}
    
    
}
