<ui:displayMessage/>

<ui:form name="pageTypeForm">

    <g:hiddenField name="id" value="${pageTypeInstance?.id}"/>
    <g:hiddenField name="version" value="${pageTypeInstance?.version}"/>

    <g:render template="/admin/primaryAdminMenuItems/pageTypes/form"/>

    <ui:button kind="anchor" class="save" mode="primary" text="plugin.furthercms.default.button.update.label"/>
    <ui:button kind="anchor" class="delete" mode="danger" text="plugin.furthercms.default.button.delete.label"/>

</ui:form>

<g:javascript>
    jQuery("form a.save").click(function (event) {
        event.preventDefault();
        var form = jQuery("#pageTypeForm");
        form.attr("action", "${g.createLink(controller: 'pageType', action: 'update')}");
        form.submit();
    });

    jQuery("form a.delete").click(function (event) {
        var form = jQuery("#pageTypeForm");
        event.preventDefault();
        var confirmDelete = confirm("${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}");
        if (confirmDelete) {
            form.attr("action", "${g.createLink(controller: 'pageType', action: 'delete')}");
            form.submit()
        }

    });
</g:javascript>