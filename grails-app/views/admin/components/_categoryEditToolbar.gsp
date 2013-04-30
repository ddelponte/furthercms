<fc:reorderModulesButton/>

<ui:button class="save" kind="anchor" mode="primary" text="plugin.furthercms.update"/>

<ui:button class="delete" kind="anchor" mode="danger"
           data-url="${createLink([controller: 'category', action: 'delete', params: [id: categoryInstance?.id]])}"
           text="plugin.furthercms.delete"/>

<fc:newCategoryButton id="${categoryInstance?.parent?.id}" pageType="${pageType}"/>