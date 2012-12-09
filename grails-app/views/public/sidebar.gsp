<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html>
    <head>
        <theme:layout name="sidebar"/>
        <theme:title text="sidebar.page.title"></theme:title>
    </head>

    <body>

        <theme:zone name="navigation">
            <ui:h1 text="sidebar.page.navigation.heading"/>
            <ul>
                <li>Home</li>
                <li>Stuff</li>
            </ul>
        </theme:zone>

        <theme:zone name="secondary-navigation">
            <ui:h1 text="sidebar.page.secondary-navigation.heading"/>
            <ul>
                <li>Secondary Home</li>
                <li>Secondary Stuff</li>
            </ul>
        </theme:zone>

        <theme:zone name="body">
            <ui:h1 text="sidebar.page.body.heading"/>
            <p><p:dummyText/></p>
        </theme:zone>

        <theme:zone name="sidebar">
            <ui:h1 text="sidebar.page.sidebar.heading"/>
            <p><p:dummyText/></p>
        </theme:zone>

        <theme:zone name="user-navigation">
            <ui:h1 text="sidebar.page.user-navigation.heading"/>
            <p><p:dummyText/></p>
        </theme:zone>

        <theme:zone name="footer">
            <ui:h1 text="sidebar.page.footer.heading"/>
            <p><p:dummyText/></p>
        </theme:zone>
    </body>
</html>