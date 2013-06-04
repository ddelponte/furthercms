<r:require modules="pageTypeForm"/>

<ui:displayMessage/>

<section>

    <ui:form name="pageTypeForm"
             controller="pageType"
             action="updateModuleTypes"
             data-update-action-url="${g.createLink(controller: 'pageType', action: 'update')}"
             data-delete-action-url="${g.createLink(controller: 'pageType', action: 'delete')}"
             data-delete-action-confirm-message="${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}">

        <g:hiddenField name="id" value="${pageTypeInstance?.id}"/>
        <g:hiddenField name="version" value="${pageTypeInstance?.version}"/>

        <g:render template="/admin/primaryAdminMenuItems/pageTypes/form"/>

        <ui:button kind="anchor" class="save" mode="primary" text="plugin.furthercms.default.button.update.label"/>
        <ui:button kind="anchor" class="delete" mode="danger" text="plugin.furthercms.default.button.delete.label"/>

    </ui:form>

</section>