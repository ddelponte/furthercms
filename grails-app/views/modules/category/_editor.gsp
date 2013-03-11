<r:require modules="categoryEditor"/>

<ui:form controller="category" action="update">

    <g:hiddenField name="category.id" value="${category?.id}"/>

    <g:hiddenField name="category.version" value="${category?.version}"/>

    <g:hiddenField name="page.id" value="${page?.id}"/>

    <g:hiddenField name="page.version" value="${page?.version}"/>

    <ui:field name="page.title" type="text" label="category.page.title.label" value="${page?.title}"/>

    <div class="plugin.furthercms.category.urlkey.label">${category?.parent?.urlKey}/<span>${category?.pageTitleToSlug()}</span></div>

</ui:form>