package com.permissionx.guolindev.request;

import com.permissionx.guolindev.PermissionX;

import java.util.ArrayList;
import java.util.List;

import static com.permissionx.guolindev.request.RequestBackgroundLocationPermission.ACCESS_BACKGROUND_LOCATION;

/**
 * Define a BaseTask to implement the duplicate logic codes. No need to implement them in every task.
 *
 * @author guolin
 * @since 2020/6/10
 */
abstract class BaseTask implements ChainTask {

    /**
     * Point to the next task. When this task finish will run next task. If there's no next task, the request process end.
     */
    protected ChainTask next;

    /**
     * Instance of PermissionBuilder.
     */
    protected PermissionBuilder pb;

    /**
     * Provide specific scopes for explainReasonCallback for specific functions to call.
     */
    ExplainScope explainReasonScope;

    /**
     * Provide specific scopes for forwardToSettingsCallback for specific functions to call.
     */
    ForwardScope forwardToSettingsScope;

    BaseTask(PermissionBuilder permissionBuilder) {
        pb = permissionBuilder;
        explainReasonScope = new ExplainScope(pb, this);
        forwardToSettingsScope = new ForwardScope(pb, this);
    }

    @Override
    public ExplainScope getExplainScope() {
        return explainReasonScope;
    }

    @Override
    public ForwardScope getForwardScope() {
        return forwardToSettingsScope;
    }

    @Override
    public void finish() {
        if (next != null) { // If there's next task, then run it.
            next.request();
        } else { // If there's no next task, finish the request process and notify the result
            List<String> deniedList = new ArrayList<>();
            deniedList.addAll(pb.deniedPermissions);
            deniedList.addAll(pb.permanentDeniedPermissions);
            deniedList.addAll(pb.permissionsWontRequest);
            if (pb.requireBackgroundLocationPermission) {
                if (PermissionX.isGranted(pb.activity, ACCESS_BACKGROUND_LOCATION)) {
                    pb.grantedPermissions.add(ACCESS_BACKGROUND_LOCATION);
                } else {
                    deniedList.add(ACCESS_BACKGROUND_LOCATION);
                }
            }
            if (pb.requestCallback != null) {
                pb.requestCallback.onResult(deniedList.isEmpty(), new ArrayList<>(pb.grantedPermissions), deniedList);
            }
        }
    }

}
