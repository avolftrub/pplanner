<div class="nav" role="navigation">
    <ul>
        <shiro:user>
            <li class="settings">
                <g:link controller="settings" action="showSettings">
                    <img class="menuIcon" src="${resource(dir: 'images', file: 'settings.png')}" alt="Grails"/>
                    <g:message code="section.settings"/>
                </g:link>
            </li>


            <shiro:hasRole name="${ShiroRole.ROLE_ADMIN}">
                <li><g:link controller="user" action="list">
                    <img class="menuIcon" src="${resource(dir: 'images', file: 'users.png')}" alt="Grails"/>
                    <g:message code="section.users"/>
                </g:link>
                </li>
                <li><g:link controller="dealer" action="list">
                    <img class="menuIcon" src="${resource(dir: 'images', file: 'house.png')}" alt="Grails"/>
                    <g:message code="section.dealers"/>
                </g:link>
                </li>
            </shiro:hasRole>
            <li>
                <g:link controller="project" action="list">
                    <img class="menuIcon" src="${resource(dir: 'images', file: 'projects.png')}" alt="Grails"/>
                    <g:message code="section.projects"/>
                </g:link>
            </li>
        </shiro:user>
    </ul>
</div>
