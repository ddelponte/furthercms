<ui:form name="form_module_${module?.id}" controller="htmlModule" action="update">

    <g:hiddenField name="id" value="${module?.id}" id="module_${module?.id}"/>

    <g:hiddenField name="version" value="${module?.version}" id="module_${module?.id}_version_${module?.version}"/>

    <fc:htmlEditor
            name="html"
            data="${module?.html}"/>

</ui:form>