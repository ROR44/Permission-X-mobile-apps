package com.permissionx.guolindev

/**
 * Provide specific scopes for ExplainReasonCallback and ForwardToSettingsCallback to give them specific functions to call.
 * @author guolin
 * @since 2020/3/18
 */
class ExplainReasonScope(private val permissionBuilder: PermissionBuilder) {

    fun showRequestReasonDialog(permissions: List<String>, message: String, positiveText: String, negativeText: String? = null) {
        permissionBuilder.showHandlePermissionDialog(true, permissions, message, positiveText, negativeText)
    }

}

class ForwardToSettingsScope(private val permissionBuilder: PermissionBuilder) {

    fun showForwardToSettingsDialog(permissions: List<String>, message: String, positiveText: String, negativeText: String? = null) {
        permissionBuilder.showHandlePermissionDialog(false, permissions, message, positiveText, negativeText)
    }

}