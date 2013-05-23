<g:each in="${moduleTypeStatusMap}" var="moduleTypeStatus">

    <ui:h1>${moduleTypeStatus?.key}</ui:h1>

    <ul class="sortable connectedSortable">

        <g:each in="${moduleTypeStatus?.value}" var="moduleType">

            <li class="ui-state-default">${moduleType}</li>

        </g:each>

    </ul>

</g:each>