<theme:zone name="secondary-navigation">
    <nav:menu custom="true" scope="plugin.furthercms.admin" class="nav nav-pills">
        <g:each in="${itemList}" var="item">
            <li ${(item == activeItem) ? 'class=\"active\"' : ''}>
                <p:callTag tag="g:link" attrs="${[url: "/${appContext}/${item?.urlKey ?: ''}"]}">
                    <nav:title item="${[titleMessageCode: item?.code, titleDefault: item?.toString()]}"/>
                </p:callTag>
            </li>
        </g:each>
    </nav:menu>
</theme:zone>