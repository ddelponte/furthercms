<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html>
    <head>

        <theme:layout name="sidebar"/>

        <theme:title text="sidebar.page.title"></theme:title>

    </head>

    <body>

        <fc:primaryNav categoryInstance="${categoryInstance}"/>

        <fc:secondaryNav categoryInstance="${categoryInstance}"/>

        <theme:zone name="body">

            <ui:displayMessage/>

            <ui:h1 text="sidebar.page.body.heading"/>

            <g:each in="${modules?.moduleData?.dataValue}" var="dataValue">

                <g:each in="${modules}" var="module">

                    <fc:renderModule module="${module}"/>

                </g:each>

            </g:each>

        </theme:zone>

        <theme:zone name="sidebar">

            <ui:block title="Your profile">

                <ui:avatar user="marc@anyware.co.uk" size="50"/>

                <p>Not everybody is this ugly</p>

            </ui:block>

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