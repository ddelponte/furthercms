<section id="available-module-types">

    <ui:h1><g:message code="com.merrycoders.furthercms.modules" default="Modules"/></ui:h1>

    <g:each in="${moduleTypes}" var="moduleType">

        <ui:button
                kind="anchor"
                href="#"
                mode="secondary"
                data-module-type-id="${moduleType?.id}">

            <g:message code="${moduleType?.code}" default="${moduleType?.name}"/> +</ui:button>

    </g:each>

</section>

<script type="text/javascript" src="${r.resource([dir: 'js/admin', file: 'moduleTypes.js'], '')}"></script>