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

        <theme:zone name="body">
            <ui:displayMessage/>
            <ui:h1 text="sidebar.page.body.heading"/>
            <p>Main Content</p>
        </theme:zone>

        <theme:zone name="sidebar">
            <ui:block title="Your profile">
                <ui:avatar user="marc@anyware.co.uk" size="50"/>
                <p>Not everybody is this ugly</p>
                <fc:navTree category="${com.merrycoders.furthercms.Category.findByUrlKey('')}"/>
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