<%@ page contentType="text/html;charset=UTF-8" %>

<!doctype html>

<html>

    <head>

        <theme:layout name="dataentry"/>

        <theme:title text="dataentry.page.title"></theme:title>

        <r:require modules="jquery, jquery-ui"/>

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

            <section id="main-editable-content">

                <ui:form controller="category">

                    <g:hiddenField name="category.id" value="${categoryInstance?.id}"/>

                    <g:hiddenField name="category.version" value="${categoryInstance?.version}"/>

                    <g:hiddenField name="page.id" value="${pageInstance?.id}"/>

                    <g:hiddenField name="page.version" value="${pageInstance?.version}"/>

                    <ui:field name="page.title" type="text" label="category.page.title.label" value="${pageInstance?.title}"/>

                    <div class="plugin.furthercms.category.urlkey.label">${categoryInstance?.urlKey}</div>

                </ui:form>

                <g:each in="${modules}" var="module">

                    <fc:renderModuleEdit module="${module}"/>

                </g:each>

                <ui:form name="testform" controller="category" action="test">
                    <ui:field name="test" class="ajaxtest" type="text" label="test" value="test"/>
                </ui:form>

                <ui:form name="testform2" controller="admin" action="test">
                    <ui:field name="test2" class="ajaxtest" type="text" label="test" value="test"/>
                </ui:form>

                <g:javascript>
                    $("form#testform").change(function () {
                        var datastring = "name=lala";
                        // if rows is filled out {
                        alert("test");
                        $.ajax({
                            type: 'post',
                            url: $("#testform").attr('action'),
                            data: datastring
                        })
                    })


                    $("form#testform2").change(function () {
                        // if rows is filled out {
                        alert("test");
                        $.ajax({
                            type: 'post',
                            url: $("#testform2").attr('action'),
                            data: "name=test"
                        })
                    })
                </g:javascript>

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