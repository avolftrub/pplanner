<div class="nav" role="navigation">
    <ul>
        <shiro:user>
            <li class="settings">
                <g:link controller="settings" action="showSettings">
                    <img class="menuIcon" src="${resource(dir: 'images', file: 'settings.png')}" alt="${g.message(code: 'section.settings')}"/>
                    <g:message code="section.settings"/>
                </g:link>
            </li>


            <shiro:hasRole name="${ShiroRole.ROLE_ADMIN}">
                <li><g:link controller="user" action="list">
                    <img class="menuIcon" src="${resource(dir: 'images', file: 'users.png')}" alt="${g.message(code: 'section.users')}"/>
                    <g:message code="section.users"/>
                </g:link>
                </li>
                <li><g:link controller="dealer" action="list">
                    <img class="menuIcon" src="${resource(dir: 'images', file: 'house.png')}" alt="${g.message(code: 'section.dealers')}"/>
                    <g:message code="section.dealers"/>
                </g:link>
                </li>
            </shiro:hasRole>
            <li>
                <g:link controller="project" action="list">
                    <img class="menuIcon" src="${resource(dir: 'images', file: 'projects.png')}" alt="${g.message(code: 'section.projects')}"/>
                    <g:message code="section.projects"/>
                </g:link>
            </li>
            <shiro:hasRole name="${ShiroRole.ROLE_ADMIN}">
                <li><g:link controller="project" action="listArchived">
                    <img class="menuIcon" src="${resource(dir: 'images', file: 'archive.png')}" alt="${g.message(code: 'section.archieved')}"/>
                    <g:message code="section.archieved"/>
            </g:link>

            </shiro:hasRole>
        </shiro:user>
    </ul>
</div>
