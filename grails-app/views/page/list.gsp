<%@ page import="com.merrycoders.furthercms.Page" %>
<!doctype html>
<html>
    <head>
        <meta name="layout" content="main">
        <g:set var="entityName" value="${message(code: 'page.label', default: 'Page')}"/>
        <title><g:message code="default.list.label" args="[entityName]"/></title>
    </head>

    <body>
        <a href="#list-page" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>

        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]"/></g:link></li>
            </ul>
        </div>

        <div id="list-page" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]"/></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <table>
                <thead>
                    <tr>

                        <g:sortableColumn property="metaDescription" title="${message(code: 'page.metaDescription.label', default: 'Meta Description')}"/>

                        <g:sortableColumn property="metaKeywords" title="${message(code: 'page.metaKeywords.label', default: 'Meta Keywords')}"/>

                        <g:sortableColumn property="linkText" title="${message(code: 'page.linkText.label', default: 'Link Text')}"/>

                        <g:sortableColumn property="dateCreated" title="${message(code: 'page.dateCreated.label', default: 'Date Created')}"/>

                        <g:sortableColumn property="isHidden" title="${message(code: 'page.isHidden.label', default: 'Is Hidden')}"/>

                        <g:sortableColumn property="isInMenu" title="${message(code: 'page.isInMenu.label', default: 'Is In Menu')}"/>

                    </tr>
                </thead>
                <tbody>
                    <g:each in="${pageInstanceList}" status="i" var="pageInstance">
                        <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

                            <td><g:link action="show" id="${pageInstance.id}">${fieldValue(bean: pageInstance, field: "metaDescription")}</g:link></td>

                            <td>${fieldValue(bean: pageInstance, field: "metaKeywords")}</td>

                            <td>${fieldValue(bean: pageInstance, field: "linkText")}</td>

                            <td><g:formatDate date="${pageInstance.dateCreated}"/></td>

                            <td><g:formatBoolean boolean="${pageInstance.isHidden}"/></td>

                            <td><g:formatBoolean boolean="${pageInstance.isInMenu}"/></td>

                        </tr>
                    </g:each>
                </tbody>
            </table>

            <div class="pagination">
                <g:paginate total="${pageInstanceTotal}"/>
            </div>
        </div>
    </body>
</html>
