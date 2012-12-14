<theme:zone name="user-navigation">
    <nav:secondary custom="true" scope="plugin.furthercms.app">
        <li class="active">
            <p:callTag tag="g:link" attrs="${[url: "urlKey"]}">
                <nav:title item="${[titleMessageCode: "code", titleDefault: 'blah']}"/>
            </p:callTag>
        </li>
    </nav:secondary>
</theme:zone>