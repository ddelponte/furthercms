<r:require modules="newCategoryButton"/>

<ui:button class="new" kind="anchor" mode="secondary"
           data-url="${createLink([controller: 'category', action: 'createAndSave', id: id, params: [pageTypeKey: pageType?.pageTypeKey]])}"
           data-success-url="${createLink([controller: 'admin', action: 'edit'])}"
           text="plugin.furthercms.new"/>