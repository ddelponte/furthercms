<ui:displayMessage/>

<ui:form name="pageTypeForm">

    <g:hiddenField name="id" value="${pageTypeInstance?.id}"/>
    <g:hiddenField name="version" value="${pageTypeInstance?.version}"/>

    <g:render template="/admin/primaryAdminMenuItems/pageTypes/form"/>

    <ui:button kind="anchor" class="save" mode="primary" text="plugin.furthercms.update"/>
    <ui:button kind="anchor" class="delete" mode="danger" text="plugin.furthercms.delete"/>

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

%{--<input type="submit" name="_action_update" value="Update" class="save">--}%
%{--<input type="submit" name="_action_delete" value="Delete" class="delete" formnovalidate="" onclick="return confirm('Are you sure?');">--}%


%{--<g:form method="post">--}%
%{--<g:hiddenField name="id" value="${pageTypeInstance?.id}"/>--}%
%{--<g:hiddenField name="version" value="${pageTypeInstance?.version}"/>--}%
%{--<fieldset class="form">--}%
%{--<g:render template="form"/>--}%
%{--</fieldset>--}%
%{--<fieldset class="buttons">--}%
%{--<g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}"/>--}%
%{--<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" formnovalidate=""--}%
%{--onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>--}%
%{--</fieldset>--}%
%{--</g:form>--}%