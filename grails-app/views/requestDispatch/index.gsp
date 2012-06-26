<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title></title>
    </head>

    <body>
        <div>
            hi
            <sec:ifNotLoggedIn>
            <g:link controller='login' action='auth'>Login</g:link>
            </sec:ifNotLoggedIn>
        </div>
    </body>
</html>