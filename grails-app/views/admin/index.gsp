<%@ page import="org.apache.commons.lang.WordUtils" contentType="text/html;charset=UTF-8" %>

<!doctype html>

<html>

    <head>

        <theme:layout name="dataentry"/>

        <theme:title text="dataentry.page.title"></theme:title>

        <r:require modules="jquery, jquery-ui, modules, hotKeys, dateFormatter, slidePanel"/>

        <ckeditor:resources/>

    </head>

    <body>

        <theme:zone name="navigation">

            <fc:primaryNavAdmin activePrimaryAdminMenuItem="${activePrimaryAdminMenuItem}"/>

        </theme:zone>

        <theme:zone name="secondary-navigation">

            <fc:secondaryNavAdmin activePrimaryAdminMenuItem="${activePrimaryAdminMenuItem}" activeSecondaryAdminMenuItem="${activeSecondaryAdminMenuItem}"/>

        </theme:zone>

    %{--Render main content area--}%

        <theme:zone name="body">

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

        </theme:zone>

        <theme:zone name="user-navigation">

            <ul class="nav secondary">

                <li><a href="#">Log in</a></li>

                <li><a href="#">Sign up</a></li>

            </ul>

        </theme:zone>

        <theme:zone name="footer">

            <p><p:dummyText/></p>

        </theme:zone>

    </body>

</html>