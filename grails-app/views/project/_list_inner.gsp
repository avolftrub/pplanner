<div id="list-projects" class="content scaffold-list" role="main">
    <h1><g:message code="${archived ? 'project.action.list.archive.title' : 'project.action.list.title'}"/></h1>
    <g:set var="sortActionName" value="${archived ? 'listArchived' : 'list'}"/>
    <g:if test="${total > 0}">
        <table class="projectsTable">
            <colgroup>
                <g:if test="${currentUser.isAdmin()}">
                    <col width="5%"/>
                </g:if>
                <col width="20%"/>
                <col width="5%"/>
                <col width="15%"/>
                <col width="12%"/>
                <col width="17%"/>
                <col width="8%"/>
                <col width="8%"/>
                <col width="4%"/>
                <col width="3%"/>
                <col width="5%"/>
                <col width="3%"/>
            </colgroup>
            <thead>
            <tr>
                <g:if test="${currentUser.isAdmin()}">
                    <th>
                        <input type="checkbox" name="selectAllProjects" style="position: relative; top: 3px;"/>
                    </th>
                </g:if>
                <g:sortableColumn action="${sortActionName}" params="${params}" property="name" title="${g.message(code:'project.name.short')}" class="${list?'':'disabled'}"/>
                <g:sortableColumn action="${sortActionName}" params="${params}" property="lastUpdated" title="${g.message(code:'project.updated.short')}" class="${list?'':'disabled'}"/>
                <g:sortableColumn action="${sortActionName}" params="${params}" property="productName" title="${g.message(code:'project.productName.short')}" class="${list?'':'disabled'}"/>
                <g:sortableColumn action="${sortActionName}" params="${params}" property="dealer" title="${g.message(code:'project.dealer.short')}" class="${list?'':'disabled'}"/>
                <g:sortableColumn action="${sortActionName}" params="${params}" property="customer" title="${g.message(code:'project.customer.short')}" class="${list?'':'disabled'}"/>
                <g:sortableColumn action="${sortActionName}" params="${params}" property="city" title="${g.message(code:'project.city.short')}" class="${list?'':'disabled'}"/>
                <g:sortableColumn action="${sortActionName}" params="${params}" property="sum" title="${g.message(code:'project.sum.short')}" class="${list?'':'disabled'}"/>
                <g:sortableColumn action="${sortActionName}" params="${params}" property="status" title="${g.message(code:'project.status')}" class="${list?'':'disabled'}"/>
                <g:sortableColumn action="${sortActionName}" params="${params}" property="approvalStatus" title="${g.message(code:'project.approvalStatus')}" class="${list?'':'disabled'}"/>
                <g:sortableColumn action="${sortActionName}" params="${params}" property="releaseDate" title="${g.message(code:'project.releaseDate.short')}" class="${list?'':'disabled'}"/>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <g:each in="${projects}" status="i" var="nextProject">
                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                    <g:if test="${currentUser.isAdmin()}">
                        <td>
                            <g:checkBox name="achivedProjectChbx" value="${nextProject.id}" checked="false"/>
                        </td>
                    </g:if>
                    <td>
                        <span class="italic">(${nextProject.id})</span>
                        <g:link controller="project" action="show" id="${nextProject.id}" params="[archived: archived]">
                            ${nextProject.name}
                        </g:link>
                    </td>
                    <td>
                        ${currentUser?.dealer == nextProject.dealer || currentUser.isAdmin() ? formatDate(format: 'dd-MM-yyyy HH:mm', date: nextProject.lastUpdated) : '&mdash;'}
                    </td>
                    <td>${nextProject.productName}</td>
                    <td>${nextProject.dealer?.name}</td>
                    <td>
                        ${nextProject.customer}
                        <g:if test="${currentUser?.dealer == nextProject.dealer || currentUser.isAdmin()}">
                            <br/>(<g:message code="project.customer.inn.short"/>:&nbsp;${nextProject.inn})
                        </g:if>
                    </td>
                    <td>${nextProject.city?.name}</td>
                    <td>
                        ${currentUser?.dealer == nextProject.dealer || currentUser.isAdmin() ? formatMoney(value: nextProject.sum) : '&mdash;'}
                    </td>
                    <td>
                        <g:if test="${currentUser?.dealer == nextProject.dealer || currentUser.isAdmin()}">
                            <g:message code="${'project.status.' + nextProject.status.id}"/>
                        </g:if>
                        <g:else>
                            &mdash;
                        </g:else>
                    </td>
                    <td class="centered">
                        <img class="actionIcon" width="24" src="${resource(dir: 'images', file: 'lt_status_' + nextProject.approvalStatus.id + '.png')}" alt="${message(code: 'project.status.lt.' + nextProject.approvalStatus.id)}" title="${message(code: 'project.status.lt.' + nextProject.approvalStatus.id)}"/>
                    </td>
                    <td>
                        ${currentUser?.dealer == nextProject.dealer || currentUser.isAdmin() ? nextProject.releaseDate : '&mdash;'}
                    </td>
                    <td>
                        <g:if test="${currentUser?.dealer == nextProject.dealer}">
                            <g:link controller="project" action="edit" id="${nextProject.id}">
                                <img class="actionIcon" src="${resource(dir: 'images', file: 'edit.png')}" alt="${message(code: 'project.action.edit')}"/>
                            </g:link>
                        </g:if>
                    </td>
                </tr>
            </g:each>
            </tbody>
        </table>
    </g:if>

    <g:if test="${total == 0}">
        <table class="notFound">
            <tr>
                <td>
                    <div class="notfoundTitle"><g:message code="list.notFound"/></div>
                    <div class="NothingFoundActions">
                        <g:message code="you.can"/><g:link controller="project" action="list"><g:message code="list.clearFilter"/></g:link>
                        <g:message code="or"/><g:link controller="project" action="create"><g:message code="add.project"/></g:link>

                    </div>
                </td>
            </tr>
        </table>
    </g:if>

    <g:if test="${filter.max < total}">
        <div class="pagination">
            <g:paginate total="${total}" params="${[q: quickSearchStr]}"/>
        </div>
    </g:if>
</div>