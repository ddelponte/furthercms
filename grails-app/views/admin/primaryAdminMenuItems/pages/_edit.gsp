<ui:h1 text="dataentry.page.body.heading"/>

<section id="category_nav">

    <ui:button
            kind="button"
            href="${createLink([controller: 'pageType', action: 'listModuleTypes', id: pageInstance?.pageType?.id, params: ['page.id': pageInstance?.id]])}"
            data-slidepanel="panel"
            mode="secondary"
            text="modules"/>

    <nav>

        <fc:navTree category="${com.merrycoders.furthercms.Category.findByUrlKey('')}" selectedNodeId="${categoryInstance?.id}"/>

    </nav>

</section>

<section id="modules-edit">

    <ui:displayMessage/>

    <div>

        <fc:categoryEditToolbar categoryInstance="${categoryInstance}" pageType="${pageInstance?.pageType}"/>

    </div>

    <div id="button-status"
         data-saving-message="${g.message([code: 'plugin.furthercms.saving', default: 'Saving...'], '')}"
         data-saved-message="${g.message([code: 'plugin.furthercms.saved', default: 'Saved'], '')}"
         data-error-saving-message="${g.message([code: 'plugin.furthercms.error.saving', default: 'Saved'], '')}">&nbsp;</div>

    <section class="module">

        <div class="errors" style="display: none;"></div>

        <fc:categoryEditor name="categoryModuleForm" category="${categoryInstance}" page="${pageInstance}"/>

    </section>

    <ul>

        <g:each in="${modules}" var="module">

            <li>

                <section class="module" data-module-name="${module}" data-module-id="${module?.id}">

                    <fc:deleteModuleIcon/>

                    <div class="errors" style="display: none;"></div>

                    <fc:renderModuleEdit module="${module}"/>

                </section>

            </li>

        </g:each>

    </ul>

</section>