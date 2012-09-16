<%@ page import="com.merrycoders.furthercms.Page" %>



<div class="fieldcontain ${hasErrors(bean: pageInstance, field: 'metaDescription', 'error')} ">
    <label for="metaDescription">
        <g:message code="page.metaDescription.label" default="Meta Description"/>

    </label>
    <g:textField name="metaDescription" value="${pageInstance?.metaDescription}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pageInstance, field: 'metaKeywords', 'error')} ">
    <label for="metaKeywords">
        <g:message code="page.metaKeywords.label" default="Meta Keywords"/>

    </label>
    <g:textField name="metaKeywords" value="${pageInstance?.metaKeywords}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pageInstance, field: 'isHidden', 'error')} ">
    <label for="isHidden">
        <g:message code="page.isHidden.label" default="Is Hidden"/>

    </label>
    <g:checkBox name="isHidden" value="${pageInstance?.isHidden}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pageInstance, field: 'isInMenu', 'error')} ">
    <label for="isInMenu">
        <g:message code="page.isInMenu.label" default="Is In Menu"/>

    </label>
    <g:checkBox name="isInMenu" value="${pageInstance?.isInMenu}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pageInstance, field: 'isPublished', 'error')} ">
    <label for="isPublished">
        <g:message code="page.isPublished.label" default="Is Published"/>

    </label>
    <g:checkBox name="isPublished" value="${pageInstance?.isPublished}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pageInstance, field: 'linkText', 'error')} ">
    <label for="linkText">
        <g:message code="page.linkText.label" default="Link Text"/>

    </label>
    <g:textField name="linkText" value="${pageInstance?.linkText}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pageInstance, field: 'pageType', 'error')} required">
    <label for="pageType">
        <g:message code="page.pageType.label" default="Page Type"/>
        <span class="required-indicator">*</span>
    </label>
    <g:select name="pageType" from="${com.merrycoders.furthercms.PageType?.values()}" keys="${com.merrycoders.furthercms.PageType.values()*.name()}" required=""
              value="${pageInstance?.pageType?.name()}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pageInstance, field: 'title', 'error')} ">
    <label for="title">
        <g:message code="page.title.label" default="Title"/>

    </label>
    <g:textField name="title" value="${pageInstance?.title}"/>
</div>

