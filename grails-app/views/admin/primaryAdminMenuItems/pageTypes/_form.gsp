<%@ page import="com.merrycoders.furthercms.PageType" %>

<ui:field name="name" type="text" bean="${pageTypeInstance}" label="${g.message([code: 'pageType.name.label'])}"/>

<ui:field name="pageTypeKey" type="text" bean="${pageTypeInstance}" label="${g.message([code: 'pageType.pageTypeKey.label'])}"/>

<ui:field name="description" type="textarea" bean="${pageTypeInstance}" label="${g.message([code: 'pageType.description.label'])}"/>

<fc:moduleTypeStatusLists moduleTypeStatusMap="${moduleTypeStatusMap}"/>
