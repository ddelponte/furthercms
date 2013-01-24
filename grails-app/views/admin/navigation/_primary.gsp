<theme:zone name="navigation">
    <nav:primary custom="true" scope="plugin.furthercms.admin">
        <g:each in="${itemList}" var="item">
            <li ${(item == activeItem) ? 'class=\"active\"' : ''}>
                <p:callTag tag="g:link" attrs="${[url: "/${appContext}/${item.controller}/${item.action}"]}">
                    <nav:title item="${[titleMessageCode: item.titleMessageCode, titleDefault: item.titleDefault]}"/>
                </p:callTag>
            </li>
        </g:each>
    </nav:primary>
</theme:zone>