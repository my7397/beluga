package org.opencloudengine.garuda.beluga.action;

import org.opencloudengine.garuda.beluga.action.callback.ActionCallback;
import org.opencloudengine.garuda.beluga.env.Environment;
import org.opencloudengine.garuda.beluga.env.SettingManager;
import org.opencloudengine.garuda.beluga.service.common.ServiceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by swsong on 2015. 8. 4..
 */
public abstract class RunnableAction<RequestType extends ActionRequest> implements Runnable {
    protected Logger logger = LoggerFactory.getLogger(RunnableAction.class);

    protected ActionStatus status;
    protected Environment environment;
    protected SettingManager settingManager;
    protected ServiceManager serviceManager;

    protected RequestType actionRequest;
    private ActionCallback callback;

    public RunnableAction(RequestType actionRequest) {
        this.actionRequest = actionRequest;
        status = new ActionStatus(actionRequest.getActionId(), getClass().getSimpleName());
        settingManager = SettingManager.getInstance();
        environment = settingManager.getEnvironment();
        serviceManager = ServiceManager.getInstance();
    }

    public ActionStatus getStatus() {
        return status;
    }

    public RequestType getActionRequest() {
        return actionRequest;
    }

    @Override
    public void run() {
        try {
            logger.info("### Start Action {}", status.getActionName(), status.getId());
            logger.info("### Status = {}", status);
            status.setStart();
            doAction();
            status.setComplete();
        } catch (Throwable t) {
            status.setError(t);
            logger.warn("### Action Error: {} : {}", status.getActionName(), t.getMessage());
            logger.error("", t);
        } finally {
            logger.info("### Finished Action {}", status.getActionName(), status.getId());
            logger.info("### Status = {}", status);
            if(callback != null) {
                callback.callback(this);
            }
        }
    }

    protected abstract void doAction() throws Exception;

    protected void setResult(Object obj) {
        status.setResult(obj);
    }

    public void setCallback(ActionCallback callback) {
        this.callback = callback;
    }
}
