<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html>
    <head>
        <theme:layout name="sidebar"/>
        <theme:title text="sidebar.page.title"></theme:title>
    </head>

    <body>

        <theme:zone name="navigation">
            <fc:primaryNavAdmin activePrimaryAdminMenuItem="${activePrimaryAdminMenuItem}"/>
        </theme:zone>

        <theme:zone name="secondary-navigation">
            <fc:secondaryNavAdmin activePrimaryAdminMenuItem="${activePrimaryAdminMenuItem}" activeSecondaryAdminMenuItem="${activeSecondaryAdminMenuItem}"/>
        </theme:zone>

    %{--Render main content area--}%
        <g:render template="${contentTemplate}" model="${model}"/>

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