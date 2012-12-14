<theme:zone name="secondary-navigation">
%{--<nav:secondary path="plugin.furthercms/htmlPageType">--}%
    <nav:menu custom="true" scope="plugin.furthercms.app" class="nav nav-pills">
        <li class="active">
            <p:callTag tag="g:link" attrs="${[url: "urlKey"]}">
                <span>
                    <nav:title item="${[titleMessageCode: "code", titleDefault: 'secondary']}"/>
                </span>
            </p:callTag>
        </li>
    </nav:menu>
%{--</nav:secondary>--}%
</theme:zone>