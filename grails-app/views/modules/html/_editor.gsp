<label><g:message code="plugin.furthercms.module.html.label"/></label>

<%
    Random rand = new Random()
    Integer max = Integer.MAX_VALUE - 1
    def randomInteger = rand.nextInt(max + 1)
%>

<g:render template="/modules/html/ckeditor" model="[name: name + '_' + randomInteger, height: height, width: width, data: data]"/>