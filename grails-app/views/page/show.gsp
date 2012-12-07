<%@ page import="com.merrycoders.furthercms.Page" %>
<!doctype html>
<html>
    <head>
        <meta name="layout" content="main">
        <g:set var="entityName" value="${message(code: 'page.label', default: 'Page')}"/>
        <title><g:message code="default.show.label" args="[entityName]"/></title>
    </head>

    <body>
        <a href="#show-page" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>

        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]"/></g:link></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]"/></g:link></li>
            </ul>
        </div>

        <div id="show-page" class="content scaffold-show" role="main">
            <h1><g:message code="default.show.label" args="[entityName]"/></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <ol class="property-list page">

                <g:if test="${pageInstance?.metaDescription}">
                    <li class="fieldcontain">
                        <span id="metaDescription-label" class="property-label"><g:message code="page.metaDescription.label" default="Meta Description"/></span>

                        <span class="property-value" aria-labelledby="metaDescription-label"><g:fieldValue bean="${pageInstance}"
                                                                                                           field="metaDescription"/></span>

                    </li>
                </g:if>

                <g:if test="${pageInstance?.metaKeywords}">
                    <li class="fieldcontain">
                        <span id="metaKeywords-label" class="property-label"><g:message code="page.metaKeywords.label" default="Meta Keywords"/></span>

                        <span class="property-value" aria-labelledby="metaKeywords-label"><g:fieldValue bean="${pageInstance}" field="metaKeywords"/></span>

                    </li>
                </g:if>

                <g:if test="${pageInstance?.linkText}">
                    <li class="fieldcontain">
                        <span id="linkText-label" class="property-label"><g:message code="page.linkText.label" default="Link Text"/></span>

                        <span class="property-value" aria-labelledby="linkText-label"><g:fieldValue bean="${pageInstance}" field="linkText"/></span>

                    </li>
                </g:if>

                <g:if test="${pageInstance?.dateCreated}">
                    <li class="fieldcontain">
                        <span id="dateCreated-label" class="property-label"><g:message code="page.dateCreated.label" default="Date Created"/></span>

                        <span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${pageInstance?.dateCreated}"/></span>

                    </li>
                </g:if>

                <g:if test="${pageInstance?.isHidden}">
                    <li class="fieldcontain">
                        <span id="isHidden-label" class="property-label"><g:message code="page.isHidden.label" default="Is Hidden"/></span>

                        <span class="property-value" aria-labelledby="isHidden-label"><g:formatBoolean boolean="${pageInstance?.isHidden}"/></span>

                    </li>
                </g:if>

                <g:if test="${pageInstance?.isInMenu}">
                    <li class="fieldcontain">
                        <span id="isInMenu-label" class="property-label"><g:message code="page.isInMenu.label" default="Is In Menu"/></span>

                        <span class="property-value" aria-labelledby="isInMenu-label"><g:formatBoolean boolean="${pageInstance?.isInMenu}"/></span>

                    </li>
                </g:if>

                <g:if test="${pageInstance?.isPublished}">
                    <li class="fieldcontain">
                        <span id="isPublished-label" class="property-label"><g:message code="page.isPublished.label" default="Is Published"/></span>

                        <span class="property-value" aria-labelledby="isPublished-label"><g:formatBoolean boolean="${pageInstance?.isPublished}"/></span>

                    </li>
                </g:if>

                <g:if test="${pageInstance?.lastUpdated}">
                    <li class="fieldcontain">
                        <span id="lastUpdated-label" class="property-label"><g:message code="page.lastUpdated.label" default="Last Updated"/></span>

                        <span class="property-value" aria-labelledby="lastUpdated-label"><g:formatDate date="${pageInstance?.lastUpdated}"/></span>

                    </li>
                </g:if>

                <g:if test="${pageInstance?.pageType}">
                    <li class="fieldcontain">
                        <span id="pageType-label" class="property-label"><g:message code="page.pageType.label" default="Page Type"/></span>

                        <span class="property-value" aria-labelledby="pageType-label"><g:link controller="pageType2" action="show"
                                                                                              id="${pageInstance?.pageType?.id}">${pageInstance?.pageType?.encodeAsHTML()}</g:link></span>

                    </li>
                </g:if>

                <g:if test="${pageInstance?.title}">
                    <li class="fieldcontain">
                        <span id="title-label" class="property-label"><g:message code="page.title.label" default="Title"/></span>

                        <span class="property-value" aria-labelledby="title-label"><g:fieldValue bean="${pageInstance}" field="title"/></span>

                    </li>
                </g:if>

            </ol>
            <g:form>
                <fieldset class="buttons">
                    <g:hiddenField name="id" value="${pageInstance?.id}"/>
                    <g:link class="edit" action="edit" id="${pageInstance?.id}"><g:message code="default.button.edit.label" default="Edit"/></g:link>
                    <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                    onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
                </fieldset>
            </g:form>
        </div>
    </body>
</html>
