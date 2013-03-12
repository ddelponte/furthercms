<%@ page contentType="text/html;charset=UTF-8" %>

<!doctype html>

<html>

    <head>

        <theme:layout name="dataentry"/>

        <theme:title text="dataentry.page.title"></theme:title>

        <r:require modules="jquery, jquery-ui, modules, hotKeys"/>

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

            <ui:displayMessage/>

            <ui:h1 text="dataentry.page.body.heading"/>

            <section id="category_nav">

                <nav>

                    <fc:navTree category="${com.merrycoders.furthercms.Category.findByUrlKey('')}" selectedNodeId="${categoryInstance?.id}"/>

                </nav>

            </section>

            <section id="modules-edit">

                <ui:button class="save" kind="anchor" mode="primary" text="plugin.furthercms.update"/>

                <ui:button kind="anchor" mode="danger" text="plugin.furthercms.delete"/>

                <div id="button-status"><g:message code="plugin.furthercms.saving" default="Saving..."/></div>

                <fc:categoryEditor category="${categoryInstance}" page="${pageInstance}"/>

                <g:each in="${modules}" var="module">

                    <fc:renderModuleEdit module="${module}"/>

                </g:each>

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