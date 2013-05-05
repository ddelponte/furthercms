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

            <g:render template="${contentTemplatePath}"/>

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